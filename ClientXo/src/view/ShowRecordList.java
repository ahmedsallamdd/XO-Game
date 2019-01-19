package view;

import controller.MyGui;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import xml.GameComplexType;
import xml.StepComplexType;

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
    protected final ImageView img_0;
    protected final ImageView img_3;
    protected final ImageView img_4;
    protected final ImageView img_5;
    protected final ImageView img_6;
    protected final ImageView img_7;
    protected final ImageView img_8;
    protected final ImageView img_2;
    protected final ImageView img_1;
    protected final ImageView exit;
    protected final ImageView play;
//    String selectedImg;

    MyGui myGui;
    int j = 0;
    Timer timer;
    private boolean isTimerRunning = false;

    public ShowRecordList(MyGui g) {

        myGui = g;
//        timer = new Timer();
        borderPane = new BorderPane();
        listView = new ListView();
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
        exit = new ImageView();
        play = new ImageView();

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

        img_0.setFitHeight(94.0);
        img_0.setFitWidth(118.0);
        img_0.setId("img_0");
        img_0.setPickOnBounds(true);
        img_0.setPreserveRatio(true);

        GridPane.setRowIndex(img_3, 1);
        img_3.setFitHeight(94.0);
        img_3.setFitWidth(118.0);
        img_3.setId("img_3");
        img_3.setPickOnBounds(true);
        img_3.setPreserveRatio(true);

        GridPane.setColumnIndex(img_4, 1);
        GridPane.setRowIndex(img_4, 1);
        img_4.setFitHeight(94.0);
        img_4.setFitWidth(118.0);
        img_4.setId("img_4");
        img_4.setPickOnBounds(true);
        img_4.setPreserveRatio(true);

        GridPane.setColumnIndex(img_5, 2);
        GridPane.setRowIndex(img_5, 1);
        img_5.setFitHeight(94.0);
        img_5.setFitWidth(118.0);
        img_5.setId("img_5");
        img_5.setPickOnBounds(true);
        img_5.setPreserveRatio(true);

        GridPane.setRowIndex(img_6, 2);
        img_6.setFitHeight(94.0);
        img_6.setFitWidth(118.0);
        img_6.setId("img_6");
        img_6.setPickOnBounds(true);
        img_6.setPreserveRatio(true);

        GridPane.setColumnIndex(img_7, 1);
        GridPane.setRowIndex(img_7, 2);
        img_7.setFitHeight(94.0);
        img_7.setFitWidth(118.0);
        img_7.setId("img_7");
        img_7.setPickOnBounds(true);
        img_7.setPreserveRatio(true);

        GridPane.setColumnIndex(img_8, 2);
        GridPane.setRowIndex(img_8, 2);
        img_8.setFitHeight(94.0);
        img_8.setFitWidth(118.0);
        img_8.setId("img_8");
        img_8.setPickOnBounds(true);
        img_8.setPreserveRatio(true);

        GridPane.setColumnIndex(img_2, 2);
        img_2.setFitHeight(94.0);
        img_2.setFitWidth(118.0);
        img_2.setId("img_2");
        img_2.setPickOnBounds(true);
        img_2.setPreserveRatio(true);

        GridPane.setColumnIndex(img_1, 1);
        img_1.setFitHeight(94.0);
        img_1.setFitWidth(118.0);
        img_1.setId("img_1");
        img_1.setPickOnBounds(true);
        img_1.setPreserveRatio(true);
        borderPane.setCenter(gridPane);

        BorderPane.setAlignment(exit, javafx.geometry.Pos.TOP_LEFT);
        exit.setFitHeight(41.0);
        exit.setFitWidth(35.0);
        exit.setPickOnBounds(true);
        exit.setPreserveRatio(true);
        BorderPane.setMargin(exit, new Insets(5.0, 0.0, 2.0, 5.0));
        exit.setImage(new Image(getClass().getResource("../images/backward.png").toExternalForm()));
        exit.setOnMouseClicked((e -> {
            myGui.createMainScreen();
        }));
        borderPane.setTop(exit);

        BorderPane.setAlignment(play, javafx.geometry.Pos.CENTER);
        play.setFitHeight(33.0);
        play.setFitWidth(38.0);
        play.setPickOnBounds(true);
        play.setPreserveRatio(true);
        play.setImage(new Image(getClass().getResource("../images/play.png").toExternalForm()));
        BorderPane.setMargin(play, new Insets(0.0, 0.0, 5.0, 0.0));

        play.setOnMouseClicked((e -> {
            if (isTimerRunning == true && timer != null) {
                timer.cancel();
                timer.purge();
                timer = null;
            }

            clearAllImages();
            imagesOptacity(0);
            String selectedImg = listView.getSelectionModel().getSelectedItem().toString();

            if (selectedImg != null) {
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
                            ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

                            Platform.runLater(() -> {
                                Alert a = new Alert(Alert.AlertType.INFORMATION,
                                        ".",
                                        ok);
                                a.setTitle("Result");
                                a.setHeaderText(game.getResult());
                                myGui.myController.myModle.currentShowenAlerts.add(a);
                                if (a.showAndWait().get() == ok) {
                                    myGui.myController.myModle.currentShowenAlerts.remove(a);
                                    a.close();
//                                    myGui.createReplayScreen();
                                }
                                j = 0;
                            });
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

            }
        }));

        borderPane.setBottom(play);

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
        getChildren().add(borderPane);

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
