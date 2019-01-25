/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverxo;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Mohamed
 */
public class ServerXo extends Application {

    @Override
    public void start(Stage primaryStage) throws RemoteException, ClassNotFoundException, SQLException {

        ServerAppFxmlBase root = new ServerAppFxmlBase();

        Scene scene = new Scene(root, 888, 670);

        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Close");
            alert.setContentText("Are you sure to exit?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                try {
                    root.closeServer(root.Service);
                } catch (NoSuchObjectException ex) {
                    showErrorAlert();
                } finally {
                    Platform.exit();
                    System.exit(0);
                }
            } else {
                event.consume();
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static void showErrorAlert() {
        ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.ERROR,
                    "",
                    ok);
            a.setTitle("Error");
            a.setHeaderText("back to our devoloper,\nthere are a Problem.");
            a.showAndWait();
        });
    }

}
