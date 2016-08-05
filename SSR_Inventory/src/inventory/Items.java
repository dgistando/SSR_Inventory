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
    private final IntegerProperty netSaleable;
    private final IntegerProperty returns; // = new SimpleBooleanProperty(false);
    private final IntegerProperty defective; /// =
    private final IntegerProperty incomplete; // = new SimpleBooleanProperty(false);
    private final StringProperty notes; //= new SimpleStringProperty("");

    private final IntegerProperty quantity;// = new SimpleIntegerProperty(0);
    private final StringProperty packingInformation;
    private Date date = new Date(0);


    protected Items(){
        this("",0,0,0,0,"",0,"",new java.sql.Date(0));
    }

    protected Items(String _custom_label, Integer net_saleable, int _returns, int _defective, int _incomplete, String _notes, Integer _quantity, String _packingInformation, java.sql.Date _date){
         this.customLabel = new SimpleStringProperty(_custom_label);
         this.netSaleable = new SimpleIntegerProperty(net_saleable);
         this.returns = new SimpleIntegerProperty(_returns);
         this.defective = new SimpleIntegerProperty(_defective);
         this.incomplete = new SimpleIntegerProperty(_incomplete);
         this.notes = new SimpleStringProperty(_notes);

        //setDate(_date);//
        this.packingInformation = new SimpleStringProperty(_packingInformation);
        try{setDate(_date);}catch(ParseException e){e.printStackTrace();}
        this.quantity = new SimpleIntegerProperty(_quantity);
    }



    public String getNotes(){
        return notes.get();
    }

    public Integer getDefective(){
        return defective.get();
    }

    public Integer getReturns(){
        return returns.get();
    }

    public Integer getIncomplete(){
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

    public Integer getNetSaleable(){
        return netSaleable.get();
    }

    public String getPackingInformation() {
        return packingInformation.get();
    }

    public StringProperty packingInformationProperty() {
        return packingInformation;
    }

    public void setPackingInformation(String packingInformation) {
        this.packingInformation.set(packingInformation);
    }

    public void setCustomLabel(String input){
        customLabel.set(input);
    }
    public void setQuantity(Integer newQuantity){
        quantity.set(newQuantity);
    }
    public void setDefective(Integer newvalue){
        defective.set(newvalue);
    }
    public void setReturns(Integer newvalue){
        returns.set(newvalue);
    }
    public void setIncomplete(Integer newvalue){
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
    public void setNetSaleable(Integer newvalue){netSaleable.set(newvalue);}
}
