package inventory;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by SSR on 7/8/2016.
 */
public class Imports {
    String firstName,lastName,productCode;
    int quantity;

    public Imports(){

    }

    public void create_file(){

            try {
                Workbook outputBook = new HSSFWorkbook();
                Sheet sheet1 = outputBook.createSheet("firstsheet");
                Sheet sheet2 = outputBook.createSheet("secondsheet");


                FileOutputStream fileout = new FileOutputStream("test.xls");
                outputBook.write(fileout);
                fileout.close();
            }catch (IOException e){
                e.printStackTrace();
            }
    }

    public static void readSalesFile(String location) {

            //07.04.2016A
            //06.22.2016A
            try {
                File file = new File("src/"+location+".xls");
                FileInputStream inputStream = new FileInputStream(file);

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