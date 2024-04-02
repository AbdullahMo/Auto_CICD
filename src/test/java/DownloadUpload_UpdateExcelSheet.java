import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;

public class DownloadUpload_UpdateExcelSheet {
    public static void main(String[] args) throws IOException, InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://rahulshettyacademy.com/upload-download-test/index.html");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        String filePath = "D:\\Users\\amohamed37\\Downloads\\download.xlsx";

        String desiredFruit = "Kivi";
        String desiredColumn = "price";
        String desiredPrice = "600";

        //Download
        driver.findElement(By.cssSelector("#downloadButton")).click();
        Thread.sleep(4000);
        //Edit Excel
        int colNumber = getColumnNumber(filePath, desiredColumn);
        System.out.println("Column Number => " + colNumber);
        int rowNumber = getRowNumber(filePath, desiredFruit);
        System.out.println("Row Number => " + rowNumber);
        Assert.assertTrue(updateCell(filePath, rowNumber, colNumber, desiredPrice));

        //Upload

        driver.findElement(By.cssSelector("[type='file']")).sendKeys(filePath);

        //Wait for success messge to appear and disappear before verifying web table
        By toastLocator = By.cssSelector(".Toastify__toast-body div:nth-child(2)");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(toastLocator));
        String text = driver.findElement(toastLocator).getText();
        Assert.assertEquals("Updated Excel Data Successfully.", text);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(toastLocator));

        // Verify Apple Price
        //System.out.println("Apple price: " + driver.findElement(By.cssSelector("#row-1 #cell-4-undefined div")).getText());

        //Smart Xpath
        System.out.println(driver.findElement(By.xpath("//div[text()='" + desiredFruit + "']/parent::div/parent::div/" +
                "div[@id='cell-" + (colNumber + 1) + "-undefined']")).getText());

        driver.quit();


    }

    private static int getColumnNumber(String filePath, String colLabel) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        //DataFormatter formatter = new DataFormatter();

        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();// sheet is collection of rows
        Row firstrow = rows.next();
        Iterator<Cell> ce = firstrow.cellIterator();//row is collection of cells
        int coloumn = 0;
        while (ce.hasNext()) {
            Cell value = ce.next();

            if (value.getStringCellValue().equalsIgnoreCase(colLabel)) {
                coloumn = value.getColumnIndex();
            }
        }
        return coloumn;
    }

    private static int getRowNumber(String filePath, String rowLabel) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rows = sheet.iterator();// sheet is collection of rows
        int rowIndex = -1;

        while (rows.hasNext()) {
            Row currentRow = rows.next();
            Iterator<Cell> ce = currentRow.cellIterator();//row is collection of cells
            while (ce.hasNext()) {
                Cell value = ce.next();

                if (value.getCellType() == CellType.STRING && value.getStringCellValue().equalsIgnoreCase(rowLabel)) {
                    rowIndex = value.getRowIndex();
                }
            }
        }
        return rowIndex;
    }

    private static boolean updateCell(String filePath, int rowNumber, int colNumber, String number) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Row rowField = sheet.getRow(rowNumber);
        Cell cellField = rowField.getCell(colNumber);
        cellField.setCellValue(number);

        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        workbook.close();
        fis.close();
        return true;
    }
}
