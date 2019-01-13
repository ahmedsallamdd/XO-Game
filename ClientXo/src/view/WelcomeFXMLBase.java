package view;

import clientxo.GameController;
import clientxo.MyGui;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WelcomeFXMLBase extends AnchorPane {

    protected final BorderPane borderPane;
    protected final AnchorPane anchorPane;
    protected final Label Title;
    protected final Label Description;
    protected final Button singleBtn;
    protected final Button onlineBtn;
    protected final ImageView gameImage;
    private GameController controller;
    public MyGui myGui;

    public WelcomeFXMLBase(MyGui myGUI) {

        borderPane = new BorderPane();
        anchorPane = new AnchorPane();
        Title = new Label();
        Description = new Label();
        singleBtn = new Button();
        onlineBtn = new Button();
        gameImage = new ImageView();
        
        //controller = new GameController();
        this.myGui = myGUI;
        
        setId("AnchorPane");
        setPrefHeight(650.0);
        setPrefWidth(600.0);

        borderPane.setPrefHeight(650.0);
        borderPane.setPrefWidth(600.0);
        borderPane.getStyleClass().add("border");
        borderPane.getStylesheets().add("myCss.css");

        BorderPane.setAlignment(anchorPane, javafx.geometry.Pos.CENTER);
        anchorPane.setPrefHeight(200.0);
        anchorPane.setPrefWidth(200.0);

        AnchorPane.setLeftAnchor(Title, 190.0);
        AnchorPane.setRightAnchor(Title, 100.0);
        AnchorPane.setTopAnchor(Title, 70.0);
        Title.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        Title.setMaxWidth(400.0);
        Title.setPrefHeight(90.0);
        Title.setPrefWidth(213.0);
        Title.setText("Tic Tac Toe ");
        Title.setTextFill(javafx.scene.paint.Color.WHITE);
        Title.setFont(new Font("Arial Bold Italic", 36.0));

        AnchorPane.setLeftAnchor(Description, 170.0);
        AnchorPane.setTopAnchor(Description, 150.0);
        Description.setLayoutX(162.0);
        Description.setLayoutY(112.0);
        Description.setPrefHeight(88.0);
        Description.setPrefWidth(278.0);
        Description.setText("A simple game To play");
        Description.setTextFill(javafx.scene.paint.Color.valueOf("#dadada"));
        Description.setFont(new Font(23.0));
        Description.setPadding(new Insets(0.0, 20.0, 0.0, 20.0));
        borderPane.setTop(anchorPane);

        BorderPane.setAlignment(singleBtn, javafx.geometry.Pos.CENTER);
        singleBtn.setDefaultButton(true);
        singleBtn.setMnemonicParsing(false);
        singleBtn.setPrefHeight(80.0);
        singleBtn.setPrefWidth(150.0);
        singleBtn.setStyle("-fx-background-radius: 10; -fx-background-color: #ff7764;");
        singleBtn.setText("Single Player");
        singleBtn.setTextFill(javafx.scene.paint.Color.valueOf("#fcfafa"));
        singleBtn.setFont(new Font(17.0));
        BorderPane.setMargin(singleBtn, new Insets(0.0, 0.0, 0.0, 30.0));
        borderPane.setLeft(singleBtn);
        singleBtn.setOnAction((e->{
//            primaryStage.setScene(new Scene(new SinglePlayerGui()));
//            controller.playWithComputer();
        }));

        BorderPane.setAlignment(onlineBtn, javafx.geometry.Pos.CENTER);
        onlineBtn.setMnemonicParsing(false);
        onlineBtn.setPrefHeight(77.0);
        onlineBtn.setPrefWidth(150.0);
        onlineBtn.setStyle("-fx-background-radius: 10; -fx-background-color: #ff7764;");
        onlineBtn.setText("Multi Players");
        onlineBtn.setTextFill(javafx.scene.paint.Color.valueOf("#fffbfb"));
        onlineBtn.setFont(new Font(17.0));
        BorderPane.setMargin(onlineBtn, new Insets(0.0, 30.0, 0.0, 0.0));
        borderPane.setRight(onlineBtn);

        BorderPane.setAlignment(gameImage, javafx.geometry.Pos.CENTER);
        gameImage.setFitHeight(202.0);
        gameImage.setFitWidth(185.0);
        gameImage.setPickOnBounds(true);
        gameImage.setPreserveRatio(true);
        gameImage.setImage(new Image(getClass().getResource("/images/welcome.jpg").toExternalForm()));
        borderPane.setCenter(gameImage);

        anchorPane.getChildren().add(Title);
        anchorPane.getChildren().add(Description);
        getChildren().add(borderPane);

        onlineBtn.setOnAction((ActionEvent event) -> {
            myGUI.createLoginScreen();
            
//            Scene newScene = new Scene(new LoginFXBase(primaryStage));
//            primaryStage.setScene(newScene);
        });

    }

}
