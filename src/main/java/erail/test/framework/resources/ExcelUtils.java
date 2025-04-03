package erail.test.framework.resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;

public class ExcelUtils {

	 private final String excelFilePath;
	 private final String sheetName;

	    public ExcelUtils(String excelFilePath, String sheetName) {
	        this.excelFilePath = excelFilePath;
	        this.sheetName = sheetName;
	    }

	    public void writeOptionsToExcel(List<WebElement> optionsList) throws IOException {
	        try (FileInputStream fileIn = new FileInputStream(excelFilePath);
	             Workbook workbook = new XSSFWorkbook(fileIn)) {

	            Sheet sheet = workbook.getSheet(sheetName);
	        	if (sheet == null) {
	                System.out.println("Sheet with name " + sheetName + " does not exist.");
	                return;
	            }

	            int rowNum = 1;
	            for (WebElement option : optionsList) {
	                Row row = sheet.getRow(rowNum);
	                if (row == null) {
	                    row = sheet.createRow(rowNum);
	                }
	                Cell cell = row.createCell(1);
	                cell.setCellValue(option.getText());
	                System.out.println("Written value: " + option.getText() + " to row: " + rowNum);
	                rowNum++;
	            }

	            try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
	                workbook.write(fileOut);
	                System.out.println("Excel file has been updated successfully.");
	            }
	        }
	    }

	    public void compareValuesInExcel() throws IOException {
	        try (FileInputStream fileIn = new FileInputStream(excelFilePath);
	                Workbook workbook = new XSSFWorkbook(fileIn)) {

	               Sheet sheet = workbook.getSheet(sheetName);
	               if (sheet == null) {
	                   System.out.println("Sheet with name " + sheetName + " does not exist.");
	                   return;
	               }

	               int rowNum = sheet.getLastRowNum();
	               for (int i = 1; i <= rowNum; i++) {
	                   Row row = sheet.getRow(i);
	                   if (row == null) {
	                       Cell cellA = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
	                       Cell cellB = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
	                       Cell cellC = row.createCell(2);

	                       if (cellA != null && cellB != null) {
	                           String valueA = cellA.getStringCellValue().trim();
	                           String valueB = cellB.getStringCellValue().trim();
	                           if (valueA.isEmpty() && valueB.isEmpty()) {
	                            System.out.println("End of data at row " + i);
	                               break;
	                           }
	                           cellC.setCellValue(valueA.equals(valueB) ? "true" : "false");
	                           System.out.println("Comparison for row " + i + ": " + valueA + " vs " + valueB + " = " + cellC.getStringCellValue());
	                       } else {
	                           cellC.setCellValue("false");
	                           System.out.println("Comparison for row " + i + ": One or both cells are null.");
	                       }
	                   }
	               }

	               try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
	                   workbook.write(fileOut);
	                   System.out.println("Excel file has been updated successfully.");
	               }
	           }
	    }

	
	
}
