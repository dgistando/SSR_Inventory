package inventory;

import javafx.beans.property.*;

import java.text.*;
import java.util.Date;

/**
 * Created by SSR on 7/12/2016.
 */
public class Items {
    private final StringProperty customLabel = new SimpleStringProperty("");
    private final StringProperty notes = new SimpleStringProperty("");
    private final BooleanProperty defective = new SimpleBooleanProperty(false);
    private final BooleanProperty returns = new SimpleBooleanProperty(false);
    private final BooleanProperty incomplete = new SimpleBooleanProperty(false);
    private Date date = new Date(0);
    private final IntegerProperty quantity = new SimpleIntegerProperty(0);

    public Items(){
        this("",0,false,false,false,new java.sql.Date(0),"");
    }

    public Items(String _custom_label, Integer _quantity, boolean _defective, boolean _returns, boolean _incomplete, java.sql.Date _date, String _notes){
        setCustomLabel(_custom_label);//custom_label = new SimpleStringProperty(_custom_label);
        setQuantity(_quantity); //= new SimpleIntegerProperty(_quantity);
        setDefective(_defective);//defective = new SimpleBooleanProperty(_defective);
        setReturns(_returns);//returns = new SimpleBooleanProperty(_returns);
        setIncomplete(_incomplete);//incomplete = new SimpleBooleanProperty(_incomplete);
        //setDate(_date);//
        try{setDate(_date);}catch(ParseException e){e.printStackTrace();}
        setNotes(_notes);//notes = new SimpleStringProperty(_notes);
    }

    public String getCustomLabel(){
        return customLabel.get();
    }

    private String getNotes(){
        return notes.get();
    }

    private Boolean getDefective(){
        return defective.get();
    }

    private Boolean getReturns(){
        return returns.get();
    }

    private Boolean getIncomplete(){
        return incomplete.get();
    }

    private Date getDate(){
        return date;
    }

    private Integer getQuantity(){
        return quantity.get();
    }

    private void setCustomLabel(String input){
        customLabel.set(input);
    }
    private void setNotes(String input){
        notes.set(input);
    }
    private void setDefective(Boolean newvalue){
        defective.set(newvalue);
    }
    private void setReturns(Boolean newvalue){
        returns.set(newvalue);
    }
    private void setIncomplete(Boolean newvalue){
        incomplete.set(newvalue);
    }
    private void setDate(Date newvalue) throws ParseException{
       //DateFormat df =  new SimpleDateFormat("dd/mm/yyyy");
        //date = df.parse(newvalue);
        date = newvalue;
    }
    private void setQuantity(Integer newQuantity){
        quantity.set(newQuantity);
    }
}
