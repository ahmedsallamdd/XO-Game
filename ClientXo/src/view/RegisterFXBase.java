package view;

import commontxo.ServerNullExeption;
import controller.MyGui;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light.Distant;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class RegisterFXBase extends AnchorPane {

    protected final VBox container;
    protected final ImageView XOimageView;
    protected final ImageView back;
    protected final Lighting lighting;
    protected final TextField userNameTextField;
    protected final Label labelForUserName;
    protected final TextField fullNameTextField;
    protected final Label labelForFullName;
    protected final TextField emailTextField;
    protected final Label labelForEmail;
    protected final TextField passwordTextField;
    protected final Label label1ForPassword;
    protected final Label label2ForPassword;
    protected final Button signUpBtn;
    MyGui myGui;

    public RegisterFXBase(MyGui g) {
        myGui = g;
        container = new VBox();
        XOimageView = new ImageView();
        back = new ImageView();
        lighting = new Lighting();
        userNameTextField = new TextField();
        labelForUserName = new Label();
        fullNameTextField = new TextField();
        labelForFullName = new Label();
        emailTextField = new TextField();
        labelForEmail = new Label();
        passwordTextField = new TextField();
        label1ForPassword = new Label();
        label2ForPassword = new Label();
        signUpBtn = new Button();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setNodeOrientation(javafx.geometry.NodeOrientation.LEFT_TO_RIGHT);
        setPrefHeight(650.0);
        setPrefWidth(600.0);
        setStyle("-fx-background-color: linear-gradient( #247ba0 0%,#70c1b3 50% ,#247ba0 100%);");

        AnchorPane.setBottomAnchor(container, 12.0);
        AnchorPane.setLeftAnchor(container, 92.0);
        AnchorPane.setRightAnchor(container, 102.0);
        AnchorPane.setTopAnchor(container, 16.0);
        container.setAlignment(javafx.geometry.Pos.BASELINE_CENTER);
        container.setFillWidth(false);
        container.setLayoutX(92.0);
        container.setLayoutY(16.0);
        container.setPrefHeight(572.0);
        container.setPrefWidth(456.0);
        container.setStyle("-fx-background-color: FFF; -fx-background-radius: 10;");

        XOimageView.setFitHeight(139.0);
        XOimageView.setFitWidth(142.0);
        XOimageView.setPickOnBounds(true);
        XOimageView.setPreserveRatio(true);
        VBox.setMargin(XOimageView, new Insets(5.0, 0.0, 0.0, 0.0));
        XOimageView.setImage(new Image(getClass().getResource("../images/gameimg.png").toExternalForm()));

        XOimageView.setEffect(lighting);

        userNameTextField.setFocusTraversable(false);
        userNameTextField.setMaxWidth(Double.MAX_VALUE);
        userNameTextField.setPrefHeight(38.0);
        userNameTextField.setPrefWidth(279.0);
        userNameTextField.setPromptText("Enetr Your User Name");
        VBox.setMargin(userNameTextField, new Insets(0.0, 15.0, 0.0, 15.0));
        userNameTextField.setFont(new Font(17.0));

        labelForUserName.setAlignment(javafx.geometry.Pos.CENTER);
        labelForUserName.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        labelForUserName.setMinHeight(USE_PREF_SIZE);
        labelForUserName.setMinWidth(USE_PREF_SIZE);
        labelForUserName.setPrefHeight(25.0);
        labelForUserName.setPrefWidth(340.0);
        labelForUserName.setText("Username must contain only characters and numbers");
        VBox.setMargin(labelForUserName, new Insets(5.0, 0.0, 2.0, 0.0));

        fullNameTextField.setFocusTraversable(false);
        fullNameTextField.setPrefHeight(54.0);
        fullNameTextField.setPrefWidth(279.0);
        fullNameTextField.setPromptText("Enter Your Full Name");
        fullNameTextField.setFont(new Font(16.0));
        VBox.setMargin(fullNameTextField, new Insets(0.0, 15.0, 0.0, 15.0));

        labelForFullName.setAlignment(javafx.geometry.Pos.CENTER);
        labelForFullName.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        labelForFullName.setMinHeight(USE_PREF_SIZE);
        labelForFullName.setMinWidth(USE_PREF_SIZE);
        labelForFullName.setPrefHeight(25.0);
        labelForFullName.setPrefWidth(340.0);
        labelForFullName.setText("Your Fullname must begin with CAPITAl, contain only characters and one space");
        VBox.setMargin(labelForFullName, new Insets(5.0, 0.0, 0.0, 0.0));

        emailTextField.setFocusTraversable(false);
        emailTextField.setPrefHeight(54.0);
        emailTextField.setPrefWidth(279.0);
        emailTextField.setPromptText("Enetr Your Email");
        emailTextField.setFont(new Font(16.0));

        labelForEmail.setAlignment(javafx.geometry.Pos.CENTER);
        labelForEmail.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        labelForEmail.setMinHeight(USE_PREF_SIZE);
        labelForEmail.setMinWidth(USE_PREF_SIZE);
        labelForEmail.setPrefHeight(25.0);
        labelForEmail.setPrefWidth(340.0);
        labelForEmail.setText("Your email should like email@example.com");
        VBox.setMargin(labelForEmail, new Insets(5.0, 0.0, 0.0, 0.0));

        passwordTextField.setFocusTraversable(false);
        passwordTextField.setPrefHeight(54.0);
        passwordTextField.setPrefWidth(280.0);
        passwordTextField.setPromptText("Enter Your Password");
        VBox.setMargin(passwordTextField, new Insets(0.0, 15.0, 0.0, 15.0));
        passwordTextField.setFont(new Font(16.0));

        label1ForPassword.setAlignment(javafx.geometry.Pos.CENTER);
        label1ForPassword.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        label1ForPassword.setPrefHeight(20.0);
        label1ForPassword.setPrefWidth(340.0);
        label1ForPassword.setText("Your password should contain at least one CAPITAL,one small");
        VBox.setMargin(label1ForPassword, new Insets(10.0, 0.0, 0.0, 0.0));

        label2ForPassword.setAlignment(javafx.geometry.Pos.CENTER);
        label2ForPassword.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        label2ForPassword.setPrefHeight(20.0);
        label2ForPassword.setPrefWidth(340.0);
        label2ForPassword.setText("one spacial character and one number and at least 8 characters");

        signUpBtn.setAlignment(javafx.geometry.Pos.CENTER);
        signUpBtn.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        signUpBtn.setGraphicTextGap(3.0);
        signUpBtn.setMnemonicParsing(false);
        signUpBtn.setStyle("-fx-background-color: #0b3c49; -fx-background-radius: 10;");
        signUpBtn.setText("Register");
        signUpBtn.setTextFill(javafx.scene.paint.Color.valueOf("#fff6f6"));
        signUpBtn.setFont(new Font("Arial", 24.0));
        VBox.setMargin(signUpBtn, new Insets(5.0, 70.0, 5.0, 50.0));
        signUpBtn.setCursor(Cursor.HAND);
        setOpaqueInsets(new Insets(0.0));
        signUpBtn.setOnAction((event) -> {
            try {
                boolean isvalid = myGui.setValidationForRegister(userNameTextField.getText(), fullNameTextField.getText(), emailTextField.getText(), passwordTextField.getText());
                if (isvalid) {
                    boolean isRegistered = myGui.signUp(userNameTextField.getText(), fullNameTextField.getText(), emailTextField.getText(), passwordTextField.getText());
                    if (isRegistered) {
                        myGui.createMainScreen();
                    }
                }
            } catch (RemoteException | ServerNullExeption ex) {
                myGui.serverUnavilable();
            }
        });

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

        container.getChildren().add(XOimageView);

        container.getChildren().add(userNameTextField);
        container.getChildren().add(labelForUserName);
        container.getChildren().add(fullNameTextField);
        container.getChildren().add(labelForFullName);
        container.getChildren().add(emailTextField);
        container.getChildren().add(labelForEmail);
        container.getChildren().add(passwordTextField);
        container.getChildren().add(label1ForPassword);
        container.getChildren().add(label2ForPassword);
        container.getChildren().add(signUpBtn);
        getChildren().add(container);
        getChildren().add(back);

    }

}
