package view;

import commontxo.PlayerList;
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
//    String opponentName;

    public ListItem(MainScreenBase m) {
        super();
        btnJoinRoom.setStyle("-fx-background-color: #d76767; -fx-border-radius: 10;");
        btnJoinRoom.setTextFill(javafx.scene.paint.Color.WHITE);

        hbox.getChildren().addAll(lblUsername, pane, lblScore, pane1, imgViewState, pane2, btnJoinRoom);
        HBox.setHgrow(pane, Priority.ALWAYS);
        HBox.setHgrow(pane1, Priority.ALWAYS);
        HBox.setHgrow(pane2, Priority.ALWAYS);

        myMainScreenBase = m;
//        Platform.runLater(() -> {
//            opponentName = lblUsername.getText();
//        });
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
                    } catch (RemoteException ex) {
                        Logger.getLogger(ListItem.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        MyGui.myController.myModle.getServerInstance()
                                .spectateGame(MyGui.myController.myModle.me.getPlayerUserName(),
                                        item.getRoomName());
                    } catch (RemoteException ex) {
                        Logger.getLogger(ListItem.class.getName()).log(Level.SEVERE, null, ex);
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
