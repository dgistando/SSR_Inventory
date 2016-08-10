package inventory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
        if(dbHelper != null && dbHelper.areChangesMade()){
            System.out.print("db not null and there are still unsaves");
            ExitDialog();
        }
    }

    public void ExitDialog(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exiting");
        alert.setHeaderText("There are still unsaved changes");
        alert.setContentText("Choose your option.");

        ButtonType SaveAndExit = new ButtonType("Save and Exit");
        ButtonType ExitWithoutSaving = new ButtonType("Exit Without Saving");

        alert.getButtonTypes().setAll(SaveAndExit, ExitWithoutSaving);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == SaveAndExit) {
           dbHelper.commitChanges();
            dbHelper.removeEditor();
        } else if (result.get() == ExitWithoutSaving) {
            // ... user chose "Three"
            dbHelper.discardChanges();
            dbHelper.removeEditor();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
