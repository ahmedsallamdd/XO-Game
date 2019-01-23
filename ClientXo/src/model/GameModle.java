package model;

import controller.GameController;
import commontxo.ChatRoom;
import commontxo.ServerCallBack;
import commontxo.ClientCallBack;
import commontxo.GameRoom;
import commontxo.GameState;
import commontxo.Player;
import commontxo.PlayerList;
import commontxo.ServerNullExeption;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import view.gameRoomFXMLBase;

/**
 *
 * @author Abdo Amin
 */
public class GameModle extends UnicastRemoteObject implements ClientCallBack {

    public GameController myController;
    private ServerCallBack server = null;
    public Player me = null;

    public ArrayList<PlayerList> onlineList;
    public GameRoom gameRoom;
    public HashMap<String, ChatRoom> chatRooms = new HashMap<>();//multibale chat rooms

    public ArrayList<Alert> currentShowenAlerts = new ArrayList<>();

    public GameModle(GameController myController) throws RemoteException {
        this.myController = myController;
    }

    public ServerCallBack getServerInstance() throws ServerNullExeption {
        if (server == null) {
            try {
                Registry reg = LocateRegistry.getRegistry(/*"10.0.1.182",*/1099);
                server = (ServerCallBack) reg.lookup("GameService");

            } catch (RemoteException | NotBoundException e) {
                throw new ServerNullExeption();
            }
        }
        return server;
    }

    @Override
    public void sendGameNotification(String oppesiteUserName) throws RemoteException {
        myController.showRequestNotification(oppesiteUserName);
    }

    @Override
    public void joinGameRoom(String roomName, ClientCallBack creatorClient) throws RemoteException {
        gameRoom = new GameRoom(roomName, new HashMap<String, ClientCallBack>() {
            {
                put(roomName, creatorClient);
            }
        });
    }

    @Override
    public void joinChatRoom(String targetUserName, ClientCallBack targetClient) throws RemoteException {
        chatRooms.put(targetUserName, new ChatRoom("", targetClient));
    }

    @Override
    public void leaveGameRoom(String winner) throws RemoteException {
        if (gameRoomFXMLBase.mode.equals("player")) {
            myController.winDialog(winner);
        } else {
            Platform.runLater(() -> {
                ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                Alert a = new Alert(Alert.AlertType.INFORMATION,
                        "",
                        ok);
                a.setTitle("Result");
                a.setHeaderText(winner + " has won!");
                currentShowenAlerts.add(a);
                a.showAndWait();

                currentShowenAlerts.remove(a);
                myController.resetGameState();
                try {
                    myController.myGUI.createMainScreen();
                } catch (ServerNullExeption ex) {
                    myController.serverUnavilable();
                }
            });
        }
        gameRoom = null;
    }

    @Override
    public void play(int position) throws RemoteException {
        try {
            //get the playerSymbol from the playerUserName.
            myController.modifyPositionsArray(position);
        } catch (ServerNullExeption ex) {
            serverUnavilable();
        }
    }

    @Override
    public void notifiyOnlineList() throws RemoteException {
        Platform.runLater(() -> {
            try {
                myController.showPlayerList();
            } catch (ServerNullExeption ex) {
                myController.serverUnavilable();
            } catch (RemoteException ex) {
                System.out.println("Server Error");
            }
        });
    }

    @Override
    public void addPlayerToGameRoom(String playerUserName, ClientCallBack player) throws RemoteException {
        gameRoom.addPlayer(playerUserName, player);
    }

    @Override
    public void leftChatRoom(String userNameWhoLeft) throws RemoteException {
        if (!me.getPlayerUserName().equals(userNameWhoLeft)
                && chatRooms.containsKey(userNameWhoLeft)) {
            chatRooms.remove(userNameWhoLeft);
        }
    }

//    @Override 
//    public void receiveMessage(String senderUserName, String message) throws RemoteException {
//        if (chatRooms.get(senderUserName) != null) {
//             chatRooms.get(senderUserName).setChat(chatRooms.get(senderUserName).getChat()+senderUserName+": "+message+"\n");
//               myController.displayMessage(chatRooms.get(senderUserName).getChat());
//        }
//    }
//
//    @Override // deprecated
//    public void sendMessage(String myUserName, String message) throws RemoteException {
//        try {
//            receiveMessage(myUserName, message);
//        } catch (RemoteException ex) {
//            Logger.getLogger(GameModle.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        ChatRoom myChatRoom = chatRooms.get(myUserName);
//
//        ClientCallBack others = myChatRoom.getOtherClients();
//        try {
//            others.receiveMessage(myUserName, message);
//        } catch (RemoteException ex) {
//            Logger.getLogger(GameModle.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
    @Override
    public void receiveMessage(String senderUserName, String message) throws RemoteException {
        if (chatRooms.get(senderUserName) != null) {
            myController.displayMessage(senderUserName + " : " + message);
        }
    }

    @Override
    public void sendMessage(String oppositeUserName, String message) throws RemoteException {
        receiveMessage(oppositeUserName, message);
    }

    @Override
    public void leftGameRoom(String userNameWhoLeft) throws RemoteException {
        if (gameRoom != null) {
            if (gameRoom.getPlayers().containsKey(userNameWhoLeft)) {
                gameRoom.removePlayer(userNameWhoLeft);
            }
        }
    }

    @Override
    public void setGameState(GameState gameState) throws RemoteException {
        myController.setGameState(gameState);
    }

    @Override
    public GameState getGameState() throws RemoteException {
        return myController.getGameState();
    }

    //deprecated
//    @Override
//    public void refuseGameRequest(String oppesiteUserName) throws RemoteException {
//        myController.refuseGameRequest(oppesiteUserName);
//    }
    @Override
    public void acceptGameRequest(String oppesiteUserName) throws RemoteException {
        try {
            myController.acceptGameRequest(oppesiteUserName);
        } catch (ServerNullExeption ex) {
            myController.serverUnavilable();
        }
    }

    @Override
    public void startGame(String mode) throws RemoteException {
        gameRoomFXMLBase.mode = mode;
        myController.startGameRoom();
    }

    public void clearServer() {
        server = null;
    }

    @Override
    public void serverUnavilable() throws RemoteException {
        try {
            myController.signOut();
        } catch (ServerNullExeption ex) {
        } finally {
            myController.serverUnavilable();
        }

    }

    @Override
    public void showAlert(String title, String headerText, String message) throws RemoteException {
        myController.showAlert(title, headerText, message);
    }

    @Override
    public void closeAllAlert() throws RemoteException {
        Platform.runLater(() -> {
            currentShowenAlerts.forEach(alert -> {
                alert.close();
            });
            currentShowenAlerts.clear();
        });
    }

}
