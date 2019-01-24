/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 *
 * @author Sallam
 */
public class ToggleBtn extends Label {

    public SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(true);
    Button btnToggle;
    public ToggleBtn() {
        btnToggle = new Button();
        btnToggle.setStyle("-fx-background-color: #0b3c49; -fx-background-radius: 10;");

        btnToggle.setPrefWidth(40);
        btnToggle.setOnAction((ActionEvent t) -> {
            switchedOn.set(!switchedOn.get());
        });
        setGraphic(btnToggle);

        switchedOn.addListener((ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) -> {
            if (t1) {
                setText("Easy");
                setFont(new Font("System Bold", 18.0));
                setStyle("-fx-background-color: green;-fx-text-fill:white; -fx-background-radius: 10;");
                setContentDisplay(ContentDisplay.RIGHT);
            } else {
                setText("Hard");
                setFont(new Font("System Bold", 18.0));
                setStyle("-fx-background-color: red;-fx-text-fill:white; -fx-background-radius: 10;");
                setContentDisplay(ContentDisplay.LEFT);
            }
        });
        switchedOn.set(false);
    }

    public SimpleBooleanProperty switchOnProperty() {
        return switchedOn;
    }
}
