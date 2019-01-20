package view;

import controller.MyGui;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class gameRoomFXMLBase extends AnchorPane {

    public static String mode;
    public static int timerNumber = 0;
    public static Timer timer;
    protected final BorderPane borderPane;
    protected final AnchorPane anchorPane;
    protected final Label label;
    protected final ScrollPane scrollPane;
    protected final TextArea textArea;
    protected final TextField textField;
    protected final Button button;
    protected final AnchorPane anchorPane0;
    protected final SplitPane splitPane;
    protected final AnchorPane anchorPane1;
    protected final VBox vBox;
    protected final Label label0;
    protected final Label label1;
    protected final AnchorPane anchorPane2;
    protected final VBox vBox0;
    protected final Label label2;
    protected final Label label3;
    protected final AnchorPane anchorPane3;
    protected final Label label4;
    protected final Label label5;
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

    MyGui myGui;

    public gameRoomFXMLBase(MyGui g) {
        timerNumber++;
        if (timer != null) {
            timer.cancel();
        }
        myGui = g;
        borderPane = new BorderPane();
        anchorPane = new AnchorPane();
        label = new Label();
        scrollPane = new ScrollPane();
        textArea = new TextArea();
        textField = new TextField();
        button = new Button();
        anchorPane0 = new AnchorPane();
        splitPane = new SplitPane();
        anchorPane1 = new AnchorPane();
        vBox = new VBox();
        label0 = new Label();
        label1 = new Label();
        anchorPane2 = new AnchorPane();
        vBox0 = new VBox();
        label2 = new Label();
        label3 = new Label();
        anchorPane3 = new AnchorPane();
        label4 = new Label();
        label5 = new Label();
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
        setPrefHeight(500.0);
        setPrefWidth(1000.0);
        setStyle("-fx-background-color: linear-gradient( #247ba0 0%,#70c1b3 50% ,#247ba0 100%)"
                + ";");

        borderPane.setLayoutY(-2.0);
        borderPane.setPrefHeight(600.0);
        borderPane.setPrefWidth(1000.0);

        BorderPane.setAlignment(anchorPane, javafx.geometry.Pos.CENTER);
        anchorPane.setPrefHeight(502.0);
        anchorPane.setPrefWidth(350.0);
        anchorPane.setStyle("-fx-background-color: #fff; -fx-border-color: #000;");

        AnchorPane.setBottomAnchor(label, 568.0);
        AnchorPane.setRightAnchor(label, 16.0);
        AnchorPane.setTopAnchor(label, 14.0);
        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        label.setLayoutX(13.0);
        label.setLayoutY(14.0);
        label.setPrefHeight(34.0);
        label.setPrefWidth(321.0);
        label.setStyle("-fx-background-color: #173551; -fx-background-radius: 10;");
        label.setText("MY CHAT");
        label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        label.setTextFill(javafx.scene.paint.Color.valueOf("#d76767"));
        label.setFont(new Font("Arial", 28.0));

        AnchorPane.setBottomAnchor(scrollPane, 88.0);
        AnchorPane.setRightAnchor(scrollPane, 13.0);
        AnchorPane.setTopAnchor(scrollPane, 53.0);
        scrollPane.setLayoutX(23.0);
        scrollPane.setLayoutY(54.0);
        scrollPane.setPrefHeight(473.0);
        scrollPane.setPrefWidth(325.0);
        scrollPane.setStyle("-fx-background-radius: 10;");

        textArea.setPrefHeight(471.0);
        textArea.setPrefWidth(321.0);
        textArea.setStyle("-fx-background-radius: 20;");
        scrollPane.setContent(textArea);

        textField.setLayoutX(14.0);
        textField.setLayoutY(544.0);
        textField.setPrefHeight(35.0);
        textField.setPrefWidth(245.0);
        textField.setStyle("-fx-background-radius: 10;");
//        textField.setOnAction(e -> {
//            try {
//                myGui.sendMessage(myGui.myController.myModle.me.getPlayerUserName(), textField.getText());
//                textField.setText("");
//            } catch (RemoteException ex) {
//                Logger.getLogger(gameRoomFXMLBase.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });

        button.setLayoutX(275.0);
        button.setLayoutY(544.0);
        button.setMnemonicParsing(false);
        button.setPrefHeight(35.0);
        button.setPrefWidth(63.0);
        button.setStyle("-fx-background-color: #d76767; -fx-background-radius: 10;");
        button.setText("Send");
        button.setTextFill(javafx.scene.paint.Color.valueOf("#f5f5f5"));
        button.setFont(new Font(15.0));
        BorderPane.setMargin(anchorPane, new Insets(0.0));
        borderPane.setRight(anchorPane);

//        button.setOnAction(e -> {
//            try {
//                myGui.sendMessage(myGui.myController.myModle.me.getPlayerUserName(), textField.getText());
//                textField.setText("");
//            } catch (RemoteException ex) {
//                Logger.getLogger(gameRoomFXMLBase.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });
        BorderPane.setAlignment(anchorPane0, javafx.geometry.Pos.CENTER);
        anchorPane0.setPrefHeight(546.0);
        anchorPane0.setPrefWidth(203.0);
        anchorPane0.setStyle("-fx-background-color: #fff;");

        splitPane.setDividerPositions(0.5);
        splitPane.setLayoutY(-1.0);
        splitPane.setOrientation(javafx.geometry.Orientation.VERTICAL);
        splitPane.setPrefHeight(601.0);
        splitPane.setPrefWidth(203.0);
        splitPane.setStyle("-fx-background-color: #fff; -fx-border-color: #000;");

        anchorPane1.setMinHeight(0.0);
        anchorPane1.setMinWidth(0.0);
        anchorPane1.setPrefHeight(100.0);
        anchorPane1.setPrefWidth(160.0);

        AnchorPane.setBottomAnchor(vBox, 0.0);
        AnchorPane.setLeftAnchor(vBox, 0.0);
        AnchorPane.setRightAnchor(vBox, 0.0);
        AnchorPane.setTopAnchor(vBox, 0.0);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);
        vBox.setLayoutX(50.0);
        vBox.setLayoutY(132.0);
        vBox.setSpacing(10.0);

        label0.setText("userName1");
        label0.setTextFill(javafx.scene.paint.Color.valueOf("#026d94"));
        label0.setFont(new Font(20.0));

        label1.setAlignment(javafx.geometry.Pos.CENTER);
        label1.setText(MyGui.myController.names.get(0));
        label1.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        label1.setTextFill(javafx.scene.paint.Color.valueOf("#6681c5"));
        label1.setFont(new Font("System Italic", 64.0));

        anchorPane2.setMinHeight(0.0);
        anchorPane2.setMinWidth(0.0);
        anchorPane2.setPrefHeight(100.0);
        anchorPane2.setPrefWidth(160.0);

        AnchorPane.setBottomAnchor(vBox0, -28.0);
        AnchorPane.setLeftAnchor(vBox0, 0.0);
        AnchorPane.setRightAnchor(vBox0, 0.0);
        AnchorPane.setTopAnchor(vBox0, -14.0);
        vBox0.setAlignment(javafx.geometry.Pos.CENTER);
        vBox0.setLayoutY(-14.0);
        vBox0.setPrefHeight(324.0);
        vBox0.setPrefWidth(199.0);
        vBox0.setSpacing(10.0);

        label2.setText("UserName2");
        label2.setTextFill(javafx.scene.paint.Color.valueOf("#026d94"));
        label2.setFont(new Font(20.0));

        label3.setAlignment(javafx.geometry.Pos.CENTER);
        label3.setText(MyGui.myController.names.get(1));
        label3.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        label3.setTextFill(javafx.scene.paint.Color.valueOf("#6681c5"));
        label3.setFont(new Font("System Italic", 64.0));
        borderPane.setLeft(anchorPane0);

        BorderPane.setAlignment(anchorPane3, javafx.geometry.Pos.CENTER);
        anchorPane3.setPrefHeight(70.0);
        anchorPane3.setPrefWidth(950.0);

        label4.setLayoutX(295.0);
        label4.setLayoutY(25.0);
        label4.setPrefHeight(40.0);
        label4.setPrefWidth(109.0);
        label4.setText("TIMER :  ");
        label4.setTextFill(javafx.scene.paint.Color.valueOf("#d76767"));
        label4.setFont(new Font(28.0));

        label5.setLayoutX(405.0);
        label5.setLayoutY(26.0);
        label5.setText("00:00");
        label5.setTextFill(javafx.scene.paint.Color.valueOf("#fffefe"));
        label5.setFont(new Font(25.0));
        BorderPane.setMargin(anchorPane3, new Insets(0.0, 50.0, 0.0, 0.0));
        borderPane.setTop(anchorPane3);

        BorderPane.setAlignment(gridPane, javafx.geometry.Pos.CENTER);
        gridPane.setGridLinesVisible(true);
        gridPane.setPrefHeight(587.0);
        gridPane.setPrefWidth(409.0);

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
        BorderPane.setMargin(gridPane, new Insets(0.0, 10.0, 10.0, 10.0));

        img_0.setFitHeight(202.0);
        img_0.setFitWidth(143.0);
        img_0.setId("img_0");
        img_0.setOnMousePressed(MyGui::onImgViewClicked);
        img_0.setPickOnBounds(true);
        img_0.setPreserveRatio(true);

        GridPane.setColumnIndex(img_1, 1);
        img_1.setFitHeight(202.0);
        img_1.setFitWidth(143.0);
        img_1.setId("img_1");
        img_1.setOnMousePressed(MyGui::onImgViewClicked);
        img_1.setPickOnBounds(true);
        img_1.setPreserveRatio(true);

        GridPane.setColumnIndex(img_2, 2);
        img_2.setFitHeight(202.0);
        img_2.setFitWidth(143.0);
        img_2.setId("img_2");
        img_2.setOnMousePressed(MyGui::onImgViewClicked);
        img_2.setPickOnBounds(true);
        img_2.setPreserveRatio(true);

        GridPane.setRowIndex(img_3, 1);
        img_3.setFitHeight(202.0);
        img_3.setFitWidth(142.0);
        img_3.setId("img_3");
        img_3.setOnMousePressed(MyGui::onImgViewClicked);
        img_3.setPickOnBounds(true);
        img_3.setPreserveRatio(true);

        GridPane.setColumnIndex(img_4, 1);
        GridPane.setRowIndex(img_4, 1);
        img_4.setFitHeight(202.0);
        img_4.setFitWidth(143.0);
        img_4.setId("img_4");
        img_4.setOnMousePressed(MyGui::onImgViewClicked);
        img_4.setPickOnBounds(true);
        img_4.setPreserveRatio(true);

        GridPane.setColumnIndex(img_5, 2);
        GridPane.setRowIndex(img_5, 1);
        img_5.setFitHeight(202.0);
        img_5.setFitWidth(143.0);
        img_5.setId("img_5");
        img_5.setOnMousePressed(MyGui::onImgViewClicked);
        img_5.setPickOnBounds(true);
        img_5.setPreserveRatio(true);

        GridPane.setRowIndex(img_6, 2);
        img_6.setFitHeight(202.0);
        img_6.setFitWidth(143.0);
        img_6.setId("img_6");
        img_6.setOnMousePressed(MyGui::onImgViewClicked);
        img_6.setPickOnBounds(true);
        img_6.setPreserveRatio(true);

        GridPane.setColumnIndex(img_7, 1);
        GridPane.setRowIndex(img_7, 2);
        img_7.setFitHeight(202.0);
        img_7.setFitWidth(143.0);
        img_7.setId("img_7");
        img_7.setOnMousePressed(MyGui::onImgViewClicked);
        img_7.setPickOnBounds(true);
        img_7.setPreserveRatio(true);

        GridPane.setColumnIndex(img_8, 2);
        GridPane.setRowIndex(img_8, 2);
        img_8.setFitHeight(202.0);
        img_8.setFitWidth(143.0);
        img_8.setId("img_8");
        img_8.setOnMousePressed(MyGui::onImgViewClicked);
        img_8.setPickOnBounds(true);
        img_8.setPreserveRatio(true);
        borderPane.setCenter(gridPane);

        anchorPane.getChildren().add(label);
        anchorPane.getChildren().add(scrollPane);
        anchorPane.getChildren().add(textField);
        anchorPane.getChildren().add(button);
        vBox.getChildren().add(label0);
        vBox.getChildren().add(label1);
        anchorPane1.getChildren().add(vBox);
        splitPane.getItems().add(anchorPane1);
        vBox0.getChildren().add(label2);
        vBox0.getChildren().add(label3);
        anchorPane2.getChildren().add(vBox0);
        splitPane.getItems().add(anchorPane2);
        anchorPane0.getChildren().add(splitPane);
        anchorPane3.getChildren().add(label4);
        anchorPane3.getChildren().add(label5);
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
        getChildren().add(borderPane);
        updateChat();

        button.setOnAction(e -> {

            myGui.sendMessage(textField.getText());
            textField.setText("");

        });
        textField.setOnAction(e -> {

            myGui.sendMessage(textField.getText());
            textField.setText("");
        });
        label1.setText(MyGui.myController.names.get(0));
        label3.setText(MyGui.myController.names.get(1));

        if (mode.equals("spectator")) {
            img_0.setDisable(true);
            img_1.setDisable(true);
            img_2.setDisable(true);
            img_3.setDisable(true);
            img_4.setDisable(true);
            img_5.setDisable(true);
            img_6.setDisable(true);
            img_7.setDisable(true);
            img_8.setDisable(true);
//            textField.setDisable(true);
            textArea.setVisible(false);
            textField.setVisible(false);
            anchorPane.setVisible(false);
        }
        timerWithdraw();
    }
//
//    public void displayMessage(String myMessage) {
//        textArea.setText(myMessage);
//    }

    public void displayMessage(String myMessage) {
        textArea.setText(myMessage);
    }

    private void updateChat() {
        if (MyGui.myController.myModle.chatRooms.size() > 0) {
            String oppesiteUserName = new ArrayList<>(MyGui.myController.myModle.chatRooms.keySet()).get(0);
            String text = MyGui.myController.myModle.chatRooms.get(oppesiteUserName).getChat();
            textArea.setText(text);
        }
    }

    private void timerWithdraw() {
        this.timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int secondsLeft = 90;

            @Override
            public void run() {
                Platform.runLater(() -> {
                    secondsLeft--;
                    System.out.println("Timer:" + timerNumber + ", now:" + secondsLeft + ".");
                    label5.setText((secondsLeft/60 < 10 ? "0" + secondsLeft/60 : secondsLeft/60 + "")
                            + ":"
                            + (secondsLeft%60 < 10 ? "0" + secondsLeft%60 : secondsLeft%60 + ""));
                    if (secondsLeft == -1) {

                        timer.cancel();
                        try {
                            MyGui.myController.withdraw();
                        } catch (RemoteException ex) {
                            Logger.getLogger(gameRoomFXMLBase.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        }, 1000, 1000);

    }
}
