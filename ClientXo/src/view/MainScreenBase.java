package view;


import commontxo.NotificationGameResult;
import controller.MyGui;
import commontxo.PlayerList;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MainScreenBase extends AnchorPane {

    protected final Button btnPlayWithComputer;
    public final ListView<PlayerList> listView;
    protected final Label label;
    protected final Label label0;
    protected final Label label1;
    protected final Label label2;
    protected final Button button0;
    protected final ImageView imageView;

    MyGui myGui;

    public MainScreenBase(MyGui g) {

        myGui = g;
        btnPlayWithComputer = new Button();
        listView = new ListView<>();
        label = new Label();
        label0 = new Label();
        label1 = new Label();
        label2 = new Label();
        button0 = new Button();
        imageView = new ImageView();

        setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        btnPlayWithComputer.setLayoutX(318.0);
        btnPlayWithComputer.setLayoutY(165.0);
        btnPlayWithComputer.setMnemonicParsing(false);
        btnPlayWithComputer.setPrefHeight(66.0);
        btnPlayWithComputer.setPrefWidth(147.0);
        btnPlayWithComputer.setText("Single Player");

        btnPlayWithComputer.setOnAction((e -> {
            myGui.createSinglePlayerScreen();
            //controller.playWithComputer();

            // TODO: clean the matherfuken code from the singlePlayerGui to the controller
        }));

        listView.setLayoutX(14.0);
        listView.setLayoutY(14.0);
        listView.setPrefHeight(335.0);
        listView.setPrefWidth(200.0);

        label.setLayoutX(267.0);
        label.setLayoutY(18.0);
        label.setText("Player :");

        label0.setLayoutX(317.0);
        label0.setLayoutY(18.0);
        label0.setText("Sallam");

        label1.setLayoutX(405.0);
        label1.setLayoutY(18.0);
        label1.setText("Score :");

        label2.setLayoutX(459.0);
        label2.setLayoutY(18.0);
        label2.setText("1523");

        button0.setLayoutX(14.0);
        button0.setLayoutY(361.0);
        button0.setMnemonicParsing(false);
        button0.setPrefHeight(25.0);
        button0.setPrefWidth(169.0);
        button0.setText("Refresh");

        imageView.setFitHeight(17.0);
        imageView.setFitWidth(20.0);
        imageView.setLayoutX(561.0);
        imageView.setLayoutY(22.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("../images/Logout.png").toExternalForm()));
        imageView.setOnMousePressed(e -> {
            myGui.signOut();
        });

        getChildren().add(btnPlayWithComputer);
        getChildren().add(listView);
        getChildren().add(label);
        getChildren().add(label0);
        getChildren().add(label1);
        getChildren().add(label2);
        getChildren().add(button0);
        getChildren().add(imageView);
    }

    public void populateListView(ArrayList<PlayerList> playerList) {
        ObservableList<PlayerList> list = FXCollections.observableArrayList(playerList);

        listView.setItems(list);
        listView.setCellFactory((ListView<PlayerList> param) -> new XCell(this));

    }

    public void showRequestNotification(String playerUserName, NotificationGameResult result) {
        ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

        Platform.runLater(() -> {
            Alert a = new Alert(AlertType.WARNING,
                    ".",
                    yes,
                    no);
            a.setTitle("Request");
            a.setHeaderText(playerUserName + " wants to play with you");
            if (a.showAndWait().get() == yes) {
                try {
                    result.onReturn(true);
                } catch (RemoteException ex) {
                    Logger.getLogger(MainScreenBase.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (a.showAndWait().get() == no) {
                try {
                    result.onReturn(false);
                } catch (RemoteException ex) {
                    Logger.getLogger(MainScreenBase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void refuseGameRequest(String playerUserName) {
        ButtonType ok = new ButtonType("ok", ButtonBar.ButtonData.CANCEL_CLOSE);

        Platform.runLater(() -> {
            Alert a = new Alert(AlertType.WARNING,
                    ".",
                    ok);
            a.setTitle("Request Refuse");
            a.setHeaderText(playerUserName + " don't want to play with you");
        });
    }

    void sendGameRequest(String playerUserName, String opponentName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
