/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientxo;

import commontxo.PlayerList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

class XCell extends ListCell<PlayerList> {

    HBox hbox = new HBox();
    Label lblUsername = new Label("(empty)");
    Label lblScore = new Label("(empty)");
    Pane pane = new Pane();
    Pane pane1 = new Pane();
    Pane pane2 = new Pane();
    Button btnJoinRoom = new Button("Challenge");
    ImageView imgViewState = new ImageView();
    PlayerList lastItem;

    public XCell() {
        super();
        hbox.getChildren().addAll(lblUsername, pane, lblScore, pane1, imgViewState, pane2, btnJoinRoom);
        HBox.setHgrow(pane, Priority.ALWAYS);
        HBox.setHgrow(pane1, Priority.ALWAYS);
        HBox.setHgrow(pane2, Priority.ALWAYS);
        btnJoinRoom.setOnAction((ActionEvent event) -> {
            System.out.println(lastItem + " : " + event);
        });
    }

    @Override
    protected void updateItem(PlayerList item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);  // No text in label of super class
        if (empty) {
            lastItem = null;
            setGraphic(null);
        } else {
            lastItem = item;
            lblScore.setText(String.valueOf(item.getScore()));
            lblUsername.setText(item.getName());
            imgViewState.setImage(item.getRoomName()==null?new Image("/images/available.png"): new Image("/images/busy.png"));

//            item != null ? item : );
            setGraphic(hbox);
        }
    }
}
