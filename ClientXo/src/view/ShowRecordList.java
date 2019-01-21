package view;

import controller.MyGui;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import xml.GameComplexType;
import xml.StepComplexType;

public class ShowRecordList extends AnchorPane {

    protected final ImageView play;
    protected final GridPane gridPane;
    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final ColumnConstraints columnConstraints1;
    protected final RowConstraints rowConstraints;
    protected final RowConstraints rowConstraints0;
    protected final RowConstraints rowConstraints1;
    protected final ImageView img_0;
    protected final ImageView img_3;
    protected final ImageView img_4;
    protected final ImageView img_5;
    protected final ImageView img_6;
    protected final ImageView img_7;
    protected final ImageView img_8;
    protected final ImageView img_2;
    protected final ImageView img_1;
    protected final ListView listView;
    protected final ImageView exit;

    MyGui myGui;
    int j = 0;
    Timer timer;
    private boolean isTimerRunning = false;

    public ShowRecordList(MyGui g) {
        myGui = g;
        play = new ImageView();
        gridPane = new GridPane();
        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        columnConstraints1 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        rowConstraints0 = new RowConstraints();
        rowConstraints1 = new RowConstraints();
        img_0 = new ImageView();
        img_3 = new ImageView();
        img_4 = new ImageView();
        img_5 = new ImageView();
        img_6 = new ImageView();
        img_7 = new ImageView();
        img_8 = new ImageView();
        img_2 = new ImageView();
        img_1 = new ImageView();
        listView = new ListView();
        exit = new ImageView();

        setId("AnchorPane");
        setPrefHeight(600.0);
        setPrefWidth(650.0);
        setStyle("-fx-background-color: linear-gradient( #247ba0 0%,#70c1b3 50% ,#247ba0 100%);");

        AnchorPane.setTopAnchor(play, 548.0);
        play.setFitHeight(50.0);
        play.setFitWidth(101.0);
        play.setLayoutX(386.0);
        play.setLayoutY(545.0);
        play.setPickOnBounds(true);
        play.setPreserveRatio(true);
        play.setImage(new Image(getClass().getResource("../images/play.png").toExternalForm()));
        play.setOnMouseClicked((e -> {
            if (isTimerRunning == true && timer != null) {
                timer.cancel();
                timer.purge();
                timer = null;
            }

            clearAllImages();
            imagesOptacity(0);

            if (listView.getSelectionModel().getSelectedItem() != null) {
                String selectedImg = listView.getSelectionModel().getSelectedItem().toString();
                File file = new File(".\\records\\" + selectedImg);
                GameComplexType game = myGui.myController.readFromXML(file);
                ArrayList<StepComplexType> stepList = game.getStep();

                int pos;
                String imgId;
                ImageView imgView;
                imagesOptacity(0);
                ArrayList<ImageView> orderedImgViewList = new ArrayList<>();

                for (int i = 0; i < stepList.size(); i++) {
                    pos = stepList.get(i).getPostion();
                    imgId = myGui.myController.constructImgeViewId(pos);
                    imgView = (ImageView) gridPane.lookup("#" + imgId);
                    orderedImgViewList.add(imgView);
                    if (stepList.get(i).getPlayer() == 0) {
                        imgView.setImage(new Image("/images/X_image.png"));
                    } else {
                        imgView.setImage(new Image("/images/O_image.png"));
                    }
                }
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        isTimerRunning = true;
                        listView.setDisable(true);
                        play.setDisable(true);
                        play.setOpacity(50.0);
                        if (j != orderedImgViewList.size()) {
                            ImageView newImgView = orderedImgViewList.get(j);
                            newImgView.setOpacity(100.0);
                            j++;
                        } else {
                            myGui.myController.showAlert("Game Result", "Result", game.getResult());
                            j = 0;
                            timer.cancel();
                            timer.purge();
                            timer = null;
                            isTimerRunning = false;

                            listView.setDisable(false);
                            play.setDisable(false);
                        }
                    }
                }, 1000, 1000);
                if (isTimerRunning && timer != null) {
                    timer.cancel();
                    timer.cancel();
                    timer.purge();
                    timer = null;
                    isTimerRunning = false;
                }

            } else {
                myGui.myController.showAlert("Hint", "Choose a record from the list first.", "");
            }
        }));

        AnchorPane.setBottomAnchor(gridPane, 55.0);
        AnchorPane.setLeftAnchor(gridPane, 180.0);
        AnchorPane.setRightAnchor(gridPane, 8.0);
        AnchorPane.setTopAnchor(gridPane, 44.0);
        gridPane.setGridLinesVisible(true);
        gridPane.setLayoutX(266.0);
        gridPane.setLayoutY(44.0);
        gridPane.setPrefHeight(489.0);
        gridPane.setPrefWidth(527.0);
        gridPane.setStyle("-fx-stroke: #fff;");

        columnConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints.setMinWidth(10.0);
        columnConstraints.setPrefWidth(100.0);

        columnConstraints0.setFillWidth(false);
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

        img_0.setFitHeight(168.0);
        img_0.setFitWidth(155.0);
        img_0.setId("img_0");
        img_0.setPickOnBounds(true);
        img_0.setPreserveRatio(true);
        img_0.setStyle("-fx-border-color: #fff;");

        GridPane.setRowIndex(img_3, 1);
        img_3.setFitHeight(166.0);
        img_3.setFitWidth(154.0);
        img_3.setId("img_3");
        img_3.setPickOnBounds(true);
        img_3.setPreserveRatio(true);

        GridPane.setColumnIndex(img_4, 1);
        GridPane.setRowIndex(img_4, 1);
        img_4.setFitHeight(167.0);
        img_4.setFitWidth(154.0);
        img_4.setId("img_4");
        img_4.setPickOnBounds(true);
        img_4.setPreserveRatio(true);

        GridPane.setColumnIndex(img_5, 2);
        GridPane.setRowIndex(img_5, 1);
        img_5.setFitHeight(166.0);
        img_5.setFitWidth(154.0);
        img_5.setId("img_5");
        img_5.setPickOnBounds(true);
        img_5.setPreserveRatio(true);

        GridPane.setRowIndex(img_6, 2);
        img_6.setFitHeight(169.0);
        img_6.setFitWidth(155.0);
        img_6.setId("img_6");
        img_6.setPickOnBounds(true);
        img_6.setPreserveRatio(true);

        GridPane.setColumnIndex(img_7, 1);
        GridPane.setRowIndex(img_7, 2);
        img_7.setFitHeight(167.0);
        img_7.setFitWidth(154.0);
        img_7.setId("img_7");
        img_7.setPickOnBounds(true);
        img_7.setPreserveRatio(true);

        GridPane.setColumnIndex(img_8, 2);
        GridPane.setRowIndex(img_8, 2);
        img_8.setFitHeight(167.0);
        img_8.setFitWidth(153.0);
        img_8.setId("img_8");
        img_8.setPickOnBounds(true);
        img_8.setPreserveRatio(true);

        GridPane.setColumnIndex(img_2, 2);
        img_2.setFitHeight(168.0);
        img_2.setFitWidth(154.0);
        img_2.setId("img_2");
        img_2.setPickOnBounds(true);
        img_2.setPreserveRatio(true);

        GridPane.setColumnIndex(img_1, 1);
        img_1.setFitHeight(169.0);
        img_1.setFitWidth(154.0);
        img_1.setId("img_1");
        img_1.setPickOnBounds(true);
        img_1.setPreserveRatio(true);

        AnchorPane.setBottomAnchor(listView, 55.0);
        AnchorPane.setLeftAnchor(listView, 8.0);
        listView.setLayoutX(14.0);
        listView.setLayoutY(43.0);
        listView.setPrefHeight(501.0);
        listView.setPrefWidth(163.0);
        listView.setStyle("-fx-background-color: #fff;");

        AnchorPane.setLeftAnchor(exit, 6.0);
        exit.setFitHeight(41.0);
        exit.setFitWidth(35.0);
        exit.setLayoutX(8.0);
        exit.setLayoutY(8.0);
        exit.setPickOnBounds(true);
        exit.setPreserveRatio(true);
        exit.setImage(new Image(getClass().getResource("../images/backward.png").toExternalForm()));
        exit.setOnMouseClicked((e -> {
            myGui.createMainScreen();
            if (isTimerRunning == true && timer != null) {
                timer.cancel();
                timer.purge();
                timer = null;
            }
        }));

        getChildren().add(play);
        gridPane.getColumnConstraints().add(columnConstraints);
        gridPane.getColumnConstraints().add(columnConstraints0);
        gridPane.getColumnConstraints().add(columnConstraints1);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane.getRowConstraints().add(rowConstraints0);
        gridPane.getRowConstraints().add(rowConstraints1);
        gridPane.getChildren().add(img_0);
        gridPane.getChildren().add(img_3);
        gridPane.getChildren().add(img_4);
        gridPane.getChildren().add(img_5);
        gridPane.getChildren().add(img_6);
        gridPane.getChildren().add(img_7);
        gridPane.getChildren().add(img_8);
        gridPane.getChildren().add(img_2);
        gridPane.getChildren().add(img_1);
        getChildren().add(gridPane);
        getChildren().add(listView);
        getChildren().add(exit);
        populateListView();
    }
    public void populateListView() {
//        ObservableList<GameComplexType> list = FXCollections.observableArrayList(playerList);
//        listView.setItems(list);
//        listView.setCellFactory((ListView<PlayerList> param) -> new ListItem(this));

        File folder = new File(".\\records");
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> list = new ArrayList<>();
//        list.add("Record");

        for (int i = 0; i < listOfFiles.length; i++) {
            list.add(listOfFiles[i].getName());
            System.out.println(list.get(i));
        }
        ListProperty<String> listProperty = new SimpleListProperty<>();
        listProperty.set(FXCollections.observableArrayList(list));
        listView.itemsProperty().bind(listProperty);

    }

    public void imagesOptacity(int value) {
        img_0.setOpacity(value);
        img_1.setOpacity(value);
        img_2.setOpacity(value);
        img_3.setOpacity(value);
        img_4.setOpacity(value);
        img_5.setOpacity(value);
        img_6.setOpacity(value);
        img_7.setOpacity(value);
        img_8.setOpacity(value);
    }

    private void clearAllImages() {
        img_0.setImage(null);
        img_1.setImage(null);
        img_2.setImage(null);
        img_3.setImage(null);
        img_4.setImage(null);
        img_5.setImage(null);
        img_6.setImage(null);
        img_7.setImage(null);
        img_8.setImage(null);
    }

}
