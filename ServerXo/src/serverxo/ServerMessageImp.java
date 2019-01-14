package serverxo;

import commontxo.ClientCallBack;
import commontxo.GameRoom;
import commontxo.Player;
import commontxo.PlayerList;
import commontxo.ServerCallBack;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Abdo Amin
 */
public class ServerMessageImp extends UnicastRemoteObject implements ServerCallBack {

    static Connection connection = null;
    static String dbName = "gamexo";
    static String url = "jdbc:mysql://localhost:3306/" + dbName;
    static String username = "root";
    static String password = "AbdoAmin01";
    static ArrayList<Player> PlayersInformation;

    HashMap<String, ClientCallBack> clients = new HashMap<>();

    HashMap<String, String> clientMapGameRoom = new HashMap<>();

    HashMap<String, GameRoom> gameRooms = new HashMap<>();//for fast acces

    public void IntializePlayersList() {
        Player p;

        try {
            Statement stmt = connection.createStatement();
            String query = "select * from user ";
            ResultSet s = stmt.executeQuery(query);

            while (s.next()) {
                p = new Player();
                p.setPlayerID(s.getInt("UserID"));
                p.setPlayerUserName(s.getString("UserName"));
                p.setPlayerEmail(s.getString("UserEmail"));
                p.setPlayerPassword(s.getString("UserPassword"));
                p.setPlayerName(s.getString("Name"));
                p.setPlayerScore(s.getInt("UserScore"));
                p.setPlayerState("offline");
                PlayersInformation.add(p);
            }
            stmt.close();
        } catch (SQLException e) {
        }

    }

    public void updateList() {
        clients.forEach((e, client) -> {
            try {
                client.notifiyOnlineList();
            } catch (RemoteException ex) {
                Logger.getLogger(ServerMessageImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public ServerMessageImp() throws RemoteException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(url, username, password);
        PlayersInformation = new ArrayList<>();
        IntializePlayersList();
    }

    @Override
    public void register(ClientCallBack clientRef, String playerUserName) throws RemoteException {
        clients.put(playerUserName, clientRef);
        updateList();
    }

    @Override
    public ArrayList<PlayerList> initOnlineList() throws RemoteException {
        ArrayList<PlayerList> ps = new ArrayList<>();
        PlayersInformation.stream()
                .filter(player -> player.getPlayerState()
                .equals("online"))
                .collect(toList()).forEach(player -> {
            String room = null;
            if (clientMapGameRoom.containsKey(player.getPlayerUserName())) {
                room = clientMapGameRoom.get(player.getPlayerUserName());
                if (room.equals("")) {
                    room = null;
                }
            }
            ps.add(new PlayerList(player.getPlayerUserName(), player.getPlayerScore(), room));
        });
        return ps;
    }

    @Override
    public void sendGameRequest(String myUserName, String oppesiteUserName) throws RemoteException {
        if (clients.containsKey(oppesiteUserName) && clients.containsKey(myUserName)) {
            clients.get(oppesiteUserName).sendGameNotification(myUserName);
        }
    }

    @Override
    public void startGameRoom(String myUserName, String oppesiteUserName) throws RemoteException {
        HashMap<String, ClientCallBack> temp = new HashMap<String, ClientCallBack>() {
            {
                put(myUserName, clients.get(myUserName));
                put(oppesiteUserName, clients.get(oppesiteUserName));
            }
        };
        GameRoom newGameRoom = new GameRoom(myUserName, temp);
        gameRooms.put(myUserName, newGameRoom);
        clientMapGameRoom.put(myUserName, newGameRoom.getRoomName());
        clientMapGameRoom.put(oppesiteUserName, newGameRoom.getRoomName());
        try {
            //Init GameRoom at Client Side
            clients.get(myUserName).joinGameRoom(myUserName, clients.get(myUserName));
            clients.get(oppesiteUserName).joinGameRoom(myUserName, clients.get(myUserName));

            //pass CleintInterFace
            clients.get(myUserName).addPlayerToGameRoom(oppesiteUserName, clients.get(oppesiteUserName));
            clients.get(oppesiteUserName).addPlayerToGameRoom(oppesiteUserName, clients.get(oppesiteUserName));

            //start game gui 
            clients.get(myUserName).startGame(oppesiteUserName, clients.get(oppesiteUserName));
            clients.get(oppesiteUserName).startGame(oppesiteUserName, clients.get(oppesiteUserName));
            updateList();

        } catch (RemoteException ex) {
            Logger.getLogger(ServerMessageImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean notifiyGameResult(String roomName, String winnerUserName) throws RemoteException {
        if (winnerUserName != null) {
            PlayersInformation.forEach(player -> {
                if (player.getPlayerUserName().equals(winnerUserName)) {
                    player.setPlayerScore(player.getPlayerScore() + 10);
                }
            });
        }
        if (gameRooms.containsKey(roomName)) {
            gameRooms.get(roomName).getPlayers().forEach((e, client) -> {
                try {
                    client.leaveGameRoom();

                } catch (RemoteException ex) {
                    Logger.getLogger(ServerMessageImp.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            });
            clientMapGameRoom.forEach((name, room)
                    -> {
                if (room.equals(roomName)) {
                    clientMapGameRoom.remove(name);
                }
            });
            leftChatRoom(gameRooms.get(roomName).getPlayers().keySet().toArray()[0].toString(),
                    gameRooms.get(roomName).getPlayers().keySet().toArray()[1].toString());
            gameRooms.remove(roomName);
            updateList();
            return true;
        }
        return false;
    }

    @Override
    public void joinChatRoom(String myUserName, String playerUserName) throws RemoteException {
        if (clients.containsKey(myUserName) && clients.containsKey(playerUserName)) {
            clients.get(myUserName).joinChatRoom(playerUserName, clients.get(playerUserName));
            clients.get(playerUserName).joinChatRoom(myUserName, clients.get(myUserName));
        }
    }

    @Override
    public void leftChatRoom(String myUserName, String userNameWhoLeft) throws RemoteException {
        if (clients.containsKey(myUserName) && clients.containsKey(userNameWhoLeft)) {
            clients.get(myUserName).leftChatRoom(userNameWhoLeft);
            clients.get(userNameWhoLeft).leftChatRoom(myUserName);
        }
    }

    @Override
    public void spectateGame(String myUserName, String roomName) throws RemoteException {
        if (gameRooms.containsKey(roomName)) {
            ArrayList<String> usersNames = new ArrayList<>(gameRooms.get(roomName).getPlayers().keySet());
            clients.get(myUserName).joinGameRoom(roomName, gameRooms.get(roomName).getPlayers().get(usersNames.get(0)));
            //to start game from last played step
            clients.get(myUserName).setArrayPosition(
                    gameRooms.get(roomName).getPlayers().get(usersNames.get(0)).getArrayPosition());
            for (int i = 1; i < gameRooms.get(roomName).getPlayers().size(); i++) {
                clients.get(myUserName).addPlayerToGameRoom(usersNames.get(i),
                        gameRooms.get(roomName).getPlayers().get(usersNames.get(i)));
            }
            gameRooms.get(roomName).addPlayer(myUserName, clients.get(myUserName));
            clientMapGameRoom.put(myUserName, roomName);
            gameRooms.get(roomName).getPlayers().forEach((e, client) -> {
                try {
                    client.addPlayerToGameRoom(myUserName, clients.get(myUserName));

                } catch (RemoteException ex) {
                    Logger.getLogger(ServerMessageImp.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            });
            //this parameters are deprecated and useless 
            clients.get(myUserName).startGame(myUserName, clients.get(myUserName));
            updateList();
        }
    }

    @Override
    public void leaveServer(String myUserName) throws RemoteException {
        if (clients.containsKey(myUserName)) {
            //TODO leave gameroom if exist
            clients.forEach((e, client) -> {
                try {
                    client.leftChatRoom(myUserName);
                    client.leftGameRoom(myUserName);

                } catch (RemoteException ex) {
                    Logger.getLogger(ServerMessageImp.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            });
            removeClient(myUserName);
        }
    }

    @Override
    public boolean checkUserName(String userName) throws RemoteException {
        for (Player p : PlayersInformation) {
            if (p.getPlayerUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    //// some Rawshana
    @Override
    public boolean signUp(String userName, String Name, String upassword, String Email) throws RemoteException {
        try {
            String query = "INSERT INTO `gamexo`.`user` "
                    + "(`UserName`, `Name`, `UserEmail`, `UserPassword`) values (?, ?, ?, ?)";
            PreparedStatement p = (PreparedStatement) connection.prepareStatement(query);
            p.setString(1, userName);
            p.setString(2, Name);
            p.setString(3, Email);
            p.setString(4, upassword);
            if (p.execute()) {
                Player player = new Player();
                player.setPlayerUserName(userName);
                player.setPlayerName(Name);
                player.setPlayerPassword(upassword);
                player.setPlayerEmail(Email);
                player.setPlayerState("online");
                PlayersInformation.add(player);
            }
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public void signOut(Player player) throws RemoteException {
        for (Player p : PlayersInformation) {
            if (p.getPlayerUserName().equals(player)) {
//                p.setPlayerState("offline");      //deprecated , called alrady at leaveServer
                leaveServer(player.getPlayerUserName());
                break;
            }
        }
    }

    @Override
    public Player signIn(String userName, String PlayerPassword) throws RemoteException {
        for (Player p : PlayersInformation) {
            if (p.getPlayerUserName().equals(userName) && p.getPlayerPassword().equals(PlayerPassword)) {
                p.setPlayerState("online");
                return p;
            }
        }
        return null;
    }

    public void removeClient(String userName) throws RemoteException {
        if (clients.containsKey(userName)) {
            clients.remove(userName);
            for (Player p : PlayersInformation) {
                if (p.getPlayerUserName().equals(userName) && p.getPlayerPassword().equals(userName)) {
                    p.setPlayerState("offline");
                }
            }
            updateList();
        }
    }

    @Override
    public void removeClientMapGameRoom(String userName) throws RemoteException {
        if (clientMapGameRoom.containsKey(userName)) {
            clientMapGameRoom.remove(userName);
        }
    }

    @Override
    public void removePlayerFromGameRoom(String userName, String gameRoom) throws RemoteException {
        if (gameRooms.containsKey(gameRoom)) {
            gameRooms.get(gameRoom).removePlayer(userName);
        }
    }

    @Override
    public void refuseGameRequest(String myUserName, String oppesiteUserName) throws RemoteException {
        if (clients.containsKey(oppesiteUserName)) {
            clients.get(oppesiteUserName).refuseGameRequest(myUserName);
        }
    }

}
