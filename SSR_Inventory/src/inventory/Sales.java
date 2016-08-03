package inventory;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Created by SSR on 7/28/2016.
 */
public class Sales extends ListCell<Sales>{
    private String date;
    //plaanignng to put the Strings in order of the columns in table ie. first column in first name.
    // first name, last name, country, custom name, info.
    private ArrayList<String> information;
    private ArrayList<Integer> quantity;
    private boolean verified;

    public Sales(){
        this("",0,false);
    }

    public Sales(String _date, int _quantity,boolean _verified){
        date = _date;
        information = new ArrayList<String>();
        quantity = new ArrayList<Integer>();
        verified = _verified;
    }

    public String getDate(){
        return date;
    }

    public ArrayList<String> getInformation() {
        return information;
    }

    public void setInformation(ArrayList<String> information) {
        this.information = information;
    }

    public ArrayList<Integer> getAllQuantity() {
        return quantity;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void addToQuantity(int quantity) {
        this.quantity.add(quantity);
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Override
    protected void updateItem(Sales item, boolean empty) {
        super.updateItem(item, empty);
        Rectangle rect = new Rectangle(100, 20);
        if(item != null){
            VBox vBox = new VBox(new Text(item.getDate()), new Text("grjijr"));
            HBox hBox = new HBox();
            ImageView l = new ImageView();

            String imageName;

            if(item.isVerified()){
                imageName="drawable/verified_image1.png";
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
