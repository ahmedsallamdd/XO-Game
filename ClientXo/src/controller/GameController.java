/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.InGamePlayer;
import model.GameModle;
import commontxo.ClientCallBack;
import commontxo.Player;
import commontxo.PlayerList;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Optional;
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

    //TODO Send Game Variable to all spectator
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
    private String result;
    String header;

    public GameController(MyGui g) {
        myGUI = g;
        stepList = new ArrayList<>();
//        readFromXML();

        try {
            myModle = new GameModle(this);
            inGamePlayer0 = new InGamePlayer();
            inGamePlayer1 = new InGamePlayer();

        } catch (RemoteException ex) {
            serverUnavilable();
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
        inGamePlayer1.setIsMyTurn(false);

        roomName = myModle.gameRoom.getRoomName();

        isYourTurn = myModle.me.getPlayerUserName().equals(names.get(0));
        System.out.println(isYourTurn);

        reDrawGameBoard();
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
            serverUnavilable();
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

    public String constructImgeViewId(int pos) {
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
                        if (myModle.me.getPlayerUserName().equals(inGamePlayer0.getPlayerName())) {
                            myModle.getServerInstance()
                                    .notifiyGameResult(roomName, inGamePlayer0.getPlayerName());
                            myModle.me.setPlayerScore(myModle.me.getPlayerScore() + 10);
                        }
                        gameRecord = new GameComplexType(stepList, names.get(0) + " is a winner");

                        result = inGamePlayer0.getPlayerName();
//                        winDialog(result);
                    } catch (RemoteException ex) {
                        Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return;
                } else {
                    System.out.println("O has won!");
                    isFinished = true;
                    try {
                        if (myModle.me.getPlayerUserName().equals(inGamePlayer0.getPlayerName())) {
                            myModle.getServerInstance()
                                    .notifiyGameResult(roomName, inGamePlayer1.getPlayerName());
                        }
                        if (myModle.me.getPlayerUserName().equals(inGamePlayer1.getPlayerName())) {
                            myModle.me.setPlayerScore(myModle.me.getPlayerScore() + 10);
                        }
                        gameRecord = new GameComplexType(stepList, names.get(1) + " is a winner");
                        result = inGamePlayer1.getPlayerName();
//                        winDialog(result);
                    } catch (RemoteException ex) {
                        Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return;
                }
            } else if (movesCounter == 9) {
                System.out.println("It's a draw!");
                try {
                    if (myModle.me.getPlayerUserName().equals(inGamePlayer0.getPlayerName())) {
                        myModle.getServerInstance().notifiyGameResult(roomName, "DRAW");
                    }
                    gameRecord = new GameComplexType(stepList, "DRAW");
//                    winDialog("DRAW");
                    isFinished = true;
                    return;

                } catch (RemoteException ex) {
                    serverUnavilable();
                }
            }
        }
    }

    public void winDialog(String winner) {
        if (gameRoomFXMLBase.timer != null) {
            gameRoomFXMLBase.timer.cancel();
        }
        ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);
        if (!winner.equals("DRAW")) {
            header = winner + " has won!" + "\n" + "Do you want record this game";
        } else {
            header = "DRAW";
        }

        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION,
                    ".",
                    yes,
                    no);
            a.setTitle("Result");
            a.setHeaderText(header);
            myModle.currentShowenAlerts.add(a);
            Optional<ButtonType> option = a.showAndWait();
            if (option.get() == yes) {
                saveGameRecordToXml();
                myGUI.createMainScreen();
                myModle.currentShowenAlerts.remove(a);
            } else if (option.get() == no) {
                myGUI.createMainScreen();
                myModle.currentShowenAlerts.remove(a);
            } else {
                myModle.currentShowenAlerts.remove(a);
            }

        });
        positions = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};
    }

    public void saveGameRecordToXml() {
        try {
            JAXBContext context = JAXBContext.newInstance(GameComplexType.class);
            Marshaller marshel = context.createMarshaller();
            Random rand = new Random();
            int pos = rand.nextInt(100);
            marshel.marshal(gameRecord,
                    new File("D:\\java game\\Xo-Java-Project-master (2)\\Xo-Java-Project-master\\ClientXo\\records\\"
                            + names.get(0) + "VS" + names.get(1) + pos + ".xml"));
            System.out.println("Record is done");
            marshel.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        } catch (JAXBException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public GameComplexType readFromXML(File file) {
        ArrayList<StepComplexType> steps = new ArrayList<>();
        GameComplexType gameRecord = new GameComplexType();

        try {
//            Ffile = new File("me7oVSsallam73.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            NodeList list = document.getElementsByTagName("step");
            String result = document.getElementsByTagName("result").item(0).getTextContent();
            int playerSymbol, posPlayed;

            for (int i = 0; i < list.getLength(); i++) {
                playerSymbol = Integer.valueOf(list.item(i).getFirstChild().getTextContent());
                posPlayed = Integer.valueOf(list.item(i).getLastChild().getTextContent());
                steps.add(new StepComplexType(playerSymbol, posPlayed));
            }

            gameRecord.setResult(result);
            gameRecord.setStep(steps);

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gameRecord;
    }

    //Client Exit
    public void leaveServer() throws RemoteException {
        if (myModle.me != null) {
            if (myModle.gameRoom != null) {
                withdraw();
            }
            myModle.getServerInstance().leaveServer(myModle.me.getPlayerUserName());
        }
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
            myModle.gameRoom = null;
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
            serverUnavilable();
        }

        return false;
    }

    public boolean checkUserName(String userName) {
        try {
            return myModle.getServerInstance().checkUserName(userName);
        } catch (RemoteException ex) {
            serverUnavilable();
        }
        return false;
    }

    void playWithComputer() {
        System.out.println("playing with computer");
    }

    public void showPlayerList() throws RemoteException {
        try {
            ArrayList<PlayerList> list = myModle.getServerInstance().initOnlineList();

            for (PlayerList pl : list) {
                if (pl.getName().equals(myModle.me.getPlayerUserName())) {
                    list.remove(pl);
                    break;
                }

            }
            myGUI.getPlayerListData(list);
        } catch (java.rmi.ConnectException e) {
            serverUnavilable();
        }

    }

    public void signOut() throws RemoteException {
        try {
            myModle.getServerInstance().signOut(myModle.me);
        } catch (java.rmi.ConnectException e) {
            serverUnavilable();
        }
        leaveServer();
        myModle.me = null;
        myModle.gameRoom = null;
        myModle.clearServer();
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

    public void acceptGameRequest(String oppesiteUserName) throws RemoteException {
        try {
            myModle.getServerInstance().startGameRoom(myModle.me.getPlayerUserName(), oppesiteUserName);
        } catch (java.rmi.ConnectException e) {
            serverUnavilable();
        }
    }

//    public void displayMessage(String myMessage) {
//        myGUI.displayMessage(myMessage);
//    }
//
//    void sendMessage(String myUserName, String message) throws RemoteException {
//        if (myModle.chatRooms.size() > 0) {
//            String oppesiteUserName = new ArrayList<>(myModle.chatRooms.keySet()).get(0);
//            myModle.chatRooms.get(oppesiteUserName).getOtherClients()
//                    .receiveMessage(myUserName, message);
//
//            //send for my self
//            if (myModle.chatRooms.get(oppesiteUserName) != null) {
//                myModle.chatRooms.get(oppesiteUserName).setChat(myModle.chatRooms.get(oppesiteUserName).getChat() + myUserName + ": " + message + "\n");
//                myModle.myController.displayMessage(myModle.chatRooms.get(oppesiteUserName).getChat());
//            }
//        }
//    }
    public void displayMessage(String myMessage) {
        myModle.chatRooms.get(new ArrayList<>(myModle.chatRooms.keySet()).get(0)).setChat(myModle.chatRooms.get(new ArrayList<>(myModle.chatRooms.keySet()).get(0)).getChat() + "\n" + myMessage);
        myGUI.displayMessage(myModle.chatRooms.get(new ArrayList<>(myModle.chatRooms.keySet()).get(0)).getChat());
    }

    public void sendMessage(String message) {
        try {
            displayMessage(myModle.me.getPlayerUserName() + ":" + message);

            myModle.chatRooms.get(new ArrayList<>(myModle.chatRooms.keySet()).get(0)).getOtherClients().sendMessage(myModle.me.getPlayerUserName(), message);
        } catch (RemoteException ex) {
            serverUnavilable();
        }
    }

    public void serverUnavilable() {
        myGUI.serverUnavilable();
    }

    public void showAlert(String title, String headerText, String message) {
        myGUI.showAlert(title, headerText, message);
    }
}
