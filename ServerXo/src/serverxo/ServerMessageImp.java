package serverxo;

import commontxo.ClientCallBack;
import commontxo.GameRoom;
import commontxo.GameState;
import commontxo.Player;
import commontxo.PlayerList;
import commontxo.ServerCallBack;
import commontxo.Utilites;
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
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import javafx.application.Platform;
import model.Pair;

/**
 *
 * @author Abdo Amin
 */
public class ServerMessageImp extends UnicastRemoteObject implements ServerCallBack {

    static Connection connection = null;
    static String dbName = "gamexo";
    static String url = "jdbc:mysql://localhost:3306/" + dbName;
    static String username = "root";
    static String password = "12345";
    static ArrayList<Player> PlayersInformation;

    HashMap<String, ClientCallBack> clients = new HashMap<>();

    HashMap<String, String> clientMapGameRoom = new HashMap<>();

    HashMap<String, GameRoom> gameRooms = new HashMap<>();//for fast acces

    ArrayList<Pair<String>> notifications = new ArrayList<>();

    public void IntializePlayersList() {
        Player p;

        try {
            Statement stmt = connection.createStatement();
            String query = "select * from user order by UserScore desc ";
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
            ServerXo.showErrorAlert();
        }

    }

    public void updateList(String playerUserName,String state) {
        ArrayList<ClientCallBack> x = new ArrayList<>(clients.values());
        for (Iterator<ClientCallBack> it = x.iterator(); it.hasNext();) {
            ClientCallBack client = it.next();
            try {
                client.notifiyOnlineList(playerUserName,state);
            } catch (RemoteException ex) {

                removePlayerWhileError(Utilites.getKeyByValue(clients, client));
            }
        }
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
        updateList(playerUserName,"online");
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
            Pair request = new Pair(myUserName, oppesiteUserName);
            if (!notifications.contains(request)) {
                notifications.add(request);
                clients.get(oppesiteUserName).sendGameNotification(myUserName);
            }
            clients.get(myUserName).showAlert("Game Request", "Waiting", "waiting for " + oppesiteUserName + " accept");
        }
    }

    @Override
    public void startGameRoom(String myUserName, String oppesiteUserName) {
        if (clients.containsKey(myUserName) && clients.containsKey(oppesiteUserName)) {

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

                joinChatRoom(myUserName, oppesiteUserName);

                GameState gameState = new GameState(myUserName, oppesiteUserName, new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2}, 0);
                clients.get(myUserName).setGameState(gameState);
                clients.get(oppesiteUserName).setGameState(gameState);

                //start game gui 
                clients.get(myUserName).startGame("player");
                clients.get(oppesiteUserName).startGame("player");

                //end any request
                clients.get(myUserName).closeAllAlert();
                clients.get(oppesiteUserName).closeAllAlert();

                Pair request = new Pair(myUserName, oppesiteUserName);
                if (notifications.contains(request)) {
                    notifications.remove(request);
                }
                updateList(null,null);

            } catch (RemoteException ex) {
                try {
                    clients.get(myUserName).notifiyOnlineList(null,null);
                } catch (RemoteException ex1) {
                    removePlayerWhileError(Utilites.getKeyByValue(clients, clients.get(myUserName)));
                }
                try {
                    clients.get(oppesiteUserName).notifiyOnlineList(null,null);
                } catch (RemoteException ex1) {
                    removePlayerWhileError(Utilites.getKeyByValue(clients, clients.get(myUserName)));
                }

            }
        } else {
            try {
                clients.get(oppesiteUserName).showAlert("Oops!", "The other player has logged out!", "");
            } catch (RemoteException ex) {
                removePlayerWhileError(Utilites.getKeyByValue(clients, clients.get(oppesiteUserName)));
            }
            Pair request = new Pair(myUserName, oppesiteUserName);
            if (notifications.contains(request)) {
                notifications.remove(request);
            }
            updateList(null,null);

        }
    }

    @Override
    public boolean notifiyGameResult(String roomName, String winnerUserName) throws RemoteException {
        if (winnerUserName != null && !winnerUserName.equals("DRAW")) {
            PlayersInformation.forEach(player -> {
                if (player.getPlayerUserName().equals(winnerUserName)) {
                    player.setPlayerScore(player.getPlayerScore() + 10);
                }
            });
        }
        if (gameRooms.containsKey(roomName)) {
            GameState gameState = clients.get(roomName).getGameState();

            ArrayList<ClientCallBack> xx = new ArrayList<>(gameRooms.get(roomName).getPlayers().values());
            for (Iterator<ClientCallBack> it = xx.iterator(); it.hasNext();) {
                ClientCallBack client = it.next();
                try {
                    client.leaveGameRoom(winnerUserName);
                } catch (RemoteException ex) {
                    removePlayerWhileError(Utilites.getKeyByValue(clients, client));
                }
            }
            clientMapGameRoom = new HashMap(clientMapGameRoom.entrySet().stream()
                    .filter(room -> !room.equals(roomName))
                    .collect(
                            Collectors.toMap(x -> x, y -> y)));

            leftChatRoom(gameState.getInGamePlayer0(),
                    gameState.getInGamePlayer1());
            gameRooms.remove(roomName);
            updateList(null,null);

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
            clients.get(myUserName).setGameState(
                    gameRooms.get(roomName).getPlayers().get(usersNames.get(0)).getGameState());

            for (int i = 1; i < gameRooms.get(roomName).getPlayers().size(); i++) {
                clients.get(myUserName).addPlayerToGameRoom(usersNames.get(i),
                        gameRooms.get(roomName).getPlayers().get(usersNames.get(i)));
            }
            gameRooms.get(roomName).addPlayer(myUserName, clients.get(myUserName));
            clientMapGameRoom.put(myUserName, roomName);

            ArrayList<ClientCallBack> xx = new ArrayList<>(gameRooms.get(roomName).getPlayers().values());
            for (Iterator<ClientCallBack> it = xx.iterator(); it.hasNext();) {
                ClientCallBack client = it.next();
                try {
                    client.addPlayerToGameRoom(myUserName, clients.get(myUserName));
                } catch (RemoteException ex) {
                    removePlayerWhileError(Utilites.getKeyByValue(clients, client));
                }
            }
            //this parameters are deprecated and useless 
            clients.get(myUserName).startGame(/*myUserName, clients.get(myUserName), */"spectator");
            updateList(null,null);
        }
    }

    @Override
    public void leaveServer(String myUserName) {
        if (clients.containsKey(myUserName)) {
            if (clientMapGameRoom.containsKey(myUserName)) {
                removePlayerFromGameRoom(myUserName, clientMapGameRoom.get(myUserName));
                removeClientMapGameRoom(myUserName);
            }
            removeClient(myUserName);
            ArrayList<ClientCallBack> x = new ArrayList<>(clients.values());
            for (Iterator<ClientCallBack> it = x.iterator(); it.hasNext();) {
                ClientCallBack client = it.next();
                try {
                    client.leftChatRoom(myUserName);
                    client.leftGameRoom(myUserName);
                } catch (RemoteException ex) {
                    removePlayerWhileError(Utilites.getKeyByValue(clients, client));
                }
            }
            updateList(myUserName,"ofline");
        }
    }

    @Override
    public boolean checkUserName(String userName) throws RemoteException {
        for (Iterator<Player> it = PlayersInformation.iterator(); it.hasNext();) {
            Player p = it.next();
            if (p.getPlayerUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    //// some Rawshana
    @Override
    public boolean signUp(String userName, String Name, String upassword, String Email) throws RemoteException {
        Player newPlayer = null;
        try {
            for (Iterator<Player> it = PlayersInformation.iterator(); it.hasNext();) {
                Player player = it.next();
                if (player.getPlayerUserName().equals(userName)) {
                    return false;
                }
            }
            newPlayer = new Player();
            newPlayer.setPlayerUserName(userName);
            newPlayer.setPlayerName(Name);
            newPlayer.setPlayerPassword(upassword);
            newPlayer.setPlayerEmail(Email);
            newPlayer.setPlayerState("offline");
            PlayersInformation.add(newPlayer);
            String query = "INSERT INTO `gamexo`.`user` "
                    + "(`UserName`, `Name`, `UserEmail`, `UserPassword`) values (?, ?, ?, ?)";
            PreparedStatement p = (PreparedStatement) connection.prepareStatement(query);
            p.setString(1, userName);
            p.setString(2, Name);
            p.setString(3, Email);
            p.setString(4, upassword);
            p.execute();
            return true;
        } catch (SQLException ex) {
            PlayersInformation.remove(newPlayer);
            return false;
        }
    }

    @Override
    public void signOut(Player player) throws RemoteException {
        for (Iterator<Player> it = PlayersInformation.iterator(); it.hasNext();) {
            Player p = it.next();
            if (p.getPlayerUserName().equals(player.getPlayerUserName())) {
                leaveServer(player.getPlayerUserName());
                break;
            }
        }
    }

    @Override
    public Player signIn(String userName, String PlayerPassword) throws RemoteException {
        for (Iterator<Player> it = PlayersInformation.iterator(); it.hasNext();) {
            Player p = it.next();
            if (p.getPlayerUserName().equals(userName)
                    && p.getPlayerPassword().equals(PlayerPassword)) {
                if (p.getPlayerState().equals("offline")) {
                    p.setPlayerState("online");
                    return p;
                } else {
//                    clients.get(p.getPlayerUserName()).showAlert("Logging in error",
//                            "This account is already logged in!", ".");
                    Player nullPlayer = new Player();
                    nullPlayer.setPlayerState("Already logged in");
                    return nullPlayer;
                }
            }
        }
        return null;
    }

    public void removeClient(String userName) {
        if (clients.containsKey(userName)) {
            clients.remove(userName);
            for (Iterator<Player> it = PlayersInformation.iterator(); it.hasNext();) {
                Player p = it.next();
                if (p.getPlayerUserName().equals(userName)) {
                    p.setPlayerState("offline");
                }
            }
        }
    }

    @Override
    public void removeClientMapGameRoom(String userName) {
        if (clientMapGameRoom.containsKey(userName)) {
            clientMapGameRoom.remove(userName);
        }
    }

    @Override
    public void removePlayerFromGameRoom(String userName, String gameRoom) {
        if (gameRooms.containsKey(gameRoom)) {
            gameRooms.get(gameRoom).removePlayer(userName);
        }
    }

    @Override
    public void refuseGameRequest(String reciver, String sender) throws RemoteException {
        if (clients.containsKey(sender)) {
            //deprecated "" ايوه بنعمل فانكشن و نخليها  دبركيتيك... احنا مش اقل من اللي اخترعو  جافا
//            clients.get(sender).refuseGameRequest(reciver);  
            clients.get(sender).showAlert("Game Refused", "Refused", reciver + " refuse to play with you.");
            Pair request = new Pair(reciver, sender);
            if (notifications.contains(request)) {
                notifications.remove(request);
            }
        }
    }

    void updateScore(String userName, int newScore) {
        try {
            Statement stmt = connection.createStatement();
            String query = "UPDATE user SET UserScore =" + newScore + " WHERE UserName ='" + userName + "'";
            stmt.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Can't Update This Player: " + userName + "\nKeep in Log File To Handle his score is: " + newScore);
        }
    }

    void removePlayerWhileError(String playerUserName) {
        leaveServer(playerUserName);
    }
}
