package inventory;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.*;
import java.util.Date;

/**
 * Created by SSR on 7/12/2016.
 */
public class Items {
    SimpleStringProperty custom_label, notes;
    SimpleBooleanProperty defective, returns, incomplete;
    Date date;
    SimpleIntegerProperty quantity;

    public Items(){

    }

    public Items(String _custom_label, Integer _quantity, boolean _defective, boolean _returns, boolean _incomplete, java.sql.Date _date, String _notes){
        custom_label = new SimpleStringProperty(this, "",_custom_label);
        quantity = new SimpleIntegerProperty(this, "",_quantity);
        defective = new SimpleBooleanProperty(this, "",_defective);
        returns = new SimpleBooleanProperty(this, "",_returns);
        incomplete = new SimpleBooleanProperty(this, "",_incomplete);
        date = _date;
        //try{setDate(_date);}catch(ParseException e){e.printStackTrace();}
        notes = new SimpleStringProperty(this,"",_notes);
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

    private Date getDate(){
        return date;
    }

    private void setDate(String newvalue) throws ParseException{
       DateFormat df =  new SimpleDateFormat("dd/mm/yyyy");
        date = df.parse(newvalue);
    }

    private SimpleIntegerProperty getQuantity(){
        return quantity;
    }

    private void setQuantity(SimpleIntegerProperty newQuantity){
        quantity = newQuantity;
    }
}
