package view;

import commontxo.ServerNullExeption;
import controller.MyGui;
import java.util.Optional;
import java.util.Random;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import model.ToggleBtn;

public class SinglePlayerGui extends AnchorPane {

    protected final GridPane gridPane;
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
    protected final ImageView back;
    protected int activePlayer = 0;
    protected int movesCounter = 0;
    protected int[] positions;
    protected int[][] winningPositions;
    protected boolean isFinished = false;
    ImageView imgView;
    ToggleBtn toggleBtn;
    Button btnStartGame;
    public static String mode = "";

    MyGui myGui;
    String parentScreen;

    public SinglePlayerGui(MyGui g, String parentScreen) {

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
        back = new ImageView();

        toggleBtn = new ToggleBtn();
        btnStartGame = new Button();

        myGui = g;
        this.parentScreen = parentScreen;

        setId("AnchorPane");
        setPrefHeight(650.0);
        setPrefWidth(600.0);
//        gridPane.setAlignment(javafx.geometry.Pos.CENTER);
        setStyle("-fx-background-color: linear-gradient( #247ba0 0%,#70c1b3 50% ,#247ba0 100%);;");

        AnchorPane.setBottomAnchor(gridPane, 70.0);
        AnchorPane.setLeftAnchor(gridPane, 60.0);
        AnchorPane.setRightAnchor(gridPane, 60.0);
        AnchorPane.setTopAnchor(gridPane, 70.0);

        gridPane.setGridLinesVisible(true);
        gridPane.setLayoutX(60.0);
        gridPane.setLayoutY(58.0);
        gridPane.setPrefHeight(240.0);
        gridPane.setPrefWidth(481.0);

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

        img_0.setFitHeight(170.0);
        img_0.setFitWidth(160.0);
        img_0.setId("img_0");
        img_0.setOnMousePressed(this::changeImg);
        img_0.setPickOnBounds(true);
        img_0.setPreserveRatio(true);
        img_0.requestFocus();

        GridPane.setColumnIndex(img_1, 1);
        img_1.setFitHeight(170.0);
        img_1.setFitWidth(160.0);
        img_1.setId("img_1");
        img_1.setLayoutX(10.0);
        img_1.setLayoutY(12.0);
        img_1.setOnMousePressed(this::changeImg);
        img_1.setPickOnBounds(true);
        img_1.setPreserveRatio(true);

        GridPane.setColumnIndex(img_2, 2);
        img_2.setFitHeight(170.0);
        img_2.setFitWidth(160.0);
        img_2.setId("img_2");
        img_2.setOnMousePressed(this::changeImg);
        img_2.setPickOnBounds(true);
        img_2.setPreserveRatio(true);

        GridPane.setRowIndex(img_3, 1);
        img_3.setFitHeight(170.0);
        img_3.setFitWidth(160.0);
        img_3.setId("img_3");
        img_3.setOnMousePressed(this::changeImg);
        img_3.setPickOnBounds(true);
        img_3.setPreserveRatio(true);

        GridPane.setColumnIndex(img_4, 1);
        GridPane.setRowIndex(img_4, 1);
        img_4.setFitHeight(170.0);
        img_4.setFitWidth(160.0);
        img_4.setId("img_4");
        img_4.setOnMousePressed(this::changeImg);
        img_4.setPickOnBounds(true);
        img_4.setPreserveRatio(true);

        GridPane.setColumnIndex(img_5, 2);
        GridPane.setRowIndex(img_5, 1);
        img_5.setFitHeight(170.0);
        img_5.setFitWidth(160.0);
        img_5.setId("img_5");
        img_5.setOnMousePressed(this::changeImg);
        img_5.setPickOnBounds(true);
        img_5.setPreserveRatio(true);

        GridPane.setRowIndex(img_6, 2);
        img_6.setFitHeight(170.0);
        img_6.setFitWidth(160.0);
        img_6.setId("img_6");
        img_6.setOnMousePressed(this::changeImg);
        img_6.setPickOnBounds(true);
        img_6.setPreserveRatio(true);

        GridPane.setColumnIndex(img_7, 1);
        GridPane.setRowIndex(img_7, 2);
        img_7.setFitHeight(170.0);
        img_7.setFitWidth(160.0);
        img_7.setId("img_7");
        img_7.setOnMousePressed(this::changeImg);
        img_7.setPickOnBounds(true);
        img_7.setPreserveRatio(true);

        GridPane.setColumnIndex(img_8, 2);
        GridPane.setRowIndex(img_8, 2);
        img_8.setFitHeight(170.0);
        img_8.setFitWidth(160.0);
        img_8.setId("img_8");
        img_8.setOnMousePressed(this::changeImg);
        img_8.setPickOnBounds(true);
        img_8.setPreserveRatio(true);

        back.setFitHeight(41.0);
        back.setFitWidth(35.0);
        back.setLayoutX(14.0);
        back.setLayoutY(14.0);
        back.setPickOnBounds(true);
        back.setPreserveRatio(true);
        back.setImage(new Image(getClass().getResource("../images/backward.png").toExternalForm()));

        back.setOnMousePressed(e -> {
            if (this.parentScreen.equals("welcome")) {
                myGui.createWelcomeScreen();
            } else if (this.parentScreen.equals("main")) {
                try {
                    myGui.createMainScreen();
                } catch (ServerNullExeption ex) {
                    myGui.myController.serverUnavilable();
                }
            }
        });

        ToggleBtn toggleBtn = new ToggleBtn();
        toggleBtn.setLayoutX(this.getPrefWidth() / 2 - 40);
        toggleBtn.setLayoutY(35);

        btnStartGame.setStyle("-fx-background-color: #0b3c49; -fx-background-radius: 10;");
        btnStartGame.setText("Start Game");
        btnStartGame.setTextFill(javafx.scene.paint.Color.WHITE);
        btnStartGame.setFont(new Font("System Bold", 22.0));
        btnStartGame.setLayoutX(this.getPrefWidth() / 2 - 70);
        btnStartGame.setLayoutY(this.getPrefHeight() - 60);

        btnStartGame.setOnAction((e) -> {
            btnStartGame.setDisable(true);
            disableAllImages(false);
            toggleBtn.setDisable(true);
            mode = toggleBtn.getText();
            positions = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};
            movesCounter = 0;
            isFinished = false;
            activePlayer = 0;
            System.out.println(mode);
        });

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
        getChildren().add(back);
        getChildren().add(gridPane);
        getChildren().add(toggleBtn);
        getChildren().add(btnStartGame);

        positions = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};
        winningPositions = new int[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6},
        {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
        disableAllImages(true);
    }

    protected void changeImg(MouseEvent mouseEvent) {
        if (isFinished) {
            return;
        }
        imgView = (ImageView) mouseEvent.getSource();
        movesCounter++;
        if (imgView.getImage() == null) {
            if (activePlayer == 0) {
                imgView.setImage(new Image("/images/X_image.png"));
                modifyPositionsArray(mouseEvent.getPickResult().getIntersectedNode().getId(), activePlayer);
                activePlayer = 1;
                computerTurn();
            }
        }
    }

    protected void modifyPositionsArray(String id, int player) {
        String[] _id = id.split("_");
        int pos = Integer.valueOf(_id[1]);
        positions[pos] = player;
        checkGameResult(positions);
    }

    protected void checkGameResult(int[] gameState) {
        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] != 2
                    && gameState[winningPosition[0]] == gameState[winningPosition[1]]
                    && gameState[winningPosition[1]] == gameState[winningPosition[2]]) {

                if (gameState[winningPosition[0]] == 0) {
                    System.out.println("X has won!");
                    isFinished = true;
                    gameResultDialog("Congratulations,\nYou Won!");
                    return;
                } else {
                    System.out.println("O has won!");
                    isFinished = true;
                    gameResultDialog("Oops!\nYou Lost!");

                    return;
                }
            }
        }
        if (movesCounter == 9) {
            System.out.println("It's a draw!");
            isFinished = true;
            gameResultDialog("It's a draw!");
        }
    }

    public void gameResultDialog(String header) {
        Platform.runLater(() -> {
            ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            Alert a = new Alert(Alert.AlertType.INFORMATION, "", ok);
            a.setTitle("Result");
            a.setHeaderText(header);
            myGui.myController.myModle.currentShowenAlerts.add(a);
            a.showAndWait();
            clearAllImages();
            disableAllImages(true);
            btnStartGame.setDisable(false);
            toggleBtn.setDisable(false);
        });
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

    public void computerTurn() {
        if (!isFinished) {
            movesCounter++;
            //abdo code here
            int pos;
            if (mode.equals("Hard")) {
                int whichPosition = 0;
                int counter = 0;
                int max = -1;
                int defendCounter = 0;
                int defendTargetPosition = -1;
                int attackTargetPosition = -1;
                boolean freeSpace;
                if (movesCounter == 2) {
                    pos = pickRandomImageView();
                } else {
                    for (int[] winningPosition : winningPositions) {
                        counter = 0;
                        defendCounter = 0;
                        freeSpace = false;
                        for (int i = 0; i < winningPosition.length; i++) {
                            //attack
                            switch (positions[winningPosition[i]]) {
                                case 0:
                                    //defend
                                    defendCounter++;
                                    counter = 0;
                                    break;
                                case 1:
                                    counter++;
                                    break;
                                default://2
                                    freeSpace = true;
                                    whichPosition = winningPosition[i];
                                    break;
                            }
                            if (i == 2 && freeSpace) {
                                if (defendCounter == 2) {
                                    defendTargetPosition = whichPosition;
                                }
                                switch (counter) {
                                    case 2:
                                        attackTargetPosition = whichPosition;
                                        break;
                                    case 1:
                                        max = whichPosition;
                                        break;
                                    default:
                                }
                            }
                        }
                    }
                    pos = whichPosition;//play random if no O for him
                    if (max > -1) {//have already one O 
                        pos = max;
                    }
                    if (defendTargetPosition > -1) {
                        pos = defendTargetPosition;
                    }
                    if (attackTargetPosition > -1) {//piriorty to attack, if need defence swap this with defend
                        pos = attackTargetPosition;
                    }
                }
            } else {
                pos = pickRandomImageView();
            }
            String imgViewId = constructImgeViewId(pos);
            imgView = (ImageView) gridPane.lookup("#" + imgViewId);
            imgView.setImage(new Image("/images/O_image.png"));
            modifyPositionsArray(imgViewId, 1);
            activePlayer = 0;
        }
    }

    private int pickRandomImageView() {
        Random rand = new Random();
        int pos;
        do {
            pos = rand.nextInt(9);
        } while (!isFinished && positions[pos] != 2);
        return pos;
    }

    private String constructImgeViewId(int pos) {
        return "img_" + String.valueOf(pos);
    }

    private void disableAllImages(boolean state) {
        img_0.setDisable(state);
        img_1.setDisable(state);
        img_2.setDisable(state);
        img_3.setDisable(state);
        img_4.setDisable(state);
        img_5.setDisable(state);
        img_6.setDisable(state);
        img_7.setDisable(state);
        img_8.setDisable(state);
    }
}
