package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Iterator;

/**
 * Created by SSR on 7/28/2016.
 */
public class Sales extends ImportList{
    private String date,source;
    private char part;
    //plaanignng to put the Strings in order of the columns in table ie. first column in first name.
    // first name, last name, country, custom name, info.
    private ArrayList<String> firstname,lastname,itemCode,country,information;
    private ArrayList<Integer> quantity;
    private boolean verified;
    private int total;
    private File file;

    public Sales(){
        this("","",'Z',0);
    }

    public Sales(File file){
        firstname = new ArrayList<String>();
        lastname = new ArrayList<String>();
        itemCode = new ArrayList<String>();
        country =  new ArrayList<String>();
        information = new ArrayList<String>();
        quantity = new ArrayList<Integer>();
        setFile(file);
        importToSales(file);
    }

    public Sales(String _source, String _date, char _part, int _total){
        date = _date;
        source = _source;
        part = _part;
        total = _total;
        firstname = new ArrayList<String>();
        lastname = new ArrayList<String>();
        itemCode = new ArrayList<String>();
        country =  new ArrayList<String>();
        information = new ArrayList<String>();
        quantity = new ArrayList<Integer>();
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getAllQuantity() {
        return quantity.stream().mapToInt(Integer::intValue).sum();
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

    public char getPart() {
        return part;
    }

    public void setPart(char part) {
        this.part = part;
    }

    public ArrayList<String> getFirstname() {
        return firstname;
    }

    public ArrayList<String> getLastname() {
        return lastname;
    }

    public ArrayList<String> getItemCode() {
        return itemCode;
    }

    public ArrayList<String> getCountry() {
        return country;
    }

    public ArrayList<Integer> getQuantity() {
        return quantity;
    }

    public int getTotal() {
        return total;
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

    public void setTotal() {
        this.total = getAllQuantity();
    }

    public String getTitle(){
       return title + "" + String.valueOf(getPart());
    }

    @Override
    protected void updateItem(ImportList item, boolean empty) {
        super.updateItem(item, empty);
        if(item != null){
            Label label = new Label("  SSR "+item.getSource()+" "+item.getDate()+" "+item.getTitle());
            label.setStyle("-fx-font:28 Ariel;-fx-font-weight: bold;-fx-text-fill:#010101;");

            VBox vbox = new VBox();
            Label itemssold = new Label("ITEMS\nSOLD");
            Label numSold = new Label(item.getTotal()+"    ");
            itemssold.setStyle("-fx-font:14 Ariel;-fx-font-weight: bold;-fx-text-fill:#010101;");
            numSold.setStyle("-fx-font:14 Ariel;-fx-font-weight: bold;-fx-text-fill:#010101;");

            numSold.setAlignment(Pos.CENTER);
            vbox.getChildren().addAll(itemssold,numSold);
            vbox.setAlignment(Pos.CENTER_RIGHT);

            HBox hBox = new HBox();

            hBox.getChildren().addAll(label, vbox);
            hBox.setSpacing(10);
            hBox.setHgrow(hBox.getChildren().get(0), Priority.ALWAYS);
            hBox.setHgrow(hBox.getChildren().get(1), Priority.ALWAYS);
            setGraphic(hBox);
        }else{
            setGraphic(null);
        }
    }

    private void importToSales(File file){
        try {
            FileInputStream inputStream = new FileInputStream(file);

            System.out.println("READING FILE: "+file.getName().toString());

            Workbook workbook = new HSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            int i = -9999;
            String temp="";


            Row nextRow = rowIterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            //gathering beginning information for each file.
            if(cellIterator.hasNext()){
                Cell cell = cellIterator.next();

                if(cell.getCellType() == Cell.CELL_TYPE_STRING && nextRow.getRowNum() == 0 && cell.getStringCellValue().substring(0,3).equals("SSR")){
                    //String[] arr = cell.getStringCellValue().split("\\p{Upper}\\p{Space}0|1",2);

                    String[] arr  = cell.getStringCellValue().split(" ");

                    if(arr.length == 2){
                        setSource("EBAY");
                    }else{
                        setSource(arr[1]);
                    }

                    setDate(arr[arr.length-1]);
                    setPart(getDate().charAt(getDate().length()-1));
                    setDate(date.substring(0,date.length()-1));

                    //System.out.println(getSource() + " then " + getDate() + " part " + getPart());
                }

            }

            String savedfirst="",savedlast="";
            while (rowIterator.hasNext()) {
                nextRow = rowIterator.next();
                cellIterator = nextRow.cellIterator();


                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();

                    /*if(cell.getCellType() == Cell.CELL_TYPE_STRING && nextRow.getRowNum() == 0 && cell.getStringCellValue().substring(0,3).equals("SSR")){
                        //String[] arr = cell.getStringCellValue().split("\\p{Upper}\\p{Space}0|1",2);

                        String[] arr  = cell.getStringCellValue().split(" ");

                        if(arr.length == 2){
                            source = "EBAY";
                        }else{
                            source = arr[1];
                        }

                        date = arr[arr.length];

                        System.out.println(arr[0] + " then " + arr[1]);
                        if(cellIterator.hasNext()){
                            cellIterator.next();
                        }else{
                            break;
                        }
                    }*/


                    while (cell.getColumnIndex() - 1 > i && i != -9999) {
                       // System.out.print(" \n ");
                        i++;
                    }

                    // System.out.print(cell.getCellType());
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            quantity.add((int)cell.getNumericCellValue());
                            // System.out.print(cell.getNumericCellValue());
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            System.out.print(cell.getBooleanCellValue());
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            System.out.print(cell.getCellFormula());
                            break;
                        case Cell.CELL_TYPE_STRING:
                            temp = cell.getStringCellValue();

                            if(cell.getColumnIndex()+1 == 1){
                                String[] arr;
                                if(temp.contains(" ")) {
                                    arr = temp.split("\\p{Space}");

                                    if (lastname.size() > 0 && temp.equals(" ")) {
                                        //Name repeat case
                                        if(savedlast.equals(lastname.get(lastname.size()-1)) && savedfirst.equals("-"))
                                        {//case when last name had no first
                                            firstname.add("");
                                            lastname.add(lastname.get(lastname.size()-1));
                                        }
                                        else if(savedlast.equals(lastname.get(lastname.size()-1))  && savedfirst.equals(firstname.get(firstname.size()-1)))
                                        {//normal repetition
                                            firstname.add(firstname.get(firstname.size()-1));
                                            lastname.add(lastname.get(lastname.size()-1));
                                        }

                                       // System.out.println("FirstNames: " + firstname.get(firstname.size()-1) + "\nLastName: " + lastname.get(lastname.size()-1));
                                    }else{
                                        //Most common case
                                        String allfirstnames = "";
                                        for (int j = 0; j < arr.length - 1; j++) {
                                            allfirstnames = allfirstnames + arr[j] + " ";
                                        }
                                        firstname.add(allfirstnames);
                                        lastname.add(arr[arr.length-1]);

                                        savedfirst = allfirstnames;
                                        savedlast = arr[arr.length-1];

                                       // System.out.println("FirstNames: " + allfirstnames + "\nLastName: " + arr[arr.length-1]);
                                    }
                                }else{
                                    //case without spaces in name
                                    savedfirst="-";
                                    savedlast=temp;
                                    firstname.add("");
                                    lastname.add(temp);
                                  //  System.out.println("FirstNames: "+ "" + "\nLastName: " + temp);
                                }

                            } else if(cell.getColumnIndex()+1 == 2){
                                if(temp.equals(" ")){
                                    country.add(new String(country.get(country.size()-1)));
                                }else{
                                    country.add(temp);
                                }
                              //  System.out.println("country: " + country.get(country.size()-1));
                            }else if(cell.getColumnIndex()+1 == 3){
                                //Check label they're disappearing
                                String BoxAndWeight="", label = "";

                                label = temp.substring(0,temp.lastIndexOf("-")-1);//custom label
                                //figuring out if 100% and getting rid of instructions or other strange things
                                if(label.contains("(")){
                                    String[] arr = label.split("\\(",2);
                                    if(arr[1].length()> 4 && arr[1].substring(0,5).equals("100%)")){
                                        label = arr[0] + "(100%)";
                                        itemCode.add(label);
                                    }else if(arr[1].contains("-")){
                                        itemCode.add(arr[0]+ arr[1].substring(arr[1].indexOf("-"),arr[1].length()));
                                    }else{
                                        itemCode.add(arr[0]);
                                    }
                                }else{
                                    itemCode.add(label);
                                }
                              //  System.out.println("Label: " + itemCode.get(itemCode.size()-1));

                                BoxAndWeight = temp.substring(temp.lastIndexOf("-")+2,temp.length());//weight and box size
                                BoxAndWeight = BoxAndWeight.replaceAll("\\p{Punct}*","");

                                // BoxAndWeight = BoxAndWeight.replace("");
                                information.add(BoxAndWeight);
                              //  System.out.println(BoxAndWeight);

                                if(BoxAndWeight.contains("\\d+X\\d+X\\d+")){
                                    System.out.print("There is a box");
                                }
                            }else{
                             //   System.out.print(cell.getStringCellValue()+"");
                            }

                            break;
                        default:
                    }




                    System.out.print(" ");
                    i = cell.getColumnIndex();

                    if(i+1>3){
                        break;
                    }
                }
                System.out.println("");
            }

            setTotal();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    protected ArrayList<TableColumn> setSalesTable(){
        ArrayList<TableColumn> columns = new ArrayList<>();

        TableColumn<SaleItems,String> NameColumn = new TableColumn<SaleItems,String>("");
        NameColumn.setCellValueFactory(new PropertyValueFactory<SaleItems,String>("name"));
        NameColumn.setStyle("-fx-font: 15.5 Ariel;");
        NameColumn.setMinWidth(200.0);
        NameColumn.setMaxWidth(300.0);
        NameColumn.setGraphic(setColumnName("NAME"));
        columns.add(NameColumn);

        TableColumn<SaleItems,String> countryColumn = new TableColumn<SaleItems,String>("");
        countryColumn.setCellValueFactory(new PropertyValueFactory<SaleItems,String>("country"));
        countryColumn.setStyle("-fx-font: 15.5 Ariel;");
        countryColumn.setMinWidth(200.0);
        countryColumn.setMaxWidth(300.0);
        countryColumn.setGraphic(setColumnName("COUNTRY"));
        columns.add(countryColumn);


        TableColumn<SaleItems,String> labelColumn = new TableColumn<SaleItems,String>("");
        labelColumn.setCellValueFactory(new PropertyValueFactory<SaleItems,String>("customLabel"));
        labelColumn.setStyle("-fx-font: 15.5 Ariel;");
        labelColumn.setMinWidth(200.0);
        labelColumn.setMaxWidth(300.0);
        labelColumn.setGraphic(setColumnName("CUSTOM LABEL"));
        columns.add(labelColumn);

        TableColumn<SaleItems,String> packingColumn = new TableColumn<SaleItems,String>("");
        packingColumn.setCellValueFactory(new PropertyValueFactory<SaleItems,String>("packing"));
        packingColumn.setStyle("-fx-font: 15.5 Ariel;");
        packingColumn.setMinWidth(200.0);
        packingColumn.setMaxWidth(300.0);
        packingColumn.setGraphic(setColumnName("PACKING"));
        columns.add(packingColumn);

        TableColumn<SaleItems,Integer> quantityColumn = new TableColumn<SaleItems,Integer>("");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<SaleItems,Integer>("quantity"));
        quantityColumn.setStyle("-fx-font: 15.5 Ariel; -fx-alignment: CENTER;");
        quantityColumn.setMinWidth(120);
        quantityColumn.setMaxWidth(120);
        quantityColumn.setGraphic(setColumnName("QUANTITY"));
        columns.add(quantityColumn);

        return columns;
    }

    protected ObservableList<SaleItems> addTableData(){
        ObservableList<SaleItems> itemList = FXCollections.observableArrayList();

        for(int i = 0; i < quantity.size(); i++){
            itemList.add(new SaleItems(firstname.get(i)+" "+lastname.get(i),
                                        country.get(i),
                                        itemCode.get(i),
                                        information.get(i),
                                        quantity.get(i)));

            System.out.println(firstname.get(i)+" "+lastname.get(i) +"\n"+
                    country.get(i)+"\n"+
                    itemCode.get(i)+"\n"+
                    information.get(i)+"\n"+
                    quantity.get(i)+" num: "+i+"\n\n");
        }

        return itemList;
    }

    private Label setColumnName(String name){
        Label columnName = new Label();
        columnName.setText(name);
        columnName.setStyle("-fx-font: 16 Ariel; -fx-font-weight: bold;");
        return columnName;
    }

    public class SaleItems {

        private String name,country,customLabel,packing;
        private int quantity;

        SaleItems(){}

        public SaleItems(String name, String country, String customLabel, String packing, int quantity) {
            this.name = name;
            this.country = country;
            this.customLabel = customLabel;
            this.packing = packing;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getCountry() {
            return country;
        }
        public void setCountry(String country) {
            this.country = country;
        }
        public String getCustomLabel() {
            return customLabel;
        }
        public void setCustomLabel(String customLabel) {
            this.customLabel = customLabel;
        }
        public String getPacking() {
            return packing;
        }
        public void setPacking(String packing) {
            this.packing = packing;
        }
        public int getQuantity() {
            return quantity;
        }
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

}
