package inventory;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by SSR on 7/12/2016.
 */
public class Items {
    SimpleStringProperty custom_label, notes;
    SimpleBooleanProperty defective, returns, incomplete;
    DateFormat date;
    SimpleIntegerProperty quantity;

    public Items(String _custom_label, Integer _quantity, boolean _defective, boolean _returns, boolean _incomplete, DateFormat _date, String _notes){
        custom_label = new SimpleStringProperty(this, "",_custom_label);
        quantity = new SimpleIntegerProperty(this, "",_quantity);
        defective = new SimpleBooleanProperty(this, "",_defective);
        returns = new SimpleBooleanProperty(this, "",_returns);
        incomplete = new SimpleBooleanProperty(this, "",_incomplete);

    }

    public String getCustom_label(){
        return custom_label.get();
    }

    private void setCustom_label(SimpleStringProperty input){
        custom_label = input;
    }

    private SimpleStringProperty getNotes(){
        return notes;
    }

    private void setNotes(SimpleStringProperty input){
        notes = input;
    }

    private SimpleBooleanProperty getDefective(){
        return defective;
    }

    private void setDefective(SimpleBooleanProperty newvalue){
        defective = newvalue;
    }

    private SimpleBooleanProperty getReturns(){
        return returns;
    }

    private void setReturns(SimpleBooleanProperty newvalue){
        returns = newvalue;
    }

    private SimpleBooleanProperty getIncomplete(){
        return incomplete;
    }

    private void setIncomplete(SimpleBooleanProperty newvalue){
        incomplete = newvalue;
    }

    private DateFormat getDate(){
        return date;
    }

    private void setDate(DateFormat newvalue){
       date = newvalue;
    }

    private SimpleIntegerProperty getQuantity(){
        return quantity;
    }

    private void setQuantity(SimpleIntegerProperty newQuantity){
        quantity = newQuantity;
    }
}
