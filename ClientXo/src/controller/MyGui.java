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

    int width = 600;
    int height = 650;

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
            if (alert.showAndWait().get() == ButtonType.OK) {
                try {
                    MyGui.myController.leaveServer();
                } catch (RemoteException ex) {
                    Logger.getLogger(MyGui.class.getName()).log(Level.SEVERE, null, ex);
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

    public boolean checkIfUserExists(String username, String password) {
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

    public void createSinglePlayerScreen() {
        singlePlayerScreen = new SinglePlayerGui(this);
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
        scene.setRoot(multiPlayerScreen);
        stage.setScene(scene);
    }

    public void createMultiPlayerGui(gameRoomFXMLBase multiplayerScreen) {
        this.multiPlayerScreen = multiplayerScreen;
        scene = new Scene(multiPlayerScreen, 1000, 700);
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
            a.setTitle("Oflline");
            a.setHeaderText("Server is Down, try again later.");
            if (a.showAndWait().get() == ok) {
                createLoginScreen();
            }
//            signOut();
        }
        );
    }
}
