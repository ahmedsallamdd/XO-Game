package view;

import controller.MyGui;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class showRecordListFXMLBase extends AnchorPane {

    protected final BorderPane borderPane;
    protected final ListView listView;
    protected final GridPane gridPane;
    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final ColumnConstraints columnConstraints1;
    protected final RowConstraints rowConstraints;
    protected final RowConstraints rowConstraints0;
    protected final RowConstraints rowConstraints1;
    protected final ImageView imageView;
    protected final ImageView imageView0;
    MyGui myGui;

    public showRecordListFXMLBase(MyGui g) {

        myGui = g;
        
        borderPane = new BorderPane();
        listView = new ListView();
        gridPane = new GridPane();
        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        columnConstraints1 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        rowConstraints0 = new RowConstraints();
        rowConstraints1 = new RowConstraints();
        imageView = new ImageView();
        imageView0 = new ImageView();

        setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(600.0);
        setStyle("-fx-background-color: linear-gradient( #173551 0%,#62828f 50% ,#173551 100%);");

        borderPane.setPrefHeight(400.0);
        borderPane.setPrefWidth(600.0);

        BorderPane.setAlignment(listView, javafx.geometry.Pos.CENTER);
        listView.setPrefHeight(323.0);
        listView.setPrefWidth(196.0);
        listView.setStyle("-fx-background-color: #fff;");
        BorderPane.setMargin(listView, new Insets(5.0, 0.0, 0.0, 10.0));
        borderPane.setLeft(listView);

        BorderPane.setAlignment(gridPane, javafx.geometry.Pos.CENTER);
        gridPane.setPrefHeight(356.0);
        gridPane.setPrefWidth(301.0);

        columnConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints.setMinWidth(10.0);
        columnConstraints.setPrefWidth(100.0);

        columnConstraints0.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints0.setMinWidth(10.0);
        columnConstraints0.setPrefWidth(100.0);

        columnConstraints1.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints1.setMinWidth(10.0);
        columnConstraints1.setPrefWidth(100.0);

        rowConstraints.setMinHeight(10.0);
        rowConstraints.setPrefHeight(30.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints0.setMinHeight(10.0);
        rowConstraints0.setPrefHeight(30.0);
        rowConstraints0.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints1.setMinHeight(10.0);
        rowConstraints1.setPrefHeight(30.0);
        rowConstraints1.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
        BorderPane.setMargin(gridPane, new Insets(20.0));
        borderPane.setCenter(gridPane);

        BorderPane.setAlignment(imageView, javafx.geometry.Pos.TOP_LEFT);
        imageView.setFitHeight(41.0);
        imageView.setFitWidth(35.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        BorderPane.setMargin(imageView, new Insets(5.0, 0.0, 2.0, 5.0));
//        imageView.setImage(new Image(getClass().getResource("../images/login-arrow-symbol-entering-back-into-a-square.png").toExternalForm()));
        borderPane.setTop(imageView);

        BorderPane.setAlignment(imageView0, javafx.geometry.Pos.CENTER);
        imageView0.setFitHeight(33.0);
        imageView0.setFitWidth(38.0);
        imageView0.setPickOnBounds(true);
        imageView0.setPreserveRatio(true);
//        imageView0.setImage(new Image(getClass().getResource("../images/play-button.png").toExternalForm()));
        BorderPane.setMargin(imageView0, new Insets(0.0, 0.0, 5.0, 0.0));
        borderPane.setBottom(imageView0);

        gridPane.getColumnConstraints().add(columnConstraints);
        gridPane.getColumnConstraints().add(columnConstraints0);
        gridPane.getColumnConstraints().add(columnConstraints1);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane.getRowConstraints().add(rowConstraints0);
        gridPane.getRowConstraints().add(rowConstraints1);
        getChildren().add(borderPane);

    }
}
