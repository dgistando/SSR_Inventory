package inventory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    ImageView imageView;
    Image img;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert imageView != null:"the image might not exist";
        img = new Image("file:logo.png");
        imageView = new ImageView(img);


    }
}
