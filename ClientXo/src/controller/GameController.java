package controller;

import model.InGamePlayer;
import model.GameModle;
import commontxo.GameState;
import commontxo.Player;
import commontxo.PlayerList;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    public MyGui myGUI;
    public GameModle myModle;

    int[] positions = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = new int[][]{
        {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6},
        {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    private int movesCounter = 0;
    public boolean isFinished = false;
    public int activePlayer = 0;

    public ArrayList<String> names;
    public InGamePlayer inGamePlayer0;
    public InGamePlayer inGamePlayer1;
    String roomName;
    public boolean isYourTurn;

    public GameComplexType gameRecord;
    public ArrayList<StepComplexType> stepList;
    private String result;
    String header;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            =Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    
    public  static final Pattern VALID_PASSWORD_REGEX
            =Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-_]).{8,}$");
    public static final Pattern VALID_NAME_REGEX=Pattern.compile("^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$");
      public static final Pattern VALID_USER_NAME_REGEX=Pattern.compile("^[a-zA-Z0-9_.]+$");
    public GameController(MyGui g) {
        myGUI = g;

        try {
            myModle = new GameModle(this);
            inGamePlayer0 = new InGamePlayer();
            inGamePlayer1 = new InGamePlayer();
        } catch (RemoteException ex) {
            serverUnavilable();
        }
    }

    public void startGameRoom() {
        inGamePlayer0.setPlayerSymbol(0);
        inGamePlayer0.setIsMyTurn(true);

        inGamePlayer1.setPlayerSymbol(1);
        inGamePlayer1.setIsMyTurn(false);

        roomName = myModle.gameRoom.getRoomName();

        isYourTurn = myModle.me.getPlayerUserName().equals(inGamePlayer0.getPlayerName());
        System.out.println(isYourTurn);

        reDrawGameBoard();
        stepList = new ArrayList<>();
        
    }

    void getSelectedImgView(String id) {
        ImageView imgView = (ImageView) myGUI.multiPlayerScreen.gridPane.lookup("#" + id);
        if (isFinished == false) {
            if (isYourTurn == true && imgView.getImage() == null) {
                getPositionFromId(id);
            }
        }
    }

    public void modifyPositionsArray(int posPlayed) {
        positions[posPlayed] = activePlayer;
        stepList.add(new StepComplexType(activePlayer, posPlayed));
        movesCounter++;

        reDrawGameBoard();
        checkGameResult();
        switchTurns();
    }

    private void getPositionFromId(String id) {
        String[] components = id.split("_");
        int posPlayed = Integer.valueOf(components[1]);
        System.out.println(myModle.gameRoom.getPlayers().size());
        try {
            ArrayList<String> playerClientlist = new ArrayList(myModle.gameRoom.getPlayers().keySet());
            for (int i = 0; i < playerClientlist.size(); i++) {
                if (!inGamePlayer0.getPlayerName().equals(playerClientlist.get(i))) {
                    myModle.gameRoom.getPlayers().get(playerClientlist.get(i)).play(posPlayed);
                }
            }
            myModle.gameRoom.getPlayers().get(inGamePlayer0.getPlayerName()).play(posPlayed);
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
                        gameRecord = new GameComplexType(stepList, inGamePlayer0.getPlayerName() + " is the winner");

                        result = inGamePlayer0.getPlayerName();
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
                        gameRecord = new GameComplexType(stepList, inGamePlayer1.getPlayerName() + " is the winner");
                        result = inGamePlayer1.getPlayerName();
                    } catch (RemoteException ex) {
                        Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return;
                }
            }
        }
        if (isFinished == false && movesCounter == 9) {
            System.out.println("It's a draw!");
            try {
                if (myModle.me.getPlayerUserName().equals(inGamePlayer0.getPlayerName())) {
                    myModle.getServerInstance().notifiyGameResult(roomName, "DRAW");
                }
                gameRecord = new GameComplexType(stepList, "DRAW");
                isFinished = true;
                return;

            } catch (RemoteException ex) {
                serverUnavilable();
            }
        }
    }

    public void winDialog(String winner) {
        ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);
        if (!winner.equals("DRAW")) {
            header = winner + " has won!" + "\n" + "Do you want record this game";
        } else {
            header = "DRAW" + "\n" + "Do you want record this game";
        }

        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION,
                    "",
                    yes,
                    no);
            a.setTitle("Result");
            a.setHeaderText(header);
            myModle.currentShowenAlerts.add(a);
            Optional<ButtonType> option = a.showAndWait();
            if (option.get() == yes) {
                saveGameRecordToXml();
            }
            myModle.currentShowenAlerts.remove(a);
            resetGameState();
            myGUI.createMainScreen();

        });
    }

    public void saveGameRecordToXml() {
        try {
            JAXBContext context = JAXBContext.newInstance(GameComplexType.class);
            Marshaller marshel = context.createMarshaller();

            Date date = new Date();
            String strDateFormat = "hh-mm-ss-a";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            String formattedDate = dateFormat.format(date);

            marshel.marshal(gameRecord,
                    new File(".\\records\\"
                            + inGamePlayer0.getPlayerName() + "VS" + inGamePlayer1.getPlayerName() + " " + formattedDate + ".xml"));

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
            showAlert("Sorry", "Bad record", "Can't rendering this recording.");
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

    public String signIn(String userName, String password) {

        try {
            if (myModle.getServerInstance() != null) {
                Player p = myModle.getServerInstance().signIn(userName, password);
                if (p != null && !p.getPlayerState().equals("Already logged in")) {
                    myModle.me = p;
                    myModle.getServerInstance().register(myModle, userName);
                    return "hello";//means a successfull login process.
                } else if (p != null && p.getPlayerState().equals("Already logged in")) {
                    return p.getPlayerState();
                }
            } else {
                return "Server is down now." + "\n" + "Try again later.";
            }

        } catch (RemoteException ex) {
            serverUnavilable();
        }

        return "Wrong username or password!";
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
            myModle.onlineList = myModle.getServerInstance().initOnlineList();

            for (PlayerList pl : myModle.onlineList) {
                if (pl.getName().equals(myModle.me.getPlayerUserName())) {
                    myModle.onlineList.remove(pl);
                    break;
                }
            }
            myGUI.getPlayerListData(myModle.onlineList);
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

    public void setGameState(GameState gameState) {
        this.positions = gameState.getPositions();
        this.activePlayer = gameState.getActivePlayer();
        this.inGamePlayer0.setPlayerName(gameState.getInGamePlayer0());
        this.inGamePlayer1.setPlayerName(gameState.getInGamePlayer1());
    }

    public GameState getGameState() {
        return new GameState(inGamePlayer0.getPlayerName(), inGamePlayer1.getPlayerName(), positions, activePlayer);
    }

    public void showRequestNotification(String oppesiteUserName) {
        myGUI.showRequestNotification(oppesiteUserName);
    }

    public void refuseGameRequest(String oppesiteUserName) {
        myGUI.refuseGameRequest(oppesiteUserName);
    }

    public void acceptGameRequest(String oppesiteUserName) throws RemoteException {
        try {
            myModle.getServerInstance().startGameRoom(oppesiteUserName, myModle.me.getPlayerUserName());
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

    public void resetGameState() {
        positions = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};
        movesCounter = 0;
        isFinished = false;
        activePlayer = 0;
//        stepList.clear();
    }

    boolean setValidationForRegister(String userName, String name, String email, String password) {
 boolean validEmail,validPass,validName,validUserName;
        String alerMessage="Error ";
        if(userName.trim().length()==0||name.trim().length()==0|| email.trim().length()==0|| password.trim().length()==0)
        {
         Alert alerForSpaces = new Alert(Alert.AlertType.WARNING);
                alerForSpaces.setTitle("Warning");
                alerForSpaces.setHeaderText(null);

                alerForSpaces.setContentText("Enter Valid Data");
                alerForSpaces.show();
        
        
        }
        else
        {
        Matcher matcherUserName=VALID_USER_NAME_REGEX.matcher(userName);
          validUserName =matcherUserName.matches();

          if(!validUserName)
          alerMessage=alerMessage+", Invalid UserName";
          
            Matcher matcherName=VALID_NAME_REGEX.matcher(name);
          validName=(matcherName.find());
          if(!validName)
          alerMessage=alerMessage+", Invalid Name";
         
          
      Matcher matcherEmail = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
      validEmail = matcherEmail.find();
      if(!validEmail)
          alerMessage=alerMessage+", Invalid Email";
          Matcher matcherPass=VALID_PASSWORD_REGEX.matcher(password);
          validPass=matcherPass.find();
          if(!validPass)
          alerMessage=alerMessage+", Invalid Password";
              
        
          if(validUserName && validEmail && validName && validPass)
          {
              
             if(checkUserName(userName)){
                 Alert alerForUserName = new Alert(Alert.AlertType.WARNING);
                alerForUserName.setTitle("Warning");
                alerForUserName.setHeaderText(null);

                alerForUserName.setContentText("Username already exist, try another username!");
                alerForUserName.show();
                return false;
                
             }
             else 
                 return true;

          }
          else
          {
               Alert alerForValidate = new Alert(Alert.AlertType.WARNING);
                alerForValidate.setTitle("Warning");
                alerForValidate.setHeaderText(null);

                alerForValidate.setContentText(alerMessage);
                alerForValidate.show();
              
              
              return false;
          
          }
        
        
        }
        return false;


    }

  

}
