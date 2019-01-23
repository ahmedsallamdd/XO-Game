package view;

import commontxo.PlayerList;
import commontxo.ServerNullExeption;
import controller.MyGui;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

class ListItem extends ListCell<PlayerList> {

    HBox hbox = new HBox();
    Label lblUsername = new Label("(empty)");
    Label lblScore = new Label("(empty)");
    Pane pane = new Pane();
    Pane pane1 = new Pane();
    Pane pane2 = new Pane();
    Button btnJoinRoom = new Button("Challenge");
    ImageView imgViewState = new ImageView();
    PlayerList lastItem;
    MainScreenBase myMainScreenBase;

    public ListItem(MainScreenBase m) {
        super();
        btnJoinRoom.setStyle("-fx-background-color: #0b3c49; -fx-border-radius: 10;");
        btnJoinRoom.setTextFill(javafx.scene.paint.Color.valueOf("#f3ffbd"));
        hbox.setStyle("-fx-background-radius: 10;");
        hbox.setStyle("-fx-border-radius: 10;");

        hbox.getChildren().addAll(lblUsername, pane, lblScore, pane1, imgViewState, pane2, btnJoinRoom);
        HBox.setHgrow(pane, Priority.ALWAYS);
        HBox.setHgrow(pane1, Priority.ALWAYS);
        HBox.setHgrow(pane2, Priority.ALWAYS);

        myMainScreenBase = m;
    }

    @Override
    protected void updateItem(PlayerList item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);  // No text in label of super class
        if (empty) {
            lastItem = null;
            setGraphic(null);
        } else {
            btnJoinRoom.setOnAction((e) -> {
                if (btnJoinRoom.getText().equals("Challenge")) {
                    try {
                        MyGui.myController.myModle.getServerInstance()
                                .sendGameRequest(
                                        MyGui.myController.myModle.me.getPlayerUserName(),
                                        item.getName());
                    } catch (ServerNullExeption | RemoteException ex) {
                        MyGui.myController.serverUnavilable();
                    }
                } else {
                    try {
                        MyGui.myController.myModle.getServerInstance()
                                .spectateGame(MyGui.myController.myModle.me.getPlayerUserName(),
                                        item.getRoomName());
                    } catch (ServerNullExeption | RemoteException ex) {
                        MyGui.myController.serverUnavilable();
                    }
                }
            });
            lastItem = item;
            lblScore.setText(String.valueOf(item.getScore()));
            lblUsername.setText(item.getName());
            if (item.getRoomName() != null) {
                btnJoinRoom.setText("Spectate");
            }
            imgViewState.setImage(item.getRoomName() == null
                    ? new Image("/images/available.png") : new Image("/images/busy.png"));

            setGraphic(hbox);
        }
    }
}
