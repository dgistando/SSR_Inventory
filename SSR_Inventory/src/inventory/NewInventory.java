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

    private String supplier,date,invoice;
    private boolean verified;

    NewInventory(){
        this("","","",false);
    }

    public NewInventory(String supplier, String date, String invoice, boolean verified) {
        this.supplier = supplier;
        this.date = date;
        this.invoice = invoice;
        this.verified = verified;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
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
            Text text = new Text(item.getDate() + "\n" + item.getInvoice());
            text.setStyle("-fx-font:13 Ariel;-fx-font-weight: bold;-fx-text-fill:#010101;");
            Text text1 = new Text(item.getSupplier());
            text.setStyle("-fx-font:16 Ariel;-fx-font-weight: bold;-fx-text-fill:#010101;");

            VBox vBox = new VBox(text1, text);
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
