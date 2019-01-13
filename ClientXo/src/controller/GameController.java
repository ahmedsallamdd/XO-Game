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
import commontxo.NotificationGameResult;
import commontxo.Player;
import commontxo.PlayerList;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameController {

    MyGui myGUI;
    GameModle myModle;

    int[] positions = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = new int[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6},
    {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    private int movesCounter = 0;
    private boolean isFinished = false;
    int activePlayer = 0;

    ArrayList<String> names;
    InGamePlayer inGamePlayer0;
    InGamePlayer inGamePlayer1;

    public GameController(MyGui g) {
//        myGUI = new MyGui(this);
//        Application.launch(MyGui.class);
          myGUI = g;
        try {
            myModle = new GameModle(this);
            inGamePlayer0 = new InGamePlayer();
            inGamePlayer1 = new InGamePlayer();

        } catch (RemoteException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void startGameRoom() {
        names = new ArrayList<String>(myModle.gameRoom.getPlayers().keySet());

        inGamePlayer0.setPlayerName(names.get(0));
        inGamePlayer0.setPlayerSymbol(0);

        inGamePlayer1.setPlayerName(names.get(1));
        inGamePlayer1.setPlayerSymbol(1);
    }

    public void displayMessage(String myMessage) {
        myGUI.displayMessage(myMessage);
    }

//    public void unRegister() {
//        try {
//            myModle.getServerInstance().unRegister(myModle, "Abdo");
//        } catch (RemoteException ex) {
//            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    void getSelectedImgView(String id) {
        getPositionFromId(id);
    }

    public void modifyPositionsArray(String player, int posPlayed) {
        int symbol;
        if (inGamePlayer0.getPlayerName().equals(myModle.gameRoom.getPlayers().get(player))) {
            symbol = inGamePlayer0.getPlayerSymbol();
        } else {
            symbol = inGamePlayer1.getPlayerSymbol();
        }
        positions[posPlayed] = symbol;
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
                if (activePlayer == 0) {
                    client.play(inGamePlayer0.getPlayerName(), posPlayed);
                } else {
                    client.play(inGamePlayer1.getPlayerName(), posPlayed);
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void switchTurns() {
        if (activePlayer == 1) {
            activePlayer = 0;
        } else {
            activePlayer = 1;
        }
    }

    public void reDrawGameBoard() {
        String imgViewId;
        ImageView imgView;
        Gui newRoot = new Gui();
        for (int i = 0; i < positions.length; i++) {
            if (positions[i] != 2) {
                imgViewId = constructImgeViewId(i);
                System.out.println(imgViewId);
//                imgView = (ImageView) myGUI.root.gridPane.lookup("#" + imgViewId);
                imgView = (ImageView) newRoot.gridPane.lookup("#" + imgViewId);
                if (positions[i] == 0) {
                    System.out.println("hi");
                    imgView.setImage(new Image("/images/X_image.png"));
                } else {
                    imgView.setImage(new Image("/images/O_image.png"));
                }
            }
        }
        Scene newScene = new Scene(newRoot, 600, 450);
        myGUI.stage.setScene(newScene);
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
                    //movesCounter=9;
                    isFinished = true;
                    return;
                } else {
                    System.out.println("O has won!");
                    //movesCounter=9;   
                    isFinished = true;
                    return;
                }
            } else if (movesCounter == 9) {
                System.out.println("It's a draw!");
                isFinished = true;
            }
        }
        if (movesCounter == 9) {
            System.out.println("It's a draw!");
            isFinished = true;
        }
    }
    //current player surrender or leave spectate.

    public void withdraw(String myUserName) throws RemoteException {
        //remove Mysilfe ...
        ArrayList<String> temp = new ArrayList<>(myModle.gameRoom.getPlayers().keySet());
        if (temp.indexOf(myUserName) > 1) {
            myModle.gameRoom.getPlayers().forEach((e, client) -> {
                try {
                    client.leftGameRoom(myUserName);
                } catch (RemoteException ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            myModle.leaveGameRoom();
            myModle.getServerInstance().removeClientMapGameRoom(myUserName);
            myModle.getServerInstance().removePlayerFromGameRoom(myUserName, myModle.gameRoom.getRoomName());
        } else {
            temp.remove(myUserName);
            myModle.getServerInstance().notifiyGameResult(myModle.gameRoom.getRoomName(), temp.get(0));
        }
    }

//    public static void main(String args[]) {
//        GameController myGame = new GameController();
////        myGame.startGUI();
//        myGame.myModle.getServerInstance();
//        try {
//            myGame.myModle.getServerInstance().sendGameRequest("Abdo", "Sallam");
//        } catch (RemoteException ex) {
//            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
//        }
////        Application.launch(myGame.myGUI.getClass(), args);
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                
//            }
//        });
//    }

    public void signUp(String userName, String name, String email, String password) throws RemoteException {
        myModle.getServerInstance().signUp(userName, name, password, email);
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
        System.out.println("mgldfk");
    }

    public void showPlayerList() throws RemoteException {
        myGUI.getPlayerListData(myModle.getServerInstance().initOnlineList());
    }

    void signOut() throws RemoteException {
        myModle.getServerInstance().signOut(myModle.me);
    }

    public void setArrayPosition(int[] positions) {
        this.positions=positions;
    }

    public int[] getArrayPosition() {
        return positions;
    }


    public void showRequestNotification(String playerUserName, NotificationGameResult result) {
        myGUI.showRequestNotification(playerUserName,result);
    }

    public void refuseGameRequest(String playerUserName) {
        myGUI.refuseGameRequest(playerUserName);
    }

}
