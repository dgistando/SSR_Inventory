package inventory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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

        Btnlogin.setOnAction(e->{
            Parent root;
            Stage stage;
            try {
                root = FXMLLoader.load(getClass().getResource("view_inventory.fxml"),resources);
                stage = new Stage();
                stage.setTitle("SSR_Inventory");
                stage.setScene(new Scene(root, 450, 450));
                stage.show();

                //hide this current window
                ((Node)(e.getSource())).getScene().getWindow().hide();

            } catch (IOException err) {
                err.printStackTrace();
            }
        });
    }
}
