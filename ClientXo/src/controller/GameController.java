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
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import view.gameRoomFXMLBase;
import xml.GameComplexType;
import xml.StepComplexType;

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
    String roomName;
    boolean isYourTurn;

    public GameComplexType gameRecord;
    public ArrayList<StepComplexType> stepList;

    public GameController(MyGui g) {
        myGUI = g;
        stepList = new ArrayList<>();
//        readFromXML();

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
        
        roomName = myModle.gameRoom.getRoomName();

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
        if (isFinished == false) {
            if (isYourTurn == true && imgView.getImage() == null) {
                getPositionFromId(id);
            }
        }
    }

    public void modifyPositionsArray(String player, int posPlayed) {
//        int symbol;
//        if (inGamePlayer0.getPlayerName().equals(myModle.gameRoom.getPlayers().get(player))) {
//            symbol = inGamePlayer0.getPlayerSymbol();
//        } else {
//            symbol = inGamePlayer1.getPlayerSymbol();
//        }
        positions[posPlayed] = activePlayer;
        stepList.add(new StepComplexType(activePlayer, posPlayed));
        movesCounter++;

//        System.out.println("[" + posPlayed + "]" + " = " + symbol);
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
                    try {
                        myModle.getServerInstance()
                                .notifiyGameResult(roomName, inGamePlayer0.getPlayerName());
                        gameRecord = new GameComplexType(stepList, names.get(0) + " is a winner");
                        myModle.me.setPlayerScore(myModle.me.getPlayerScore() + 10);
                        winDialog(inGamePlayer0.getPlayerName());
                    } catch (RemoteException ex) {
                        Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return;
                } else {
                    System.out.println("O has won!");
                    isFinished = true;
                    try {
                        myModle.getServerInstance()
                                .notifiyGameResult(roomName, inGamePlayer1.getPlayerName());
                        gameRecord = new GameComplexType(stepList, names.get(1) + " is a winner");
                        winDialog(inGamePlayer1.getPlayerName());
                    } catch (RemoteException ex) {
                        Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return;
                }
            } else if (movesCounter == 9) {
                System.out.println("It's a draw!");
                try {
                    myModle.getServerInstance().notifiyGameResult(myModle.gameRoom.getRoomName(), "DRAW");
                    gameRecord = new GameComplexType(stepList, "DRAW");
                    saveGameRecordToXml();

                } catch (RemoteException ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                }
                isFinished = true;
            }
        }
    }

    public void winDialog(String winner) {
        ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION,
                    ".",
                    yes,
                    no);
            a.setTitle("Result");
            a.setHeaderText(winner + " has won!" + "\n" + "Do you want record this game");
            if (a.showAndWait().get() == yes) {
                saveGameRecordToXml();
                myGUI.createMainScreen();
            } else {
                myGUI.createMainScreen();
                myModle.me.setPlayerState("online");
            }
        }
        );
    }

    public void saveGameRecordToXml() {
        try {
            JAXBContext context = JAXBContext.newInstance(GameComplexType.class);
            Marshaller marshel = context.createMarshaller();
            Random rand = new Random();
            int pos = rand.nextInt(100);
            marshel.marshal(gameRecord, new File(names.get(0) + "VS" + names.get(1) + pos + ".xml"));
            System.out.println("Record is done");
            marshel.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        } catch (JAXBException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<StepComplexType> readFromXML() {
        ArrayList<StepComplexType> steps = new ArrayList<>();

        try {
            File file = new File("me7oVSsallam73.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            NodeList list = document.getElementsByTagName("step");
            int playerSymbol, posPlayed;

            for (int i = 0; i < list.getLength(); i++) {
                playerSymbol = Integer.valueOf(list.item(i).getFirstChild().getTextContent());
                posPlayed = Integer.valueOf(list.item(i).getLastChild().getTextContent());
                steps.add(new StepComplexType(playerSymbol, posPlayed));
            }

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return steps;

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
