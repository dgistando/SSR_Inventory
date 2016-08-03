package inventory;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
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
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by SSR on 7/28/2016.
 */
public class Sales extends ListCell<Sales>{
    private String date,source;
    //plaanignng to put the Strings in order of the columns in table ie. first column in first name.
    // first name, last name, country, custom name, info.
    private ArrayList<String> information;
    private ArrayList<Integer> quantity;
    private boolean verified;
    private File file;

    public Sales(){
        this("","",0,false);
    }

    public Sales(File file){
        importToSales(file);
    }

    public Sales(String _date,String _source, int _quantity,boolean _verified){
        date = _date;
        source = _source;
        information = new ArrayList<String>();
        quantity = new ArrayList<Integer>();
        verified = _verified;
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

    public ArrayList<Integer> getAllQuantity() {
        return quantity;
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

    @Override
    protected void updateItem(Sales item, boolean empty) {
        super.updateItem(item, empty);
        Rectangle rect = new Rectangle(100, 20);
        if(item != null){
            VBox vBox = new VBox(new Text(item.getDate()), new Text("grjijr"));
            HBox hBox = new HBox();
            ImageView l = new ImageView();

            String imageName;

            if(item.isVerified()){
                imageName="drawable/verified_image1.png";
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

    private void importToSales(File file){
        try {
            FileInputStream inputStream = new FileInputStream(file);

            System.out.print("READING FILE:"+file.getName().toString());

            Workbook workbook = new HSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            int i = -9999;
            String temp="";

            while (rowIterator.hasNext()) {
                Row nextRow = rowIterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();



                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();



                    while (cell.getColumnIndex() - 1 > i && i != -9999) {
                        System.out.print(" \n ");
                        i++;
                    }

                    // System.out.print(cell.getCellType());
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC:
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


                            if(cell.getColumnIndex()+1 == 3){
                                String BoxAndWeight="";
                                System.out.println("\n"+temp.substring(0,temp.lastIndexOf("-")));//custom label
                                BoxAndWeight = temp.substring(temp.lastIndexOf("-")+2,temp.length());//weight and box size

                                BoxAndWeight = BoxAndWeight.replaceAll("\\p{Punct}*","");
                                // BoxAndWeight = BoxAndWeight.replace("");
                                System.out.println(BoxAndWeight);

                                if(BoxAndWeight.contains("\\d+X\\d+X\\d+")){
                                    System.out.print("There is a box");
                                }
                            }else{
                                System.out.print(cell.getStringCellValue());
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

        }catch(IOException e){
            e.printStackTrace();
        }

    }
}
