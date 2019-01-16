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

public class ShowRecordList extends AnchorPane {

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
    protected final ImageView imageView1;
    protected final ImageView imageView2;
    protected final ImageView imageView3;
    protected final ImageView imageView4;
    protected final ImageView imageView5;
    protected final ImageView imageView6;
    protected final ImageView imageView7;
    protected final ImageView playButton;
    protected final ImageView imageView9;

    MyGui myGui;
    public ShowRecordList(MyGui g) {

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
        imageView1 = new ImageView();
        imageView2 = new ImageView();
        imageView3 = new ImageView();
        imageView4 = new ImageView();
        imageView5 = new ImageView();
        imageView6 = new ImageView();
        imageView7 = new ImageView();
        playButton = new ImageView();
        imageView9 = new ImageView();

        setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(600.0);
        setStyle("-fx-background-color: linear-gradient( #173551 0%,#62828f 50% ,#173551 100%);;");

        borderPane.setPrefHeight(400.0);
        borderPane.setPrefWidth(600.0);

        BorderPane.setAlignment(listView, javafx.geometry.Pos.CENTER);
        listView.setPrefHeight(323.0);
        listView.setPrefWidth(196.0);
        listView.setStyle("-fx-background-color: #fff;");
        BorderPane.setMargin(listView, new Insets(5.0, 0.0, 0.0, 10.0));
        borderPane.setLeft(listView);

        BorderPane.setAlignment(gridPane, javafx.geometry.Pos.CENTER);
        gridPane.setGridLinesVisible(true);
        gridPane.setPrefHeight(307.0);
        gridPane.setPrefWidth(349.0);

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

        imageView.setFitHeight(94.0);
        imageView.setFitWidth(118.0);
        imageView.setId("img_0");
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);

        GridPane.setRowIndex(imageView0, 1);
        imageView0.setFitHeight(94.0);
        imageView0.setFitWidth(118.0);
        imageView0.setId("img_3");
        imageView0.setPickOnBounds(true);
        imageView0.setPreserveRatio(true);

        GridPane.setColumnIndex(imageView1, 1);
        GridPane.setRowIndex(imageView1, 1);
        imageView1.setFitHeight(94.0);
        imageView1.setFitWidth(118.0);
        imageView1.setId("img_4");
        imageView1.setPickOnBounds(true);
        imageView1.setPreserveRatio(true);

        GridPane.setColumnIndex(imageView2, 2);
        GridPane.setRowIndex(imageView2, 1);
        imageView2.setFitHeight(94.0);
        imageView2.setFitWidth(118.0);
        imageView2.setId("img_5");
        imageView2.setPickOnBounds(true);
        imageView2.setPreserveRatio(true);

        GridPane.setRowIndex(imageView3, 2);
        imageView3.setFitHeight(94.0);
        imageView3.setFitWidth(118.0);
        imageView3.setId("img_6");
        imageView3.setPickOnBounds(true);
        imageView3.setPreserveRatio(true);

        GridPane.setColumnIndex(imageView4, 1);
        GridPane.setRowIndex(imageView4, 2);
        imageView4.setFitHeight(94.0);
        imageView4.setFitWidth(118.0);
        imageView4.setId("img_7");
        imageView4.setPickOnBounds(true);
        imageView4.setPreserveRatio(true);

        GridPane.setColumnIndex(imageView5, 2);
        GridPane.setRowIndex(imageView5, 2);
        imageView5.setFitHeight(94.0);
        imageView5.setFitWidth(118.0);
        imageView5.setId("img_8");
        imageView5.setPickOnBounds(true);
        imageView5.setPreserveRatio(true);

        GridPane.setColumnIndex(imageView6, 2);
        imageView6.setFitHeight(94.0);
        imageView6.setFitWidth(118.0);
        imageView6.setId("img_3");
        imageView6.setPickOnBounds(true);
        imageView6.setPreserveRatio(true);

        GridPane.setColumnIndex(imageView7, 1);
        imageView7.setFitHeight(94.0);
        imageView7.setFitWidth(118.0);
        imageView7.setId("img_1");
        imageView7.setPickOnBounds(true);
        imageView7.setPreserveRatio(true);
        borderPane.setCenter(gridPane);

        BorderPane.setAlignment(playButton, javafx.geometry.Pos.TOP_LEFT);
        playButton.setFitHeight(41.0);
        playButton.setFitWidth(35.0);
        playButton.setPickOnBounds(true);
        playButton.setPreserveRatio(true);
        BorderPane.setMargin(playButton, new Insets(5.0, 0.0, 2.0, 5.0));
        playButton.setImage(new Image(getClass().getResource("../images/backward.png").toExternalForm()));
        playButton.setOnMousePressed((e->{
            
        }));
        borderPane.setTop(playButton);

        BorderPane.setAlignment(imageView9, javafx.geometry.Pos.CENTER);
        imageView9.setFitHeight(33.0);
        imageView9.setFitWidth(38.0);
        imageView9.setPickOnBounds(true);
        imageView9.setPreserveRatio(true);
        imageView9.setImage(new Image(getClass().getResource("../images/play.png").toExternalForm()));
        BorderPane.setMargin(imageView9, new Insets(0.0, 0.0, 5.0, 0.0));
        borderPane.setBottom(imageView9);

        gridPane.getColumnConstraints().add(columnConstraints);
        gridPane.getColumnConstraints().add(columnConstraints0);
        gridPane.getColumnConstraints().add(columnConstraints1);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane.getRowConstraints().add(rowConstraints0);
        gridPane.getRowConstraints().add(rowConstraints1);
        gridPane.getChildren().add(imageView);
        gridPane.getChildren().add(imageView0);
        gridPane.getChildren().add(imageView1);
        gridPane.getChildren().add(imageView2);
        gridPane.getChildren().add(imageView3);
        gridPane.getChildren().add(imageView4);
        gridPane.getChildren().add(imageView5);
        gridPane.getChildren().add(imageView6);
        gridPane.getChildren().add(imageView7);
        getChildren().add(borderPane);
    }
    
}
