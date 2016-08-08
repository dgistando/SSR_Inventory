package inventory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Calendar;
import java.util.Optional;

import static inventory.Controller.dbHelper;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        root.setStyle("-fx-background-color: white;");
        primaryStage.setTitle("SSR Inventory");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if(!dbHelper.getUSERNAME().equals("") && dbHelper.usernameOfEditor().equals(dbHelper.getUSERNAME()) ){
            dbHelper.commitChanges();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
