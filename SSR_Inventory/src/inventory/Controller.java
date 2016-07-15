package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.apache.poi.ss.formula.functions.T;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


/**
 * This class controls the login page for Sling Shot racing Inventory
 * The controller is linked to the login.fxml and uses the database backend to verify
 * accounts. There are no inputs or outputs to this page.
 *
 * @author  David Gistand of SSR
 *
 */
public class Controller implements Initializable{

    @FXML
    Button Btnlogin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert Btnlogin != null : "Button Btnlogin not found";


        Btnlogin.setOnAction(e-> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("view_inventory.fxml"));
                //Scene scene = new Scene(root,450,450);

                //TPinventory.getTabs().addAll(InventoryTable, InventoryTable1);

                Stage stage = new Stage();
                stage.setTitle("SSR_Inventory");
                stage.setScene(new Scene(root, 450, 450));
                stage.show();


                //hide this current window
                ((Node) (e.getSource())).getScene().getWindow().hide();
            }catch (IOException err){
                err.printStackTrace();
            }
        });

    }


}
