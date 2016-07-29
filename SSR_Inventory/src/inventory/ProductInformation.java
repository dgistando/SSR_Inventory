package inventory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static inventory.Inventory_Controller.SearchBox;

/**
 * Created by dgist on 7/23/2016.
 */
public class ProductInformation implements Initializable {

    @FXML
    ImageView productImage;
    @FXML
    GridPane gridPane;
    @FXML
    Text infoText, infoText1;

    //make the fxml first, then fill all this in.

  //  private static final String url = ProductInformation.class.getResource("not_available.png").toString();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert productImage != null:"ImageView doesn't exist";
        assert gridPane != null: "what happened to the gridpane";

 //       productImage = new ImageView(new Image(url, 30, 70, false, true));

        gridPane.setPadding(new Insets(5,5,5,5));
        infoText.setFont(Font.font("Ariel",14));
        infoText1.setFont(Font.font("Ariel",14));
    }
}
