package controller;

import view.SignUpFXBase;
import view.LoginFXBase;
import view.MainScreenBase;
import view.WelcomeFXMLBase;
import commontxo.PlayerList;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.ShowRecordList;
import javafx.stage.WindowEvent;
import view.SinglePlayerGui;
import view.gameRoomFXMLBase;

public class MyGui extends Application {

    public static GameController myController;

    Stage stage;
    private Scene scene;

    WelcomeFXMLBase welcome;
    LoginFXBase login;
    SignUpFXBase signUp;
    MainScreenBase mainScreen;
    SinglePlayerGui singlePlayerScreen;
    gameRoomFXMLBase multiPlayerScreen;
    ShowRecordList replayScreen;

    int width = 1000;
    int height = 600;

    public MyGui() {
        myController = new GameController(this);
    }

    public MyGui(GameController c) {
        try {
            myController = c;
            //welcomeFXMLBase = new WelcomeFXMLBase(this);
        } catch (Exception ex) {
            Logger.getLogger(MyGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void onImgViewClicked(MouseEvent mouseEvent) {
        System.out.println(mouseEvent.getPickResult().getIntersectedNode().getId());
        myController.getSelectedImgView(mouseEvent.getPickResult().getIntersectedNode().getId());
//        myController.switchTurns();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //root = new Gui();

        welcome = new WelcomeFXMLBase(this);

        stage = primaryStage;
        scene = new Scene(welcome, width, height);

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Close");
            alert.setContentText("Are you sure to exit?");
            myController.myModle.currentShowenAlerts.add(alert);
            if (alert.showAndWait().get() == ButtonType.OK) {
                try {
                    MyGui.myController.leaveServer();
                    myController.myModle.currentShowenAlerts.remove(alert);
                } catch (RemoteException ex) {
                    Logger.getLogger(MyGui.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    Platform.exit();
                }
            } else {
                event.consume();
            }
        });
    }

    public void createLoginScreen() {
        login = new LoginFXBase(this);
        scene.setRoot(login);
        stage.setScene(scene);

    }

    public void createSignUpScreen() {
        signUp = new SignUpFXBase(this);
        scene.setRoot(signUp);
        stage.setScene(scene);

    }

    public String checkIfUserExists(String username, String password) {
        return myController.signIn(username, password);
    }

    public void createMainScreen() {
        mainScreen = new MainScreenBase(this);
        try {
            getPlayerListData();
        } catch (RemoteException ex) {
            Logger.getLogger(MyGui.class.getName()).log(Level.SEVERE, null, ex);
        }
        scene.setRoot(mainScreen);
        stage.setScene(scene);
    }

    public boolean checkUserName(String userName) {
        return myController.checkUserName(userName);
    }

    public boolean signUp(String userName, String fullName, String email, String password) throws RemoteException {
        return myController.signUp(userName, fullName, email, password);
    }

    public void getPlayerListData(ArrayList<PlayerList> playerList) {
        mainScreen.populateListView(playerList);
    }

    public void getPlayerListData() throws RemoteException {
        myController.showPlayerList();
    }

    public static void main(String args[]) {
        launch(args);
        myController.myModle.getServerInstance();

//        try {
////            myController.myModle.getServerInstance().sendGameRequest("Abdo", "Sallam");
//        } catch (RemoteException ex) {
//            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        Application.launch(myGame.myGUI.getClass(), args);
    }

    public void signOut() {
        try {
            myController.signOut();
            createLoginScreen();

        } catch (RemoteException ex) {
            Logger.getLogger(MyGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createSinglePlayerScreen(String parentScreen) {
        singlePlayerScreen = new SinglePlayerGui(this, parentScreen);
        scene.setRoot(singlePlayerScreen);
        stage.setScene(scene);
    }

    public void createReplayScreen() {
        replayScreen = new ShowRecordList(this);
        scene.setRoot(replayScreen);
        stage.setScene(scene);
    }

    void showRequestNotification(String oppesiteUserName) {
        mainScreen.showRequestNotification(oppesiteUserName);
    }

    void refuseGameRequest(String oppesiteUserName) {
        mainScreen.refuseGameRequest(oppesiteUserName);
    }

     public void createMultiPlayerGui() {
        multiPlayerScreen = new gameRoomFXMLBase(this);
        scene = new Scene(multiPlayerScreen, 
                this.multiPlayerScreen.getPrefWidth(), multiPlayerScreen.getPrefHeight());
        scene.setRoot(multiPlayerScreen);
        stage.setScene(scene);
    }

    public void createMultiPlayerGui(gameRoomFXMLBase multiplayerScreen) {
        this.multiPlayerScreen = multiplayerScreen;
        scene = new Scene(multiPlayerScreen, 
                this.multiPlayerScreen.getPrefWidth(), multiPlayerScreen.getPrefHeight());
        scene.setRoot(multiplayerScreen);
        stage.setScene(scene);
    }

//    public void displayMessage(String myMessage) {
//        multiPlayerScreen.displayMessage(myMessage);
//    }
//    public void sendMessage(String myUserName, String message) throws RemoteException {
//        myController.sendMessage(myUserName,message);
//    }
    public void displayMessage(String myMessage) {
        multiPlayerScreen.displayMessage(myMessage);
    }

    public void sendMessage(String text) {
        myController.sendMessage(text);
    }

    public void createWelcomeScreen() {
        welcome = new WelcomeFXMLBase(this);
        scene.setRoot(welcome);
        stage.setScene(scene);
    }

    void serverUnavilable() {
        ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION,
                    ".",
                    ok);
            a.setTitle("Offline");
            a.setHeaderText("Server is Down, try again later.");
            myController.myModle.currentShowenAlerts.add(a);
            a.showAndWait();
            createLoginScreen();
            myController.myModle.currentShowenAlerts.remove(a);
        });
    }

    void showAlert(String title, String headerText, String message) {
        ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION,
                    message,
                    ok);
            a.setTitle(title);
            a.setHeaderText(headerText);
            myController.myModle.currentShowenAlerts.add(a);
            a.showAndWait();
            if (myController.myModle.currentShowenAlerts.contains(a)) {
                myController.myModle.currentShowenAlerts.remove(a);
            }
        });
    }
}
