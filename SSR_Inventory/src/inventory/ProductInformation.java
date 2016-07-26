package inventory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

import static inventory.Inventory_Controller.SearchBox;

/**
 * Created by dgist on 7/23/2016.
 */
public class ProductInformation implements Initializable {

    @FXML
    ImageView productImage;

    //make the fxml first, then fill all this in.

    private static final String url = ProductInformation.class.getResource("not_available.png").toString();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert productImage != null:"ImageView doesn't exist";

        productImage = new ImageView(new Image(url, 30, 70, false, true));
    }
}
