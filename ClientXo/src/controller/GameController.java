/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.InGamePlayer;
import model.GameModle;
import view.Gui;
import commontxo.ClientCallBack;
import commontxo.Player;
import commontxo.PlayerList;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.gameRoomFXMLBase;

public class GameController {

    MyGui myGUI;
    public GameModle myModle;

    int[] positions = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = new int[][]{
        {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6},
        {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    private int movesCounter = 0;
    private boolean isFinished = false;
    public int activePlayer = 0;

    public ArrayList<String> names;
    InGamePlayer inGamePlayer0;
    InGamePlayer inGamePlayer1;
    boolean isYourTurn;

    public GameController(MyGui g) {
        myGUI = g;
        try {
            myModle = new GameModle(this);
            inGamePlayer0 = new InGamePlayer();
            inGamePlayer1 = new InGamePlayer();

        } catch (RemoteException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startGameRoom() {
        //Note to myself: don't worry..the two players have the same tarteeb.
        names = new ArrayList<>(myModle.gameRoom.getPlayers().keySet());
//        reDrawGameBoard();
        inGamePlayer0.setPlayerName(names.get(0));
        inGamePlayer0.setPlayerSymbol(0);
        inGamePlayer0.setIsMyTurn(true);

        inGamePlayer1.setPlayerName(names.get(1));
        inGamePlayer1.setPlayerSymbol(1);
        inGamePlayer0.setIsMyTurn(false);

        isYourTurn = myModle.me.getPlayerUserName().equals(names.get(0));
        System.out.println(isYourTurn);

        Platform.runLater(() -> {
            myGUI.createMultiPlayerGui();
        });
    }

    public void displayMessage(String myMessage) {
        myGUI.displayMessage(myMessage);
    }

    void getSelectedImgView(String id) {
        ImageView imgView = (ImageView) myGUI.multiPlayerScreen.gridPane.lookup("#" + id);
        if (isFinished==false) {
            if (isYourTurn == true && imgView.getImage() == null) {
                getPositionFromId(id);
            }
        }
    }

    public void modifyPositionsArray(String player, int posPlayed) {
        int symbol;
        if (inGamePlayer0.getPlayerName().equals(myModle.gameRoom.getPlayers().get(player))) {
            symbol = inGamePlayer0.getPlayerSymbol();
        } else {
            symbol = inGamePlayer1.getPlayerSymbol();
        }
        positions[posPlayed] = activePlayer;
        System.out.println("[" + posPlayed + "]" + " = " + symbol);
        reDrawGameBoard();
        checkGameResult();
        switchTurns();
    }

    private void getPositionFromId(String id) {
        String[] components = id.split("_");
        int posPlayed = Integer.valueOf(components[1]);
        System.out.println(myModle.gameRoom.getPlayers().size());
        try {
            for (ClientCallBack client : myModle.gameRoom.getPlayers().values()) {
                if (isYourTurn == true) {
                    client.play(inGamePlayer0.getPlayerName(), posPlayed);
//                    isYourTurn = false;
                } else {
                    client.play(inGamePlayer1.getPlayerName(), posPlayed);
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void switchTurns() {
        isYourTurn = isYourTurn != true;
        if (activePlayer == 0) {
            activePlayer = 1;
        } else {
            activePlayer = 0;
        }
    }

    public void reDrawGameBoard() {
        String imgViewId;
        ImageView imgView;
        gameRoomFXMLBase newRoot = new gameRoomFXMLBase(myGUI);
        for (int i = 0; i < positions.length; i++) {
            if (positions[i] != 2) {
                imgViewId = constructImgeViewId(i);
                System.out.println(imgViewId);
                imgView = (ImageView) newRoot.gridPane.lookup("#" + imgViewId);
                if (positions[i] == 0) {
                    imgView.setImage(new Image("/images/X_image.png"));
                } else {
                    imgView.setImage(new Image("/images/O_image.png"));
                }
            }
        }
        Platform.runLater(() -> {
            myGUI.createMultiPlayerGui(newRoot);
        });

    }

    private String constructImgeViewId(int pos) {
        return "img_" + String.valueOf(pos);
    }

    private void checkGameResult() {
        for (int[] winningPosition : winningPositions) {
            if (positions[winningPosition[0]] != 2
                    && positions[winningPosition[0]] == positions[winningPosition[1]]
                    && positions[winningPosition[1]] == positions[winningPosition[2]]) {

                if (positions[winningPosition[0]] == 0) {
                    System.out.println("X has won!");
                    isFinished = true;
                    return;
                } else {
                    System.out.println("O has won!");
                    isFinished = true;
                    return;
                }
            } else if (movesCounter == 9) {
                System.out.println("It's a draw!");
                isFinished = true;
            }
        }
    }

    
    
    public void leaveServer() throws RemoteException {
        if(myModle.gameRoom!=null){
            withdraw();
        }
        else
            myModle.getServerInstance().leaveServer(myModle.me.getPlayerUserName());
    }
    
    //current player surrender or leave spectate.
    public void withdraw() throws RemoteException {
        //remove Mysilfe ...
        ArrayList<String> temp = new ArrayList<>(myModle.gameRoom.getPlayers().keySet());
        if (temp.indexOf(myModle.me.getPlayerUserName()) > 1) {
            myModle.gameRoom.getPlayers().forEach((e, client) -> {
                try {
                    client.leftGameRoom(myModle.me.getPlayerUserName());
                } catch (RemoteException ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            myModle.leaveGameRoom();
            myModle.getServerInstance().removeClientMapGameRoom(myModle.me.getPlayerUserName());
            myModle.getServerInstance().removePlayerFromGameRoom(myModle.me.getPlayerUserName(), myModle.gameRoom.getRoomName());
        } else {
            temp.remove(myModle.me.getPlayerUserName());
            myModle.getServerInstance().notifiyGameResult(myModle.gameRoom.getRoomName(), temp.get(0));
        }
    }
    
    

    public boolean signUp(String userName, String name, String email, String password) throws RemoteException {
        if (myModle.getServerInstance().signUp(userName, name, password, email)) {
            signIn(userName, password);
            return true;
        }
        return false;
    }

    public boolean signIn(String userName, String password) {

        try {
            Player p = myModle.getServerInstance().signIn(userName, password);
//            System.out.println(myModle.me.getPlayerName());
            if (p != null) {
                myModle.me = p;
                myModle.getServerInstance().register(myModle, userName);
                return true;
            }
        } catch (RemoteException ex) {
            System.out.println("Server Error");
        }

        return false;
    }

    public boolean checkUserName(String userName) {
        try {
            return myModle.getServerInstance().checkUserName(userName);
        } catch (RemoteException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    void playWithComputer() {
        System.out.println("playing with computer");
    }

    public void showPlayerList() throws RemoteException {
        ArrayList<PlayerList> list = myModle.getServerInstance().initOnlineList();

        for (PlayerList pl : list) {
            if (pl.getName().equals(myModle.me.getPlayerUserName())) {
                list.remove(pl);
                break;
            }

        }
        myGUI.getPlayerListData(list);

    }

    void signOut() throws RemoteException {
        myModle.getServerInstance().signOut(myModle.me);
    }

    public void setArrayPosition(int[] positions) {
        this.positions = positions;
    }

    public int[] getArrayPosition() {
        return positions;
    }

    public void showRequestNotification(String oppesiteUserName) {
        myGUI.showRequestNotification(oppesiteUserName);
    }

    public void refuseGameRequest(String oppesiteUserName) {
        myGUI.refuseGameRequest(oppesiteUserName);
    }

    public void sendGameRequest() {

    }

    public void acceptGameRequest(String oppesiteUserName) throws RemoteException {
        myModle.getServerInstance().startGameRoom(myModle.me.getPlayerUserName(), oppesiteUserName);
    }
}
