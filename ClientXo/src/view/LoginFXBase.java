package view;

import controller.MyGui;
import javafx.event.ActionEvent;
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

public class LoginFXBase extends AnchorPane {

    protected final VBox containerOfFields;
    protected final ImageView xoGameImg;
    protected final ImageView back;
    protected final Lighting lighting;
    protected final TextField userNameField;
    protected final PasswordField passwordField;
    protected final Button signInBtn;
    protected final Button signUpBtn;
    MyGui myGui;

    public LoginFXBase(MyGui g) {

        containerOfFields = new VBox();
        xoGameImg = new ImageView();
        back = new ImageView();
        lighting = new Lighting();
        userNameField = new TextField();
        passwordField = new PasswordField();
        signInBtn = new Button();
        signUpBtn = new Button();

        myGui = g;

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setNodeOrientation(javafx.geometry.NodeOrientation.LEFT_TO_RIGHT);
        setPrefHeight(650.0);
        setPrefWidth(600.0);
        setStyle("-fx-background-color: linear-gradient( #247ba0 0%,#70c1b3 50% ,#247ba0 100%);");
        getStyleClass().add(".border");
        getStylesheets().add("/trydesign/../myCss.css");

        AnchorPane.setBottomAnchor(containerOfFields, 120.0);
        AnchorPane.setLeftAnchor(containerOfFields, 120.0);
        AnchorPane.setRightAnchor(containerOfFields, 120.0);
        AnchorPane.setTopAnchor(containerOfFields, 120.0);
        containerOfFields.setAlignment(javafx.geometry.Pos.BASELINE_CENTER);
        containerOfFields.setFillWidth(false);
        containerOfFields.setLayoutX(122.0);
        containerOfFields.setLayoutY(108.0);
        containerOfFields.setPrefHeight(492.0);
        containerOfFields.setPrefWidth(369.0);
        containerOfFields.setStyle("-fx-background-color: #fff; -fx-background-radius: 10;");

        xoGameImg.setFitHeight(147.0);
        xoGameImg.setFitWidth(136.0);
        xoGameImg.setPickOnBounds(true);
        xoGameImg.setPreserveRatio(true);
        xoGameImg.setFocusTraversable(true);
        VBox.setMargin(xoGameImg, new Insets(20.0, 0.0, 0.0, 0.0));
        xoGameImg.setImage(new Image(getClass().getResource("/images/gameimg.png").toExternalForm()));

        xoGameImg.setEffect(lighting);

        userNameField.setMaxWidth(Double.MAX_VALUE);
        userNameField.setPrefHeight(38.0);
        userNameField.setPrefWidth(279.0);
        userNameField.setPromptText("User Name");
        VBox.setMargin(userNameField, new Insets(0.0, 15.0, 0.0, 15.0));
        userNameField.setFont(new Font(17.0));

        passwordField.setPrefHeight(54.0);
        passwordField.setPrefWidth(280.0);
        passwordField.setPromptText("Password");
        VBox.setMargin(passwordField, new Insets(0.0, 15.0, 0.0, 15.0));
        passwordField.setFont(new Font(16.0));

        signInBtn.setMnemonicParsing(false);
        signInBtn.setStyle("-fx-background-radius: 10; -fx-background-color: #ff7764;");
        signInBtn.setText("SignIn");
        signInBtn.setTextFill(javafx.scene.paint.Color.valueOf("#fffbfb"));
        signInBtn.setFont(new Font("Arial", 24.0));
        VBox.setMargin(signInBtn, new Insets(0.0, 100.0, 20.0, 100.0));

        signInBtn.setOnAction((ActionEvent event) -> {
            String isFound;
            if (!(userNameField.getText().equals("") || passwordField.getText().equals(""))) {
                isFound = myGui.checkIfUserExists(userNameField.getText(), passwordField.getText());

                if (isFound.equals("Wrong username or password!")) {
                    Alert alerForSignIn = new Alert(Alert.AlertType.ERROR);
                    alerForSignIn.setHeight(10);
                    alerForSignIn.setHeaderText(null);
                    alerForSignIn.setContentText(isFound);
                    alerForSignIn.show();

                } else if(isFound.equals("hello")){
                    myGui.createMainScreen();
                }
                else if(isFound.equals("Already logged in")){
                    Alert alerForSignIn = new Alert(Alert.AlertType.ERROR);
                    alerForSignIn.setHeight(10);
                    alerForSignIn.setHeaderText(null);
                    alerForSignIn.setContentText(isFound);
                    alerForSignIn.show();
                }else{
                    myGui.myController.showAlert("Login error", "", isFound);
                }
            } else {
                Alert alerForSignIn = new Alert(Alert.AlertType.WARNING);
                // alerForSignIn.setTitle("Error");
                alerForSignIn.setHeaderText(null);

                alerForSignIn.setContentText("Fill all fileds!");
                alerForSignIn.show();

            }

        });
        signUpBtn.setMnemonicParsing(false);
        signUpBtn.setStyle("-fx-background-color: #ff7764; -fx-background-radius: 10;");
        signUpBtn.setText("SignUp");
        signUpBtn.setTextFill(javafx.scene.paint.Color.valueOf("#fffdfd"));
        VBox.setMargin(signUpBtn, new Insets(20.0, 0.0, 0.0, 0.0));
        signUpBtn.setFont(new Font("Arial", 23.0));
        setOpaqueInsets(new Insets(0.0));
        signUpBtn.setOnAction((e) -> {
            myGui.createSignUpScreen();
        });
        back.setFitHeight(41.0);
        back.setFitWidth(37.0);
        back.setLayoutX(10.0);
        back.setLayoutY(10.0);
        back.setPickOnBounds(true);
        back.setPreserveRatio(true);
        back.setImage(new Image(getClass().getResource("../images/backward.png").toExternalForm()));
        back.setOnMousePressed(e -> {
            myGui.createWelcomeScreen();
        });
        containerOfFields.getChildren().add(xoGameImg);
        containerOfFields.getChildren().add(userNameField);
        containerOfFields.getChildren().add(passwordField);
        containerOfFields.getChildren().add(signInBtn);
        containerOfFields.getChildren().add(signUpBtn);
        getChildren().add(containerOfFields);
        getChildren().add(back);

        passwordField.setOnAction(e -> {
            signInBtn.fire();
        });

    }
}
