import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by SSR on 7/6/2016.
 */
public class read_excel {

    public static void main(String[] args) throws IOException{
        File file = new File("src/06.22.2016A.xls");
        FileInputStream inputStream = new FileInputStream(file);

        Workbook workbook = new HSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        int i = -9999;

        while(rowIterator.hasNext()){
            Row nextRow = rowIterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();


            while (cellIterator.hasNext()){
                Cell cell = cellIterator.next();


                while(cell.getColumnIndex()-1 > i && i!=-9999){
                    System.out.print(" \n ");
                    i++;
                }

               // System.out.print(cell.getCellType());
                switch (cell.getCellType()){
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print(cell.getNumericCellValue());
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        System.out.print(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        System.out.print(cell.getCellFormula());
                        break;

                    case Cell.CELL_TYPE_STRING:
                        System.out.print(cell.getRichStringCellValue());
                        break;
                    default:
                }



                System.out.print(" ");
                i = cell.getColumnIndex();
            }
            System.out.println("");
        }

    }

}
