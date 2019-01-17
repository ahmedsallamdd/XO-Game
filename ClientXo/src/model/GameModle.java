package model;

import controller.GameController;
import commontxo.ChatRoom;
import commontxo.ServerCallBack;
import commontxo.ClientCallBack;
import commontxo.GameRoom;
import commontxo.Player;
import commontxo.PlayerList;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
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

    public ServerCallBack getServerInstance() {
        if (server == null) {
            try {
                Registry reg = LocateRegistry.getRegistry(/*"10.0.1.182",*/1099);
                server = (ServerCallBack) reg.lookup("GameService");

            } catch (RemoteException | NotBoundException e) {
                System.err.println(e.getMessage());
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
        myController.winDialog(winner);
        gameRoom = null;
    }

    @Override
    public void play(String player, int position) throws RemoteException {
        //get the playerSymbol from the playerUserName.
        myController.modifyPositionsArray(player, position);
    }

    @Override
    public void notifiyOnlineList() throws RemoteException {
        Platform.runLater(() -> {
            try {
                myController.showPlayerList();
            } catch (RemoteException ex) {
                Logger.getLogger(GameModle.class.getName()).log(Level.SEVERE, null, ex);
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
    public void setArrayPosition(int[] positions) throws RemoteException {
        myController.setArrayPosition(positions);
    }

    @Override
    public int[] getArrayPosition() throws RemoteException {
        return myController.getArrayPosition();
    }

    //deprecated
//    @Override
//    public void refuseGameRequest(String oppesiteUserName) throws RemoteException {
//        myController.refuseGameRequest(oppesiteUserName);
//    }
    @Override
    public void acceptGameRequest(String oppesiteUserName) throws RemoteException {
        myController.acceptGameRequest(oppesiteUserName);
    }

    @Override
    public void startGame(String playerUserName, ClientCallBack player, String mode) throws RemoteException {
        setArrayPosition(player.getArrayPosition());
        gameRoomFXMLBase.mode = mode;
        myController.startGameRoom();
    }

    public void clearServer() {
        server = null;
    }

    @Override
    public void serverUnavilable() throws RemoteException {
        myController.signOut();
        myController.serverUnavilable();
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
