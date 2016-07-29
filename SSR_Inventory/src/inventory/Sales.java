package inventory;

import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by SSR on 7/28/2016.
 */
public class Sales extends ListCell<Sales>{
    private String date;
    private String firstName,lastName,country;
    private String customName,info;
    private int quantity;

    public Sales(){
        this("","","","","","",0);
    }

    public Sales(String _date,String _firstName, String _lastName, String _country, String _customName, String _info, int _quantity){
        date = _date;
        firstName = _firstName;
        lastName = _lastName;
        country = _country;
        customName = _customName;
        info = _info;
        quantity = _quantity;
    }

    public String getDate(){
        return date;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getInfo() {
        return info;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCustomName() {
        return customName;
    }

    public String getCountry() {
        return country;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCustomeName(String customName) {
        this.customName = customName;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    protected void updateItem(Sales item, boolean empty) {
        super.updateItem(item, empty);
        Rectangle rect = new Rectangle(100, 20);
        if (item != null) {
            rect.setFill(Color.web("red"));
            setGraphic(rect);
        }
    }
}
