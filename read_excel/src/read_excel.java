import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;

/**
 * Created by SSR on 7/6/2016.
 */
public class read_excel {

    public static void main(String[] args){
        read_file("newInventory");
        // create_file();
    }

    public static void create_file(){

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

    public static void read_file(String location) {

        //07.04.2016A
        //06.22.2016A
        try {
            File file = new File("src/"+location+".xls");
            FileInputStream inputStream = new FileInputStream(file);

            Workbook workbook = new HSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

           // int i = -9999;
            String temp="";

            while (rowIterator.hasNext()) {
                Row nextRow = rowIterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();

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
                            System.out.print(cell.getBooleanCellValue());
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            System.out.print(cell.getCellFormula());
                            break;
                        case Cell.CELL_TYPE_STRING:
                            str = cell.getStringCellValue();
                            temp = cell.getStringCellValue();

                            System.out.print(temp);

                            if(cell.getColumnIndex()+1 == 1){
                                System.out.println("custom label: " + str.replaceAll("\\p{Space}",""));
                            }else if(cell.getColumnIndex()+1 == 2){
                                System.out.println("quantity: " + i);
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



}
