package serverxo;

import commontxo.ClientCallBack;
import commontxo.Player;
import commontxo.Utilites;
import java.io.IOException;
import java.net.Socket;
import java.rmi.AccessException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.text.Font;

public class ServerAppFxmlBase extends AnchorPane {

    protected final ListView listView;
    protected final Button btnOpen;
    protected final Button btnClose;
    protected final Button btnRefresh;
    ServerMessageImp obj;
    static Registry reg;
    ArrayList<String> playersInListView = new ArrayList<>();

    final static String Service = "GameService";

    public ServerAppFxmlBase() throws RemoteException, ClassNotFoundException, SQLException {

        listView = new ListView();
        btnOpen = new Button();
        btnClose = new Button();
        btnRefresh = new Button();

        setId("AnchorPane");
        setPrefHeight(600.0);
        setPrefWidth(650.0);
        setStyle("-fx-background-color: linear-gradient(#247ba0 0%,#70c1b3 50% ,#247ba0 100%);");

        listView.setLayoutX(14.0);
        listView.setLayoutY(9.0);
        listView.setPrefHeight(568.0);
        listView.setPrefWidth(853.0);

        AnchorPane.setTopAnchor(btnOpen, 605.0);
        btnOpen.setLayoutX(120.0);
        btnOpen.setLayoutY(625.0);
        btnOpen.setMnemonicParsing(false);
        btnOpen.setPrefHeight(44.0);
        btnOpen.setPrefWidth(200.0);
        btnOpen.setStyle("-fx-background-color:  #0b3c49; -fx-background-radius: 10;");
        btnOpen.setText("Open Server");
        btnOpen.setTextFill(javafx.scene.paint.Color.WHITE);
        btnOpen.setFont(new Font(19.0));

        AnchorPane.setTopAnchor(btnRefresh, 605.0);
        btnRefresh.setLayoutX(370.0);
        btnRefresh.setLayoutY(610.0);
        btnRefresh.setMnemonicParsing(false);
        btnRefresh.setPrefHeight(44.0);
        btnRefresh.setPrefWidth(200.0);
        btnRefresh.setStyle("-fx-background-color:  #0b3c49; -fx-background-radius: 10;");
        btnRefresh.setText("Refresh Players List");
        btnRefresh.setTextFill(javafx.scene.paint.Color.WHITE);
        btnRefresh.setFont(new Font(19.0));

        AnchorPane.setTopAnchor(btnClose, 605.0);
        btnClose.setLayoutX(600.0);
        btnClose.setLayoutY(625.0);
        btnClose.setMnemonicParsing(false);
        btnClose.setPrefHeight(44.0);
        btnClose.setPrefWidth(200.0);
        btnClose.setStyle("-fx-background-color:  #0b3c49; -fx-background-radius: 10;");
        btnClose.setText("Close Server");
        btnClose.setTextFill(javafx.scene.paint.Color.WHITE);
        btnClose.setFont(new Font(19.0));
        btnClose.setDisable(true);
        btnRefresh.setDisable(true);
        getChildren().add(listView);
        getChildren().add(btnOpen);
        getChildren().add(btnClose);
        getChildren().add(btnRefresh);
        playersInListView = new ArrayList<>();
        //showList(obj.PlayersInformation);
        btnRefresh.setOnAction((ActionEvent event) -> {
            playersInListView.clear();
            showList(obj.PlayersInformation);
        });
        btnOpen.setOnAction((event) -> {
            try {
                openServer(Service, 1099);
            } catch (ClassNotFoundException e) {
                ServerXo.showErrorAlert();
            } catch (SQLException ex) {
                ServerXo.showErrorAlert();
            }
        });
        btnClose.setOnAction((event) -> {
            try {
                closeServer(Service);
            } catch (NoSuchObjectException ex) {
                ServerXo.showErrorAlert();
            }
        });
    }

    private void showList(ArrayList<Player> k) {
        if (!k.isEmpty()) {
            playersInListView.add("Score" + "\t\t\t\t\t" + "state" + "\t \t \t \t \t" + " \t UserName");
        }
        for (Iterator<Player> it = k.iterator(); it.hasNext();) {
            Player player = it.next();
            playersInListView.add(player.getPlayerScore() + "\t \t \t \t \t    \t"
                    + player.getPlayerState() + "\t \t \t \t \t   " + player.getPlayerUserName());
        }
        ListProperty<String> listProperty = new SimpleListProperty<>();
        listProperty.set(FXCollections.observableArrayList(playersInListView));
        listView.itemsProperty().bind(listProperty);

    }

    private void openServer(String Service, int port) throws ClassNotFoundException, SQLException {
        if (isPortInUse(port) == false) {
            try {
                obj = new ServerMessageImp();
                if (reg == null) {
                    reg = LocateRegistry.createRegistry(port);
                    showList(obj.PlayersInformation);
                }
                reg.rebind(Service, obj);
                btnClose.setDisable(false);
                btnRefresh.setDisable(false);
                btnOpen.setDisable(true);
            } catch (RemoteException ex) {
                ServerXo.showErrorAlert();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(10);
            alert.setHeaderText(null);
            alert.setContentText("Server is already open!");
            alert.showAndWait();
            System.exit(0);
        }
    }

    public void closeServer(String Service) throws NoSuchObjectException {

        try {
            //clients foreach . leave server
            for (Iterator<Player> it = obj.PlayersInformation.iterator(); it.hasNext();) {
                Player player = it.next();
                obj.updateScore(player.getPlayerUserName(), player.getPlayerScore());
//                obj.clients.get(player.getPlayerUserName()).serverUnavilable();
            }
            ArrayList<ClientCallBack> x = new ArrayList<>(obj.clients.values());
            for (Iterator<ClientCallBack> it = x.iterator(); it.hasNext();) {
                ClientCallBack client = it.next();
                try {
                    client.serverUnavilable();
                } catch (RemoteException ex) {
                    obj.leaveServer(Utilites.getKeyByValue(obj.clients, client));
                }
            }

//            for (Iterator<Map.Entry<String, ClientCallBack>> it = obj.clients.entrySet().iterator(); it.hasNext();) {
//                Map.Entry<String, ClientCallBack> entry = it.next();
//                entry.getValue().serverUnavilable();
////               obj.leaveServer(entry.getKey());
//            }
            reg.unbind(Service);
            UnicastRemoteObject.unexportObject(obj, true);
            playersInListView.clear();
            obj.PlayersInformation.clear();
            showList(obj.PlayersInformation);
            obj = null;
            btnClose.setDisable(true);
            btnRefresh.setDisable(true);
            btnOpen.setDisable(false);

        } catch (RemoteException ex) {
            ServerXo.showErrorAlert();
        } catch (NotBoundException ex) {
            ServerXo.showErrorAlert();
        }

    }

    boolean isPortInUse(int portNumber) {
        boolean result;
        try {
            Socket s = new Socket("127.0.0.1", portNumber);
            s.close();
            result = true;
        } catch (IOException e) {
            result = false;
        }

        return result;

    }
}
