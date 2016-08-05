package inventory;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Created by SSR on 8/4/2016.
 */
public class NewInventory extends ListCell<NewInventory> {

    private boolean verified;
    private String date;

    NewInventory(){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Override
    protected void updateItem(NewInventory item, boolean empty) {
        super.updateItem(item, empty);
        Rectangle rect = new Rectangle(100, 20);
        if(item != null){
            VBox vBox = new VBox(new Text(item.getDate()), new Text("grjijr"));
            HBox hBox = new HBox();
            ImageView l = new ImageView();

            String imageName;

            if(item.isVerified()){
                imageName="drawable/verified_image4.png";
                Image image = new Image(Sales.class.getResourceAsStream(imageName));
                l.setImage(image);
                l.setStyle("-fx-background-color: #FFFF66");
                hBox.setStyle("-fx-background-color: #FFFF66");
            }else{
                l.setVisible(false);
            }



            //l.setAlignment(Pos.CENTER_RIGHT);
            hBox.getChildren().addAll(vBox, l);
            hBox.setSpacing(10);
            hBox.setHgrow(hBox.getChildren().get(0), Priority.ALWAYS);
            setGraphic(hBox);
        }
    }
}
