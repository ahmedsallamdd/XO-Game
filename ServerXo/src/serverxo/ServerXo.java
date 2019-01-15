/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverxo;

import java.rmi.RemoteException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Mohamed
 */
public class ServerXo extends Application {

    @Override
    public void start(Stage primaryStage) throws RemoteException, ClassNotFoundException, SQLException {

        ServerAppFxmlBase root = new ServerAppFxmlBase();

        Scene scene = new Scene(root, 700, 560);

        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
