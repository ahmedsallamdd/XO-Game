package view;

import controller.MyGui;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SignUpFXBase extends AnchorPane {

    protected final VBox vBox;
    protected final ImageView xoGameImg;
    protected final ImageView back;
    protected final Lighting lighting;
    protected final TextField userNameField;
    protected final TextField fullNameField;
    protected final TextField emailField;
    protected final PasswordField  passwordField;
    protected final Button signUpBtn;
    

//    GameController controller = new GameController();
    MyGui myGui;

    public SignUpFXBase(MyGui g) {

        myGui = g;
        vBox = new VBox();
        xoGameImg = new ImageView();
        back = new ImageView();
        lighting = new Lighting();
        userNameField = new TextField();
        fullNameField = new TextField();
        emailField = new TextField();
        passwordField = new PasswordField();
        signUpBtn = new Button();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setNodeOrientation(javafx.geometry.NodeOrientation.LEFT_TO_RIGHT);
        setPrefHeight(700.0);
        setPrefWidth(600.0);
        setStyle("-fx-background-color: linear-gradient( #247ba0 0%,#70c1b3 50% ,#247ba0 100%);");

        AnchorPane.setBottomAnchor(vBox, 50.0);
        AnchorPane.setLeftAnchor(vBox, 130.0);
        AnchorPane.setRightAnchor(vBox, 130.0);
        AnchorPane.setTopAnchor(vBox, 50.0);
        vBox.setAlignment(javafx.geometry.Pos.BASELINE_CENTER);
        vBox.setFillWidth(false);
        vBox.setLayoutX(131.0);
        vBox.setLayoutY(33.0);
        vBox.setPrefHeight(594.0);
        vBox.setPrefWidth(339.0);
        vBox.setStyle("-fx-background-color: FFF; -fx-background-radius: 10;");

        xoGameImg.setFitHeight(147.0);
        xoGameImg.setFitWidth(136.0);
        xoGameImg.setPickOnBounds(true);
        xoGameImg.setPreserveRatio(true);
        VBox.setMargin(xoGameImg, new Insets(20.0, 0.0, 0.0, 0.0));
        xoGameImg.setImage(new Image(getClass().getResource("../images/gameimg.png").toExternalForm()));

        xoGameImg.setEffect(lighting);

        userNameField.setMaxWidth(Double.MAX_VALUE);
        userNameField.setPrefHeight(73.0);
        userNameField.setPrefWidth(280.0);
        userNameField.setPromptText("User Name");
        VBox.setMargin(userNameField, new Insets(0.0, 15.0, 0.0, 15.0));
        userNameField.setFont(new Font(17.0));

        fullNameField.setPrefHeight(54.0);
        fullNameField.setPrefWidth(279.0);
        fullNameField.setPromptText("Full Name");
        fullNameField.setFont(new Font(16.0));
        VBox.setMargin(fullNameField, new Insets(0.0, 15.0, 0.0, 15.0));

        emailField.setPrefHeight(54.0);
        emailField.setPrefWidth(279.0);
        emailField.setPromptText("Email");
        emailField.setFont(new Font(16.0));

        passwordField.setPrefHeight(54.0);
        passwordField.setPrefWidth(280.0);
        passwordField.setPromptText("Password");
        VBox.setMargin(passwordField, new Insets(0.0, 15.0, 0.0, 15.0));
        passwordField.setFont(new Font(16.0));

        signUpBtn.setMnemonicParsing(false);
        signUpBtn.setStyle("-fx-background-color: #ff7764; -fx-background-radius: 10;");
        signUpBtn.setText("Register");
        signUpBtn.setTextFill(javafx.scene.paint.Color.valueOf("#fff6f6"));
        signUpBtn.setFont(new Font("Arial", 24.0));
        VBox.setMargin(signUpBtn, new Insets(20.0, 0.0, 50.0, 0.0));
        setOpaqueInsets(new Insets(0.0));

        signUpBtn.setOnAction((e -> {
            if (userNameField.getText().equals("") || fullNameField.getText().equals("") || emailField.getText().equals("") || passwordField.getText().equals("")) {
                Alert alerForSignUp = new Alert(Alert.AlertType.WARNING);
                alerForSignUp.setTitle("Warning");
                alerForSignUp.setHeaderText(null);

                alerForSignUp.setContentText("Fill all fileds!");
                alerForSignUp.show();


            } else if (myGui.checkUserName(userNameField.getText())) {
                Alert alerForSignUp = new Alert(Alert.AlertType.WARNING);
                alerForSignUp.setTitle("Warning");
                alerForSignUp.setHeaderText(null);

                alerForSignUp.setContentText("Username already exist, try another username!");
                alerForSignUp.show();



            } else {
                try {
                    boolean isRegistered = myGui.signUp(userNameField.getText(), fullNameField.getText(), emailField.getText(), passwordField.getText());
                 
                    if(isRegistered)
                    {
                        myGui.createMainScreen();
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(SignUpFXBase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }));
        back.setFitHeight(41.0);
        back.setFitWidth(37.0);
        back.setLayoutX(10.0);
        back.setLayoutY(10.0);
        back.setPickOnBounds(true);
        back.setPreserveRatio(true);
        back.setImage(new Image(getClass().getResource("../images/backward.png").toExternalForm()));
        back.setOnMousePressed(e -> {
            myGui.createLoginScreen();
        });

        vBox.getChildren().add(xoGameImg);
        vBox.getChildren().add(userNameField);
        vBox.getChildren().add(fullNameField);
        vBox.getChildren().add(emailField);
        vBox.getChildren().add(passwordField);
        vBox.getChildren().add(signUpBtn);
        getChildren().add(back);
        getChildren().add(vBox);

    }

}
