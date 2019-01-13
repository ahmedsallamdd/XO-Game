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
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.SinglePlayerGui;

public class MyGui extends Application {

    public static GameController myController;

    Stage stage;
    private Scene scene;

    WelcomeFXMLBase welcome;
    LoginFXBase login;
    SignUpFXBase signUp;
    MainScreenBase mainScreen;
    SinglePlayerGui singlePlayerScreen;

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

    public void displayMessage(String myMessage) {
//        txtArea.append(myMessage + "\n");
    }

    public static void onImgViewClicked(MouseEvent mouseEvent) {
        //SelectedImgId = e.getPickResult().getIntersectedNode().getId();
        System.out.println(mouseEvent.getPickResult().getIntersectedNode().getId());
        myController.getSelectedImgView(mouseEvent.getPickResult().getIntersectedNode().getId());
//        mouseEvent.getPickResult().getIntersectedNode().getId();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //root = new Gui();

        welcome = new WelcomeFXMLBase(this);

        stage = primaryStage;
        scene = new Scene(welcome, 600, 800);

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void createLoginScreen() {
        login = new LoginFXBase(this);
        scene = new Scene(login, 600, 800);
        stage.setScene(scene);

    }

    public void createSignUpScreen() {
        signUp = new SignUpFXBase(this);
        scene = new Scene(signUp, 600, 800);
        stage.setScene(scene);

    }
    public boolean checkIfUserExists(String username, String password) {
        return myController.signIn(username, password);
    }

    public void createMainScreen() throws RemoteException {
        mainScreen = new MainScreenBase(this);
        getPlayerListData();
        scene = new Scene(mainScreen, 600, 800);
        stage.setScene(scene);
    }

    public boolean checkUserName(String userName) {
        return myController.checkUserName(userName);
    }

    public boolean signUp(String userName, String fullName, String email, String password) throws RemoteException {
        return myController.signUp(userName, fullName, email, password);
    }

    void getPlayerListData(ArrayList<PlayerList> playerList) {
        mainScreen.populateListView(playerList);
    }

    void getPlayerListData() throws RemoteException {
        myController.showPlayerList();
    }

    public static void main(String args[]) {
        launch(args);

//        myGame.startGUI();
        myController.myModle.getServerInstance();
        try {
            myController.myModle.getServerInstance().sendGameRequest("Abdo", "Sallam");
        } catch (RemoteException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    void showRequestNotification(String oppesiteUserName) {
        mainScreen.showRequestNotification(oppesiteUserName);
    }

    void refuseGameRequest(String oppesiteUserName) {
        mainScreen.refuseGameRequest(oppesiteUserName);
    }
}
