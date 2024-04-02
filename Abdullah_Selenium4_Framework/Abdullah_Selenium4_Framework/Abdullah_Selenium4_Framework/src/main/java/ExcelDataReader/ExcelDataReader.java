package ExcelDataReader;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelDataReader {
    public Object[][] readExcelData(String testcase) throws IOException {
        // Start by creating a workbook and pass it fis with excel path
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\Userdata.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        Object[][] data = new Object[0][];
        DataFormatter formatter = new DataFormatter();

        //Get sheetNumbers
        int sheetNumbers = workbook.getNumberOfSheets();
        for(int i = 0; i< sheetNumbers; i++){
            if (workbook.getSheetName(i).equalsIgnoreCase(testcase)){
                XSSFSheet sheet = workbook.getSheetAt(i);
                //Get Rows and Columns Count
                int rowCount = sheet.getPhysicalNumberOfRows();
                XSSFRow row = sheet.getRow(0);
                int columnLength = row.getLastCellNum();
                data = new Object[rowCount-1][columnLength];

                for(int j = 0; j< rowCount - 1; j++){
                    row = sheet.getRow(j + 1);
                    for(int k = 0; k<columnLength; k++){
                        //Insert cell values into 2D array to be returned
                        XSSFCell cell = row.getCell(k);
                        data[j][k] = formatter.formatCellValue(cell);
                    }
                }
            }
        }
        return data;
        //Comment
    }
}
