package view;

import commontxo.PlayerList;
import controller.MyGui;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class MainScreenBase extends AnchorPane {

    protected final Button btnPlayWithComputer;
    public final ListView<PlayerList> listView;
    protected final Label label;
    protected final Label lblUserName;
    protected final Label label1;
    protected final Label lblScore;
    protected final Button refresh;
    protected final ImageView imageView;
    protected final Button btnPreviousGames;
    protected final ImageView back;

    MyGui myGui;

    public MainScreenBase(MyGui g) {

        myGui = g;
        btnPlayWithComputer = new Button();
        listView = new ListView<>();
        label = new Label();
        lblUserName = new Label();
        label1 = new Label();
        lblScore = new Label();
        refresh = new Button();
        back = new ImageView();
        imageView = new ImageView();
        btnPreviousGames = new Button();

        setId("AnchorPane");
        setPrefHeight(450.0);
        setPrefWidth(650.0);
        setStyle("-fx-background-color: linear-gradient(#173551 0%,#62828F 50%,#173551 100%);");

        btnPlayWithComputer.setLayoutX(365.0);
        btnPlayWithComputer.setLayoutY(126.0);
        btnPlayWithComputer.setMnemonicParsing(false);
        btnPlayWithComputer.setPrefHeight(99.0);
        btnPlayWithComputer.setPrefWidth(218.0);
        btnPlayWithComputer.setStyle("-fx-background-color: #d76767; -fx-background-radius: 10;");
        btnPlayWithComputer.setText("Single Player");
        btnPlayWithComputer.setTextFill(javafx.scene.paint.Color.WHITE);
        btnPlayWithComputer.setFont(new Font("System Bold", 22.0));
        btnPlayWithComputer.setOnAction((e -> {
            myGui.createSinglePlayerScreen("main");
            //controller.playWithComputer();
            // TODO: remember to clean the matherfuken code from the singlePlayerGui to the controller
        }));

        listView.setLayoutX(14.0);
        listView.setLayoutY(12.0);
        listView.setPrefHeight(390.0);
        listView.setPrefWidth(274.0);

        label.setLayoutX(340.0);
        label.setLayoutY(16.0);
        label.setText("Player :");
        label.setTextFill(javafx.scene.paint.Color.valueOf("#ebe2e2"));
        label.setFont(new Font("System Bold", 14.0));

        lblUserName.setLayoutX(395.0);
        lblUserName.setLayoutY(16.0);
        lblUserName.setText("Sallam");
        lblUserName.setTextFill(javafx.scene.paint.Color.WHITE);
        lblUserName.setFont(new Font("System Bold", 14.0));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lblUserName.setText(String.valueOf(myGui.myController.myModle.me.getPlayerUserName()));
            }
        });

        label1.setLayoutX(477.0);
        label1.setLayoutY(16.0);
        label1.setText("Score :");
        label1.setTextFill(javafx.scene.paint.Color.WHITE);
        label1.setFont(new Font("System Bold", 14.0));

        lblScore.setLayoutX(530.0);
        lblScore.setLayoutY(16.0);
        lblScore.setText("123");
        lblScore.setTextFill(javafx.scene.paint.Color.WHITE);
        lblScore.setFont(new Font("System Bold", 14.0));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lblScore.setText(String.valueOf(myGui.myController.myModle.me.getPlayerScore()));
            }
        });

        refresh.setLayoutX(14.0);
        refresh.setLayoutY(411.0);
        refresh.setMnemonicParsing(false);
        refresh.setPrefHeight(30.0);
        refresh.setPrefWidth(274.0);
        refresh.setStyle("-fx-background-color: #d76767; -fx-border-radius: 10;");
        refresh.setText("Refresh");
        refresh.setTextFill(javafx.scene.paint.Color.WHITE);
        refresh.setFont(new Font("System Bold", 15.0));
        refresh.setOnAction((e -> {
            try {
                myGui.getPlayerListData();
            } catch (RemoteException ex) {
                Logger.getLogger(MainScreenBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }));

        imageView.setFitHeight(41.0);
        imageView.setFitWidth(37.0);
        imageView.setLayoutX(606.0);
        imageView.setLayoutY(7.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("../images/signout.png").toExternalForm()));
        imageView.setOnMousePressed(e -> {
            myGui.signOut();
        });

        btnPreviousGames.setLayoutX(368.0);
        btnPreviousGames.setLayoutY(258.0);
        btnPreviousGames.setMnemonicParsing(false);
        btnPreviousGames.setPrefHeight(99.0);
        btnPreviousGames.setPrefWidth(218.0);
        btnPreviousGames.setStyle("-fx-background-color: #d76767; -fx-background-radius: 10;");
        btnPreviousGames.setText("Previous Games");
        btnPreviousGames.setTextFill(javafx.scene.paint.Color.WHITE);
        btnPreviousGames.setFont(new Font("System Bold", 22.0));
        btnPreviousGames.setOnAction(event -> {
            myGui.createReplayScreen();
        });

        getChildren().add(btnPlayWithComputer);
        getChildren().add(listView);
        getChildren().add(label);
        getChildren().add(lblUserName);
        getChildren().add(label1);
        getChildren().add(lblScore);
        getChildren().add(refresh);
        getChildren().add(imageView);
        getChildren().add(btnPreviousGames);

    }

    public void populateListView(ArrayList<PlayerList> playerList) {
        ObservableList<PlayerList> list = FXCollections.observableArrayList(playerList);
        listView.setItems(list);
        listView.setCellFactory((ListView<PlayerList> param) -> new ListItem(this));
    }

    public void showRequestNotification(String oppesiteUserName) {
        ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.WARNING,
                    ".",
                    yes,
                    no);
            a.setTitle("Request");
            a.setHeaderText(oppesiteUserName + " wants to play with you");
            myGui.myController.myModle.currentShowenAlerts.add(a);
            if (a.showAndWait().get() == yes) {
                try {
                    myGui.myController.myModle.currentShowenAlerts.remove(a);
                    myGui.myController.myModle.acceptGameRequest(oppesiteUserName);
                } catch (RemoteException ex) {
                   MyGui.myController.showAlert("Error", "No Connection", "This Client Not responde");
                }
            } else {
                try {
                    myGui.myController.myModle.currentShowenAlerts.remove(a);
                    myGui.myController.myModle.getServerInstance()
                            .refuseGameRequest(myGui.myController.myModle.me.getPlayerUserName(), oppesiteUserName);
                } catch (RemoteException ex) {
                    MyGui.myController.serverUnavilable();
                }
            }
        });
    }

    public void refuseGameRequest(String playerUserName) {
        ButtonType ok = new ButtonType("ok", ButtonBar.ButtonData.CANCEL_CLOSE);

        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.WARNING,
                    ".",
                    ok);
            a.setTitle("Request Refuse");
            a.setHeaderText(playerUserName + " don't want to play with you");
            myGui.myController.myModle.currentShowenAlerts.add(a);
            a.showAndWait();
            myGui.myController.myModle.currentShowenAlerts.remove(a);

        });
    }
//
//    void sendGameRequest(String playerUserName, String opponentName) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

}
