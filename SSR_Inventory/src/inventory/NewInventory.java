package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by SSR on 8/4/2016.
 */
public class NewInventory extends ImportList{

    private String supplier,date,invoice;
    private boolean verified;
    private ArrayList<String> customLabel;
    private ArrayList<Integer> quantity,netSaleable,incomplete,defective;
    private File file;

    NewInventory(){
        this("","","",false);
    }

    public NewInventory(File file){
        customLabel = new ArrayList<String>();
        quantity = new ArrayList<Integer>();
        netSaleable = new ArrayList<Integer>();
        incomplete = new ArrayList<Integer>();
        defective = new ArrayList<Integer>();
        setFile(file);
        if(!importToReceiving(file)){showAlert();}
    }

    public NewInventory(String supplier, String date, String invoice, boolean verified) {
        this.supplier = supplier;
        this.date = date;
        this.invoice = invoice;
        this.verified = verified;
        customLabel = new ArrayList<String>();
        quantity = new ArrayList<Integer>();
        netSaleable = new ArrayList<Integer>();
        incomplete = new ArrayList<Integer>();
        defective = new ArrayList<Integer>();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
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



    public String getTitle(){
        return this.title = invoice;
    }

    public String getSource(){
        return getSupplier();
    }

    public int getAllQuantity() {
        return quantity.stream().mapToInt(Integer::intValue).sum();
    }

    public Date getDateInFormat(){

        Date newdate = new Date();

        try {
            DateFormat df = new SimpleDateFormat("MM/dd/yyy");
            newdate = df.parse(getDate());

        }catch (ParseException e){
            e.printStackTrace();
        }
        return newdate;
    }

    public ArrayList<String> getCustomLabel() {
        return customLabel;
    }

    public void setCustomLabel(ArrayList<String> customLabel) {
        this.customLabel = customLabel;
    }

    public ArrayList<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(ArrayList<Integer> quantity) {
        this.quantity = quantity;
    }

    public ArrayList<Integer> getNetSaleable() {
        return netSaleable;
    }

    public void setNetSaleable(ArrayList<Integer> netSaleable) {
        this.netSaleable = netSaleable;
    }

    public ArrayList<Integer> getIncomplete() {
        return incomplete;
    }

    public void setIncomplete(ArrayList<Integer> incomplete) {
        this.incomplete = incomplete;
    }

    public ArrayList<Integer> getDefective() {
        return defective;
    }

    public void setDefective(ArrayList<Integer> defective) {
        this.defective = defective;
    }

    public boolean importToReceiving(File file){

        try {
            FileInputStream inputStream = new FileInputStream(file);

            System.out.println("READING FILE: "+file.getName().toString());

            Workbook workbook = new HSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();


            Row nextRow = rowIterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            //gathering beginning information for each file.
            if(cellIterator.hasNext()){
                Cell cell = cellIterator.next();

                if(cell.getCellType() == Cell.CELL_TYPE_STRING){
                    //String[] arr = cell.getStringCellValue().split("\\p{Upper}\\p{Space}0|1",2);

                    String[] arr  = cell.getStringCellValue().split(" ");

                    setDate(arr[0]);
                    setSupplier(arr[1]);

                    if(arr.length > 2){
                        setInvoice(arr[2]);
                    }

                    System.out.println(getSupplier() + " then " + getDate() + " part " + getInvoice());
                }else{
                    //Not a new Inventory File
                    return false;
                }

            }


            while (rowIterator.hasNext()) {
                nextRow = rowIterator.next();
                cellIterator = nextRow.cellIterator();

                while (cellIterator.hasNext()) {
                    int i=0; boolean bool=false; String str="";
                    Cell cell = cellIterator.next();


                    // System.out.print(cell.getCellType());
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            i = (int)cell.getNumericCellValue();
                            System.out.print(cell.getNumericCellValue());
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            // System.out.print(cell.getBooleanCellValue());
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            // System.out.print(cell.getCellFormula());
                            break;
                        case Cell.CELL_TYPE_STRING:
                            str = cell.getStringCellValue();
                            break;
                        default:
                    }


                    if(cell.getColumnIndex()+1 == 1)
                    {
                        if(incomplete.size() < quantity.size()){
                            incomplete.add(0);
                        }

                        if(defective.size() < quantity.size()){
                            defective.add(0);
                        }

                        System.out.print("custom label: " + str.replaceAll("\\p{Space}",""));
                        customLabel.add(str);
                        quantity.add(0);
                    }
                    else if(cell.getColumnIndex()+1 == 2)
                    {
                        System.out.print("quantity: " + i);
                        netSaleable.add(i);
                        quantity.add(netSaleable.size()-1,i);
                    }else if(cell.getColumnIndex()+1 == 3){
                        incomplete.add(i);
                        quantity.add(netSaleable.size()-1, netSaleable.get(netSaleable.size()-1)+i);
                    }else if(cell.getColumnIndex()+1 == 4){
                        defective.add(i);
                        quantity.add(netSaleable.size()-1, netSaleable.get(netSaleable.size()-1) + incomplete.get(incomplete.size()-1) + i);
                    }



                    System.out.print(" ");
                }
                System.out.println("");
            }

            if(incomplete.size() < quantity.size()){
                incomplete.add(0);
            }

            if(defective.size() < quantity.size()){
                defective.add(0);
            }

        }catch(IOException e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void updateItem(ImportList item, boolean empty) {
        super.updateItem(item, empty);
        Rectangle rect = new Rectangle(100, 20);
        if(item != null){
            Text text = new Text(item.getDate() + "\n" + item.getTitle());
            text.setStyle("-fx-font:13 Ariel;-fx-font-weight: bold;-fx-text-fill:#010101;");
            Text text1 = new Text(item.getSource());
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
        }else {
            setGraphic(null);
        }
    }

    protected ArrayList<TableColumn> setNewInventoryTable(){
        ArrayList<TableColumn> columns = new ArrayList<>();
        TableColumn<newInventoryItems,String> labelColumn = new TableColumn<newInventoryItems,String>("");
        labelColumn.setCellValueFactory(new PropertyValueFactory<newInventoryItems,String>("label"));
        labelColumn.setStyle("-fx-font: 15.5 Ariel;");
        labelColumn.setMinWidth(200.0);
        labelColumn.setMaxWidth(300.0);
        labelColumn.setGraphic(setColumnName("CUSTOM LABEL"));
        columns.add(labelColumn);

        TableColumn<newInventoryItems,Integer> quantityColumn = new TableColumn<newInventoryItems,Integer>("");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<newInventoryItems,Integer>("qty"));
        quantityColumn.setStyle("-fx-font: 15.5 Ariel; -fx-alignment: CENTER;");
        quantityColumn.setMinWidth(120);
        quantityColumn.setMaxWidth(120);
        quantityColumn.setGraphic(setColumnName("QUANTITY"));
        columns.add(quantityColumn);

        TableColumn<newInventoryItems,Integer> netColumn = new TableColumn<newInventoryItems,Integer>("");
        netColumn.setCellValueFactory(new PropertyValueFactory<newInventoryItems,Integer>("net"));
        netColumn.setStyle("-fx-font: 15.5 Ariel; -fx-alignment: CENTER;");
        netColumn.setMinWidth(120);
        netColumn.setMaxWidth(120);
        netColumn.setGraphic(setColumnName("NET SALEABLE"));
        columns.add(netColumn);

        TableColumn<newInventoryItems,Integer> incompleteColumn = new TableColumn<newInventoryItems,Integer>("");
        incompleteColumn.setCellValueFactory(new PropertyValueFactory<newInventoryItems,Integer>("incomplete"));
        incompleteColumn.setStyle("-fx-font: 15.5 Ariel; -fx-alignment: CENTER;");
        incompleteColumn.setMinWidth(120);
        incompleteColumn.setMaxWidth(120);
        incompleteColumn.setGraphic(setColumnName("INCOMPLETE"));
        columns.add(incompleteColumn);

        TableColumn<newInventoryItems,Integer> defectiveColumn = new TableColumn<newInventoryItems,Integer>("");
        defectiveColumn.setCellValueFactory(new PropertyValueFactory<newInventoryItems,Integer>("defective"));
        defectiveColumn.setStyle("-fx-font: 15.5 Ariel; -fx-alignment: CENTER;");
        defectiveColumn.setMinWidth(120);
        defectiveColumn.setMaxWidth(120);
        defectiveColumn.setGraphic(setColumnName("DEFECTIVE"));
        columns.add(defectiveColumn);

        return columns;
    }

    private Label setColumnName(String name){
        Label columnName = new Label();
        columnName.setText(name);
        columnName.setStyle("-fx-font: 16 Ariel; -fx-font-weight: bold;");
        return columnName;
    }

    protected ObservableList<newInventoryItems> addTableData(){
        ObservableList<newInventoryItems> itemList = FXCollections.observableArrayList();

        for(int i = 0; i < customLabel.size(); i++){
            itemList.add(new newInventoryItems(customLabel.get(i),quantity.get(i), netSaleable.get(i),incomplete.get(i),defective.get(i)));

            System.out.println(customLabel.get(i)+" "+quantity.get(i)+ " num: " + i + "\n\n");
        }

        return itemList;
    }

    private class newInventoryItems{
        String label;
        int qty,net,incomplete,defective;

        public newInventoryItems(){this("",0);}

        private newInventoryItems(String label, int qty) {
            this.label = label;
            this.qty = qty;
        }

        private newInventoryItems(String label, int qty, int net, int incomplete, int defective) {
            this.label = label;
            this.qty = qty;
            this.net = net;
            this.incomplete = incomplete;
            this.defective = defective;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public int getNet() {
            return net;
        }

        public void setNet(int net) {
            this.net = net;
        }

        public int getIncomplete() {
            return incomplete;
        }

        public void setIncomplete(int incomplete) {
            this.incomplete = incomplete;
        }

        public int getDefective() {
            return defective;
        }

        public void setDefective(int defective) {
            this.defective = defective;
        }
    }

    private void showAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Incorrect File");
        alert.setContentText("Ooops, there was an error!\nPlease upload a New Inventory Document");

        alert.showAndWait();
    }
}
