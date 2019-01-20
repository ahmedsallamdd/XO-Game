package serverxo;

import commontxo.ClientCallBack;
import commontxo.Player;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
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
        setPrefHeight(729.0);
        setPrefWidth(888.0);

        listView.setLayoutX(14.0);
        listView.setLayoutY(9.0);
        listView.setPrefHeight(568.0);
        listView.setPrefWidth(853.0);

        AnchorPane.setTopAnchor(btnOpen, 620.0);
        btnOpen.setLayoutX(172.0);
        btnOpen.setLayoutY(631.0);
        btnOpen.setMnemonicParsing(false);
        btnOpen.setPrefHeight(44.0);
        btnOpen.setPrefWidth(87.0);
        btnOpen.setStyle("-fx-background-color: #ff7764; -fx-background-radius: 10;");
        btnOpen.setText("Open");
        btnOpen.setTextFill(javafx.scene.paint.Color.WHITE);
        btnOpen.setFont(new Font(19.0));

        AnchorPane.setTopAnchor(btnOpen, 620.0);
        btnRefresh.setLayoutX(370.0);
        btnRefresh.setLayoutY(625.0);
        btnRefresh.setMnemonicParsing(false);
        btnRefresh.setPrefHeight(44.0);
        btnRefresh.setPrefWidth(87.0);
        btnRefresh.setStyle("-fx-background-color: #ff7764; -fx-background-radius: 10;");
        btnRefresh.setText("refresh");
        btnRefresh.setTextFill(javafx.scene.paint.Color.WHITE);
        btnRefresh.setFont(new Font(19.0));

        AnchorPane.setTopAnchor(btnClose, 625.0);
        btnClose.setLayoutX(574.0);
        btnClose.setLayoutY(631.0);
        btnClose.setMnemonicParsing(false);
        btnClose.setPrefHeight(44.0);
        btnClose.setPrefWidth(87.0);
        btnClose.setStyle("-fx-background-color: #ff7764; -fx-background-radius: 10;");
        btnClose.setText("Close");
        btnClose.setTextFill(javafx.scene.paint.Color.WHITE);
        btnClose.setFont(new Font(19.0));

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
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerAppFxmlBase.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ServerAppFxmlBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnClose.setOnAction((event) -> {
            try {
                closeServer(Service);
            } catch (NoSuchObjectException ex) {
                Logger.getLogger(ServerAppFxmlBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void showList(ArrayList<Player> k) {
        if (k.size() != 0) {
            playersInListView.add("Score" + "\t\t\t\t\t" + "UserName" + "\t \t \t \t \t" + "state");
        }
        for (Player player : k) {
            playersInListView.add(player.getPlayerScore() + "\t \t \t \t \t    \t" + player.getPlayerUserName() + "\t \t \t \t \t   " + player.getPlayerState());

        }
        ListProperty<String> listProperty = new SimpleListProperty<>();
        listProperty.set(FXCollections.observableArrayList(playersInListView));
        listView.itemsProperty().bind(listProperty);

    }

    private void openServer(String Service, int port) throws ClassNotFoundException, SQLException {

        try {
            obj = new ServerMessageImp();

            showList(obj.PlayersInformation);
            if (reg == null) {
                reg = LocateRegistry.createRegistry(port);
            }
            reg.rebind(Service, obj);
            btnClose.setDisable(false);
            btnOpen.setDisable(true);
        } catch (RemoteException ex) {
            System.err.println(ex.getMessage() + "ok");
        }

    }

    public void closeServer(String Service) throws NoSuchObjectException {

        try {
            //clients foreach . leave server
            for (Player player : obj.PlayersInformation) {
                obj.updateScore(player.getPlayerUserName(), player.getPlayerScore());
//                obj.clients.get(player.getPlayerUserName()).serverUnavilable();
            }

            obj.clients.forEach((a,client)->{
                try {
                    client.serverUnavilable();
                } catch (RemoteException ex) {
                    System.out.println("This Client is out aready");
                }
            });

            reg.unbind(Service);
            UnicastRemoteObject.unexportObject(obj, true);
            playersInListView.clear();
            obj.PlayersInformation.clear();
            showList(obj.PlayersInformation);
            obj = null;
            btnClose.setDisable(true);
            btnOpen.setDisable(false);
        } catch (RemoteException ex) {
            System.err.println(ex.getMessage() + "h");
        } catch (NotBoundException ex) {
            System.err.println("Server didn't bind to close it");
        } catch (NullPointerException ex) {
            System.err.println("you have to Open Server first");
        }

    }
}
