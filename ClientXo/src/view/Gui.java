package view;

import clientxo.GameController;
import clientxo.MyGui;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class Gui extends AnchorPane {

    public final GridPane gridPane;
    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final ColumnConstraints columnConstraints1;
    protected final RowConstraints rowConstraints;
    protected final RowConstraints rowConstraints0;
    protected final RowConstraints rowConstraints1;
    protected final ImageView img_0;
    protected final ImageView img_1;
    protected final ImageView img_2;
    protected final ImageView img_3;
    protected final ImageView img_4;
    protected final ImageView img_5;
    protected final ImageView img_6;
    protected final ImageView img_7;
    protected final ImageView img_8;

    String SelectedImgId = "";
    GameController controller;

    public Gui() {
//        controller = new GameController();
        controller.reDrawGameBoard();

        gridPane = new GridPane();
        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        columnConstraints1 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        rowConstraints0 = new RowConstraints();
        rowConstraints1 = new RowConstraints();
        img_0 = new ImageView();
        img_1 = new ImageView();
        img_2 = new ImageView();
        img_3 = new ImageView();
        img_4 = new ImageView();
        img_5 = new ImageView();
        img_6 = new ImageView();
        img_7 = new ImageView();
        img_8 = new ImageView();

        setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        gridPane.setAlignment(javafx.geometry.Pos.CENTER);
        gridPane.setGridLinesVisible(true);
        gridPane.setLayoutX(28.0);
        gridPane.setLayoutY(16.0);
        gridPane.setPrefHeight(369.0);
        gridPane.setPrefWidth(544.0);

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

        img_0.setFitHeight(120.0);
        img_0.setFitWidth(180.0);
        img_0.setId("img_0");
        img_0.setOnMousePressed(MyGui::onImgViewClicked);
        img_0.setPickOnBounds(true);
        img_0.setPreserveRatio(true);
        img_0.requestFocus();

        GridPane.setColumnIndex(img_1, 1);
        img_1.setFitHeight(120.0);
        img_1.setFitWidth(180.0);
        img_1.setId("img_1");
        img_1.setLayoutX(10.0);
        img_1.setLayoutY(12.0);
        img_1.setOnMousePressed(MyGui::onImgViewClicked);
        img_1.setPickOnBounds(true);
        img_1.setPreserveRatio(true);

        GridPane.setColumnIndex(img_2, 2);
        img_2.setFitHeight(120.0);
        img_2.setFitWidth(180.0);
        img_2.setId("img_2");
        img_2.setLayoutX(10.0);
        img_2.setLayoutY(12.0);
        img_2.setOnMousePressed(MyGui::onImgViewClicked);
        img_2.setPickOnBounds(true);
        img_2.setPreserveRatio(true);

        GridPane.setRowIndex(img_3, 1);
        img_3.setFitHeight(120.0);
        img_3.setFitWidth(180.0);
        img_3.setId("img_3");
        img_3.setLayoutX(10.0);
        img_3.setLayoutY(12.0);
        img_3.setOnMousePressed(MyGui::onImgViewClicked);
        img_3.setPickOnBounds(true);
        img_3.setPreserveRatio(true);

        GridPane.setColumnIndex(img_4, 1);
        GridPane.setRowIndex(img_4, 1);
        img_4.setFitHeight(120.0);
        img_4.setFitWidth(180.0);
        img_4.setId("img_4");
        img_4.setLayoutX(10.0);
        img_4.setLayoutY(12.0);
        img_4.setOnMousePressed(MyGui::onImgViewClicked);
        img_4.setPickOnBounds(true);
        img_4.setPreserveRatio(true);

        GridPane.setColumnIndex(img_5, 2);
        GridPane.setRowIndex(img_5, 1);
        img_5.setFitHeight(120.0);
        img_5.setFitWidth(180.0);
        img_5.setId("img_5");
        img_5.setLayoutX(10.0);
        img_5.setLayoutY(12.0);
        img_5.setOnMousePressed(MyGui::onImgViewClicked);
        img_5.setPickOnBounds(true);
        img_5.setPreserveRatio(true);

        GridPane.setRowIndex(img_6, 2);
        img_6.setFitHeight(120.0);
        img_6.setFitWidth(180.0);
        img_6.setId("img_6");
        img_6.setLayoutX(10.0);
        img_6.setLayoutY(12.0);
        img_6.setOnMousePressed(MyGui::onImgViewClicked);
        img_6.setPickOnBounds(true);
        img_6.setPreserveRatio(true);

        GridPane.setColumnIndex(img_7, 1);
        GridPane.setRowIndex(img_7, 2);
        img_7.setFitHeight(120.0);
        img_7.setFitWidth(180.0);
        img_7.setId("img_7");
        img_7.setLayoutX(10.0);
        img_7.setLayoutY(12.0);
        img_7.setOnMousePressed(MyGui::onImgViewClicked);
        img_7.setPickOnBounds(true);
        img_7.setPreserveRatio(true);

        GridPane.setColumnIndex(img_8, 2);
        GridPane.setRowIndex(img_8, 2);
        img_8.setFitHeight(120.0);
        img_8.setFitWidth(180.0);
        img_8.setId("img_8");
        img_8.setLayoutX(10.0);
        img_8.setLayoutY(12.0);
        img_8.setOnMousePressed(MyGui::onImgViewClicked);
        img_8.setPickOnBounds(true);
        img_8.setPreserveRatio(true);

        gridPane.getColumnConstraints().add(columnConstraints);
        gridPane.getColumnConstraints().add(columnConstraints0);
        gridPane.getColumnConstraints().add(columnConstraints1);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane.getRowConstraints().add(rowConstraints0);
        gridPane.getRowConstraints().add(rowConstraints1);
        gridPane.getChildren().add(img_0);
        gridPane.getChildren().add(img_1);
        gridPane.getChildren().add(img_2);
        gridPane.getChildren().add(img_3);
        gridPane.getChildren().add(img_4);
        gridPane.getChildren().add(img_5);
        gridPane.getChildren().add(img_6);
        gridPane.getChildren().add(img_7);
        gridPane.getChildren().add(img_8);
        getChildren().add(gridPane);
    }
//    void changeImg(MouseEvent e){
//        SelectedImgId = e.getPickResult().getIntersectedNode().getId();
//    }
}
