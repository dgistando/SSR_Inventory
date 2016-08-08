package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.apache.xmlbeans.impl.xb.xmlconfig.NamespaceList;

import java.sql.Date;
import java.util.ArrayList;
import java.util.function.BooleanSupplier;

import static inventory.Controller.dbHelper;
import static inventory.Inventory_Controller.changesMade;
import static inventory.Inventory_Controller.programInfo;
import static inventory.Inventory_Controller.save;

/**
 * This table class gives the possibility of adding dynamic instances
 * of a table to the system at all times. The class holds a list of item
 * that can be changed at the appropriate time to be displayed in the table.
 *
 *Created by SSR on 7/12/2016.
 */
public class InventoryTable extends TableView{
    ObservableList<Items> items;

    public InventoryTable() {
        /*items = FXCollections.observableArrayList(
                new Items("first thing"),
                new Items("second thing")
        );*/
        //dbHelper = new DBHelper();
        //items = BDHelper.getallitmes or something like this; // do something like this here.
    }

    public void getAllInventory(){this.getColumns().addAll(getLabelColumn(),getNetSaleable(),getReturnsColumn(),getDefectiveColumn(),getIncompleteColumn(),getNotesColumn());}

   // public void getReturnsInventory(){this.getColumns().addAll(getLabelColumn(),getQuantityColumn());}

    private TableColumn<Items,String> getLabelColumn(){
        TableColumn<Items,String> labelColumn = new TableColumn<Items,String>("");
        labelColumn.setCellValueFactory(new PropertyValueFactory<Items,String>("customLabel"));
        labelColumn.setStyle("-fx-font: 15.5 Ariel;");
        labelColumn.setMinWidth(200.0);
        labelColumn.setMaxWidth(300.0);
        labelColumn.setGraphic(setColumnName("CUSTOM LABEL"));
        return labelColumn;
    }

    private TableColumn<Items,Integer> getQuantityColumn(){
        TableColumn<Items,Integer> quantityColumn = new TableColumn<Items,Integer>("");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Items,Integer>("quantity"));
        quantityColumn.setStyle("-fx-font: 15.5 Ariel;");
        quantityColumn.setPrefWidth(120.0);
        quantityColumn.setMaxWidth(120.0);
        quantityColumn.setMinWidth(120.0);

        quantityColumn.setGraphic(setColumnName("QUANTITY"));
        return quantityColumn;
    }


    private TableColumn<Items,Integer> getNetSaleable(){
        TableColumn<Items,Integer> netSaleableColumn = new TableColumn<Items,Integer>("");
        netSaleableColumn.setCellValueFactory(new PropertyValueFactory<Items,Integer>("netSaleable"));
        netSaleableColumn.setStyle("-fx-font: 15.5 Ariel; -fx-alignment: CENTER;");
        netSaleableColumn.setPrefWidth(120.0);
        netSaleableColumn.setMaxWidth(120.0);
        netSaleableColumn.setMinWidth(120.0);
        netSaleableColumn.setGraphic(setColumnName("NET SALEABLE"));
        netSaleableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        netSaleableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Items, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Items, Integer> event) {
                if(event.getNewValue() != event.getOldValue()){//changes made
                    System.out.println("changes wer made");
                    if(dbHelper.isUserEditing()){
                        //set status bar to who is editing in red
                        //System.out.println(dbHelper.whoEditing());
                        programInfo.setText(dbHelper.whoEditing());
                        return;
                    }else{
                        dbHelper.recordChange("UPDATE Inventory SET net_saleable=" + event.getNewValue() + " WHERE custom_label='" + event.getRowValue().getCustomLabel() + "';");
                        dbHelper.setUserEditing();
                        changesMade.setText(dbHelper.getNumChanges()+" UNSAVED CHANGES");
                        changesMade.setVisible(true);
                        save.setVisible(true);
                    }
                }

                ((Items) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())).setNetSaleable(event.getNewValue());

            }
        });
        return netSaleableColumn;
    }

    private TableColumn<Items,Integer> getDefectiveColumn(){
        TableColumn<Items,Integer> defectiveColumn = new TableColumn<Items,Integer>("");
        defectiveColumn.setCellValueFactory(new PropertyValueFactory<Items,Integer>("defective"));
        defectiveColumn.setStyle("-fx-font: 15.5 Ariel; -fx-alignment: CENTER");
        defectiveColumn.setMinWidth(120);
        defectiveColumn.setMaxWidth(120);
        defectiveColumn.setGraphic(setColumnName("DEFECTIVE"));
        defectiveColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        defectiveColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Items, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Items, Integer> event) {

                if(event.getNewValue() != event.getOldValue()){//changes made
                    System.out.println("changes wer made");
                    if(dbHelper.isUserEditing()){
                        //set status bar to who is editing
                        programInfo.setText(dbHelper.whoEditing());
                        return;
                    }else {
                        dbHelper.recordChange("UPDATE Inventory SET defective=" + event.getNewValue() + " WHERE custom_label='" + event.getRowValue().getCustomLabel() + "';");
                        dbHelper.setUserEditing();
                        changesMade.setText(dbHelper.getNumChanges()+" UNSAVED CHANGES");
                        changesMade.setVisible(true);
                        save.setVisible(true);
                    }
                }

                ((Items) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())).setNetSaleable(event.getNewValue());
            }
        });
        return defectiveColumn;
    }

    private TableColumn<Items,Integer> getIncompleteColumn(){
        TableColumn<Items,Integer> incompleteColumn = new TableColumn<Items,Integer>("");
        incompleteColumn.setCellValueFactory(new PropertyValueFactory<Items,Integer>("incomplete"));
        incompleteColumn.setStyle("-fx-font: 15.5 Ariel; -fx-alignment: CENTER;");
        incompleteColumn.setMinWidth(120);
        incompleteColumn.setMaxWidth(120);
        incompleteColumn.setGraphic(setColumnName("INCOMPLETE"));
        incompleteColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        incompleteColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Items, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Items, Integer> event) {

                if(event.getNewValue() != event.getOldValue()){//changes made
                    System.out.println("changes wer made");
                    if(dbHelper.isUserEditing()){
                        //set status bar to who is editing
                        programInfo.setText(dbHelper.whoEditing());
                        return;
                    }else {
                        dbHelper.recordChange("UPDATE Inventory SET incomplete=" + event.getNewValue() + " WHERE custom_label='" + event.getRowValue().getCustomLabel() + "';");
                        dbHelper.setUserEditing();
                        changesMade.setText(dbHelper.getNumChanges()+" UNSAVED CHANGES");
                        changesMade.setVisible(true);
                        save.setVisible(true);
                    }

                }

                ((Items) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())).setNetSaleable(event.getNewValue());
            }
        });
        return incompleteColumn;
    }

    private TableColumn<Items,Integer> getReturnsColumn(){
        TableColumn<Items,Integer> returnsColumn = new TableColumn<Items,Integer>("");
        returnsColumn.setCellValueFactory(new PropertyValueFactory<Items,Integer>("returns"));
        returnsColumn.setStyle("-fx-font: 15.5 Ariel; -fx-alignment: CENTER;");
        returnsColumn.setMinWidth(120);
        returnsColumn.setMaxWidth(120);
        returnsColumn.setGraphic(setColumnName("RETURNS"));
        returnsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        returnsColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Items, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Items, Integer> event) {

                System.out.println("inside handle false " + event.getNewValue());

                if(event.getNewValue() != event.getOldValue()){//changes made
                    System.out.println("changes wer made");
                    if(dbHelper.isUserEditing()){
                        //set status bar to who is editing
                        programInfo.setText(dbHelper.whoEditing());
                        return;
                    }else {
                        dbHelper.recordChange("UPDATE Inventory SET returns=" + event.getNewValue() + " WHERE custom_label='" + event.getRowValue().getCustomLabel() + "';");
                        dbHelper.setUserEditing();
                        changesMade.setText(dbHelper.getNumChanges()+" UNSAVED CHANGES");
                        changesMade.setVisible(true);
                        save.setVisible(true);
                    }
                }

                ((Items) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())).setNetSaleable(event.getNewValue());
            }
        });
        return returnsColumn;
    }

    private TableColumn<Items,String> getNotesColumn(){
        TableColumn<Items,String> notesColumn = new TableColumn<Items,String>("");
        notesColumn.setCellValueFactory(new PropertyValueFactory<Items,String>("notes"));
        notesColumn.setStyle("-fx-font: 15.5 Ariel;");
        notesColumn.setMinWidth(20);
        notesColumn.setGraphic(setColumnName("NOTES"));
        return notesColumn;
    }

    private TableColumn<Items,Date> getDateColumn(){
        TableColumn<Items,Date> dateColumn = new TableColumn<Items,Date>("");
        dateColumn.setCellValueFactory(new PropertyValueFactory<Items,Date>("Date"));
        dateColumn.setStyle("-fx-font: 15.5 Ariel;");
        dateColumn.setPrefWidth(120.0);
        dateColumn.setMaxWidth(120.0);
        dateColumn.setMinWidth(120.0);
        dateColumn.setGraphic(setColumnName("DATE"));
        return dateColumn;
    }

    private Label setColumnName(String name){
        Label columnName = new Label();
        columnName.setText(name);
        columnName.setStyle("-fx-font: 16 Ariel; -fx-font-weight: bold;");
        return columnName;
    }

    private boolean isInteger(String s){
        return s.matches("^-?\\d+$");
    }

    private StringConverter<Integer> toInt1(){
        StringConverter<Integer> sc = new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                    System.out.println("inside toString" + object);
                    return String.valueOf(object);
            }

            @Override
            public Integer fromString(String string) {
                if(isInteger(string)){
                    return Integer.parseInt(string);
                }else{
                    System.out.println("inside fromString" + string);
                    return -1846;
                }
            }
        };

        return sc;
    }

}
