package view;

import commontxo.PlayerList;
import commontxo.ServerNullExeption;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    protected final VBox vBox0;
    protected final VBox vBox1;
    protected final HBox hBox;
    protected final DropShadow dropShadow;
    protected final DropShadow dropShadow0;
    protected final HBox hBox0;
    protected final DropShadow dropShadow1;
    protected final DropShadow dropShadow2;
    protected final VBox vBox;

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
        vBox = new VBox();
        vBox0 = new VBox();
        vBox1 = new VBox();
        dropShadow = new DropShadow();
        dropShadow0 = new DropShadow();
        dropShadow1 = new DropShadow();
        dropShadow2 = new DropShadow();
        hBox0 = new HBox();
        hBox = new HBox();

        setId("AnchorPane");
        setPrefHeight(600.0);
        setPrefWidth(650.0);
        setStyle("-fx-background-color: linear-gradient( #247ba0 0%,#70c1b3 50% ,#247ba0 100%);");

        AnchorPane.setBottomAnchor(vBox, 130.0);
        AnchorPane.setRightAnchor(vBox, 40.0);
        AnchorPane.setTopAnchor(vBox, 190.0);
        vBox.setLayoutX(389.0);
        vBox.setLayoutY(178.0);
        vBox.setSpacing(20.0);

        btnPlayWithComputer.setMnemonicParsing(false);
        btnPlayWithComputer.setPrefHeight(150.0);
        btnPlayWithComputer.setPrefWidth(283.0);
        btnPlayWithComputer.setStyle("-fx-background-color: #0b3c49; -fx-background-radius: 10;");
        btnPlayWithComputer.setText("Single Player");
        btnPlayWithComputer.setTextFill(javafx.scene.paint.Color.WHITE);
        btnPlayWithComputer.setFont(new Font("System Bold", 22.0));
        btnPlayWithComputer.setOnAction((e -> {
            myGui.createSinglePlayerScreen("main");
            //controller.playWithComputer();
            // TODO: remember to clean the matherfuken code from the singlePlayerGui to the controller
        }));

        listView.setPrefHeight(440.0);
        listView.setPrefWidth(247.0);
        listView.setStyle("-fx-background-radius: 10;");
        listView.setStyle("-fx-border-radius: 10;");

        label.setText("Player :");
        label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        label.setTextFill(javafx.scene.paint.Color.valueOf("#03c2ff"));
        label.setFont(new Font("System Bold", 20.0));

        label.setEffect(dropShadow);

        lblUserName.setText("Sallam");
        lblUserName.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        lblUserName.setTextFill(javafx.scene.paint.Color.WHITE);
        lblUserName.setFont(new Font("System Bold", 20.0));

        lblUserName.setEffect(dropShadow0);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lblUserName.setText(String.valueOf(myGui.myController.myModle.me.getPlayerUserName()));
            }
        });
        hBox0.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        hBox0.setPrefWidth(247.0);
        hBox0.setSpacing(14.0);

        label1.setAlignment(javafx.geometry.Pos.CENTER);
        label1.setText("Score :");
        label1.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        label1.setTextFill(javafx.scene.paint.Color.valueOf("#03c2ff"));
        label1.setFont(new Font("System Bold", 20.0));

        label1.setEffect(dropShadow1);

        lblScore.setText("123");
        lblScore.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        lblScore.setTextFill(javafx.scene.paint.Color.WHITE);
        lblScore.setFont(new Font("System Bold", 20.0));

        lblScore.setEffect(dropShadow2);

        Platform.runLater(() -> {
            lblScore.setText(String.valueOf(myGui.myController.myModle.me.getPlayerScore()));
        });

        refresh.setMnemonicParsing(false);
        refresh.setPrefHeight(31.0);
        refresh.setPrefWidth(247.0);
        refresh.setStyle("-fx-background-color: #0b3c49; -fx-background-radius: 10;");
        refresh.setText("Refresh List");
        refresh.setTextFill(javafx.scene.paint.Color.WHITE);
        refresh.setFont(new Font("System Bold", 20.0));
        refresh.setOnAction((e -> {
            try {
                myGui.getPlayerListData();
            } catch (ServerNullExeption | RemoteException ex) {
                myGui.myController.serverUnavilable();
            }
        }));

        AnchorPane.setRightAnchor(imageView, 13.0);
        imageView.setFitHeight(31.0);
        imageView.setFitWidth(35.0);
        imageView.setLayoutX(610.0);
        imageView.setLayoutY(25.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("../images/signout.png").toExternalForm()));
        imageView.setOnMousePressed(e -> {
            try {
                myGui.signOut();
            } catch (ServerNullExeption ex) {
                myGui.myController.serverUnavilable();
            }
        });
        AnchorPane.setBottomAnchor(vBox0, 25.0);
        AnchorPane.setLeftAnchor(vBox0, 35.0);
        AnchorPane.setTopAnchor(vBox0, 25.0);
        vBox0.setLayoutX(14.0);
        vBox0.setLayoutY(12.0);
        vBox0.setPrefWidth(247.0);
        vBox0.setSpacing(7.0);

        hBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        hBox.setPrefHeight(15.0);
        hBox.setPrefWidth(247.0);
        hBox.setSpacing(14.0);

        btnPreviousGames.setMnemonicParsing(false);
        btnPreviousGames.setPrefHeight(150.0);
        btnPreviousGames.setPrefWidth(283.0);
        btnPreviousGames.setStyle("-fx-background-color: #0b3c49; -fx-background-radius: 10;");
        btnPreviousGames.setText("My Records");
        btnPreviousGames.setTextFill(javafx.scene.paint.Color.WHITE);
        btnPreviousGames.setFont(new Font("System Bold", 22.0));
        btnPreviousGames.setOnAction(event -> {
            myGui.createReplayScreen();
        });

        vBox.getChildren().add(btnPlayWithComputer);
        vBox.getChildren().add(btnPreviousGames);
        getChildren().add(vBox);
        getChildren().add(imageView);
        hBox.getChildren().add(label);
        hBox.getChildren().add(lblUserName);
        vBox1.getChildren().add(hBox);
        hBox0.getChildren().add(label1);
        hBox0.getChildren().add(lblScore);
        vBox1.getChildren().add(hBox0);
        vBox0.getChildren().add(vBox1);
        vBox0.getChildren().add(listView);
        vBox0.getChildren().add(refresh);
        getChildren().add(vBox0);

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
                    myGui.myController.resetGameState();
                    myGui.myController.myModle.acceptGameRequest(oppesiteUserName);
                } catch (RemoteException ex) {
                    MyGui.myController.showAlert("Error", "No Connection", "This Client Not responde");
                }
            } else {
                try {
                    myGui.myController.myModle.currentShowenAlerts.remove(a);
                    myGui.myController.myModle.getServerInstance()
                            .refuseGameRequest(myGui.myController.myModle.me.getPlayerUserName(), oppesiteUserName);
                } catch (ServerNullExeption | RemoteException ex) {
                    myGui.myController.serverUnavilable();
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
