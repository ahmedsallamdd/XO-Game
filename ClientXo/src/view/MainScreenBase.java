package view;

import controller.GameController;
import controller.MyGui;
import commontxo.PlayerList;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class MainScreenBase extends AnchorPane {

    protected final Button btnPlayWithComputer;
    public final ListView<PlayerList> listView;
    protected final Label label;
    protected final Label label0;
    protected final Label label1;
    protected final Label label2;
    protected final Button button0;
    protected final ImageView imageView;

    GameController controller;
    MyGui myGui;

    public MainScreenBase(MyGui g) {

//        controller = new GameController();
        myGui = g;
        btnPlayWithComputer = new Button();
        listView = new ListView<>();
        label = new Label();
        label0 = new Label();
        label1 = new Label();
        label2 = new Label();
        button0 = new Button();
        imageView = new ImageView();

        setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        btnPlayWithComputer.setLayoutX(318.0);
        btnPlayWithComputer.setLayoutY(165.0);
        btnPlayWithComputer.setMnemonicParsing(false);
        btnPlayWithComputer.setPrefHeight(66.0);
        btnPlayWithComputer.setPrefWidth(147.0);
        btnPlayWithComputer.setText("Single Player");

        btnPlayWithComputer.setOnAction((e -> {
            myGui.createSinglePlayerScreen();
            //controller.playWithComputer();

            // TODO: clean the matherfuken code from the singlePlayerGui to the controller
        }));

        listView.setLayoutX(14.0);
        listView.setLayoutY(14.0);
        listView.setPrefHeight(335.0);
        listView.setPrefWidth(200.0);

        label.setLayoutX(267.0);
        label.setLayoutY(18.0);
        label.setText("Player :");

        label0.setLayoutX(317.0);
        label0.setLayoutY(18.0);
        label0.setText("Sallam");

        label1.setLayoutX(405.0);
        label1.setLayoutY(18.0);
        label1.setText("Score :");

        label2.setLayoutX(459.0);
        label2.setLayoutY(18.0);
        label2.setText("1523");

        button0.setLayoutX(14.0);
        button0.setLayoutY(361.0);
        button0.setMnemonicParsing(false);
        button0.setPrefHeight(25.0);
        button0.setPrefWidth(169.0);
        button0.setText("Refresh");

        imageView.setFitHeight(17.0);
        imageView.setFitWidth(20.0);
        imageView.setLayoutX(561.0);
        imageView.setLayoutY(22.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("../images/Logout.png").toExternalForm()));
        imageView.setOnMousePressed(e->{
            myGui.signOut();
        });

        getChildren().add(btnPlayWithComputer);
        getChildren().add(listView);
        getChildren().add(label);
        getChildren().add(label0);
        getChildren().add(label1);
        getChildren().add(label2);
        getChildren().add(button0);
        getChildren().add(imageView);
//        try {
//            //        ArrayList<PlayerList> playerList =  myGui.getPlayerListData();
//            myGui.getPlayerListData();
//        } catch (RemoteException ex) {
//            Logger.getLogger(MainScreenBase.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    
    public void populateListView(ArrayList<PlayerList> playerList) {
        //put data in listview
//        ListProperty<String> listProperty = new SimpleListProperty<>();
//        listProperty.set(FXCollections.observableArrayList(playerList));
//        listView.itemsProperty().bind(listProperty);
        System.out.println("menjfkswkspl");
        ObservableList<PlayerList> list = FXCollections.observableArrayList(playerList);

        listView.setItems(list);
        listView.setCellFactory(new Callback<ListView<PlayerList>, ListCell<PlayerList>>() {
            @Override
            public ListCell<PlayerList> call(ListView<PlayerList> param) {
                return new XCell();
            }
        });

    }

}
