package inventory;

import javafx.scene.control.ListCell;
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
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by SSR on 8/4/2016.
 */
public class NewInventory extends ListCell<NewInventory> {

    private String supplier,date,invoice;
    private boolean verified;
    private ArrayList<String> customLabel;
    private ArrayList<Integer> quantity;
    private File file;

    NewInventory(){
        this("","","",false);
    }

    public NewInventory(File file){
        customLabel = new ArrayList<String>();
        quantity = new ArrayList<Integer>();
        setFile(file);
        importToReceiving(file);
    }

    public NewInventory(String supplier, String date, String invoice, boolean verified) {
        this.supplier = supplier;
        this.date = date;
        this.invoice = invoice;
        this.verified = verified;
        customLabel = new ArrayList<String>();
        quantity = new ArrayList<Integer>();
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

    public void importToReceiving(File file){

        try {
            FileInputStream inputStream = new FileInputStream(file);

            System.out.println("READING FILE: "+file.getName().toString());

            Workbook workbook = new HSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            //int i = -9999;
            String temp="";

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
                        setInvoice(arr[3]);
                    }

                    System.out.println(getSupplier() + " then " + getDate() + " part " + getInvoice());
                }else{
                    //Not a new Inventory File
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
                            //System.out.print(cell.getNumericCellValue());
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                           // System.out.print(cell.getBooleanCellValue());
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                           // System.out.print(cell.getCellFormula());
                            break;
                        case Cell.CELL_TYPE_STRING:
                            str = cell.getStringCellValue();
                            temp = cell.getStringCellValue();

                           // System.out.print(temp);

                            if(cell.getColumnIndex()+1 == 1)
                            {
                                System.out.print("custom label: " + str.replaceAll("\\p{Space}",""));
                                customLabel.add(str);
                            }
                            else if(cell.getColumnIndex()+1 == 2)
                            {
                                System.out.print("quantity: " + i);
                                quantity.add(0);
                            }

                            break;
                        default:
                    }

                    System.out.print(" ");
                }
                System.out.println("");
            }

        }catch(IOException e){
            e.printStackTrace();
        }
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
