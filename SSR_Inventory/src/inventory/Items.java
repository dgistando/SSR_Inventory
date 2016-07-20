package inventory;

import javafx.beans.property.*;
import sun.java2d.pipe.SpanShapeRenderer;

import java.text.*;
import java.util.Date;

/**
 * Created by SSR on 7/12/2016.
 */
public class Items {
    private final StringProperty customLabel;// = new SimpleStringProperty("");
    private final IntegerProperty quantity;// = new SimpleIntegerProperty(0);
    private final StringProperty notes = new SimpleStringProperty("");
    private final BooleanProperty defective = new SimpleBooleanProperty(false);
    private final BooleanProperty returns = new SimpleBooleanProperty(false);
    private final BooleanProperty incomplete = new SimpleBooleanProperty(false);
    private Date date = new Date(0);


    protected Items(){
        this("",0,false,false,false,new java.sql.Date(0),"");
    }

    protected Items(String _custom_label, Integer _quantity, boolean _defective, boolean _returns, boolean _incomplete, java.sql.Date _date, String _notes){
        /*setCustomLabel(_custom_label);*/this.customLabel = new SimpleStringProperty(_custom_label);
        /*setQuantity(_quantity);*/ this.quantity= new SimpleIntegerProperty(_quantity);
        setDefective(_defective);//defective = new SimpleBooleanProperty(_defective);
        setReturns(_returns);//returns = new SimpleBooleanProperty(_returns);
        setIncomplete(_incomplete);//incomplete = new SimpleBooleanProperty(_incomplete);
        //setDate(_date);//
        try{setDate(_date);}catch(ParseException e){e.printStackTrace();}
        setNotes(_notes);//notes = new SimpleStringProperty(_notes);
    }



    public String getNotes(){
        return notes.get();
    }

    public Boolean getDefective(){
        return defective.get();
    }

    public Boolean getReturns(){
        return returns.get();
    }

    public Boolean getIncomplete(){
        return incomplete.get();
    }

    public Date getDate(){
        return date;
    }

    public Integer getQuantity(){
        return quantity.get();
    }

    public String getCustomLabel(){
        return customLabel.get();
    }

    public void setCustomLabel(String input){
        customLabel.set(input);
    }

    public void setQuantity(Integer newQuantity){
        quantity.set(newQuantity);
    }
    public void setDefective(Boolean newvalue){
        defective.set(newvalue);
    }
    public void setReturns(Boolean newvalue){
        returns.set(newvalue);
    }
    public void setIncomplete(Boolean newvalue){
        incomplete.set(newvalue);
    }
    public void setDate(Date newvalue) throws ParseException{
       //DateFormat df =  new SimpleDateFormat("dd/mm/yyyy");
        //date = df.parse(newvalue);
        date = newvalue;
    }
    public void setNotes(String input){
        notes.set(input);
    }
}
