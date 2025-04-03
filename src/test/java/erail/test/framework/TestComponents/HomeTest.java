package erail.test.framework.TestComponents;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.collect.Table.Cell;

import io.github.bonigarcia.wdm.WebDriverManager;


public class HomeTest {
	
	public static void main(String args[]) throws IOException {
			//System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver-win64\\chromedriver.exe");
			WebDriverManager.chromedriver().setup();
			WebDriver driver = new ChromeDriver();
			//Step1
			driver.get("https://erail.in/");
			driver.manage().window().maximize();
			//Step2			
			driver.findElement(By.xpath("//input[@id='txtStationFrom']"));
			//Step3
			driver.findElement(By.xpath("//input[@id='txtStationFrom']")).clear();
			//Step4
			driver.findElement(By.xpath("//input[@id='txtStationFrom']")).sendKeys("Del");
			//Step5
			 System.out.println(driver.findElement(By.xpath("//div[@id='Autocomplete_1734104098515']/div[4]/div")));
			//driver.findElement(By.xpath("//div[@id='Autocomplete_1734104233054']/div[4]/div")).click();
		    //driver.findElement(By.xpath("//div[@id='divMain']/div[2]")).click();
			//List<WebElement> listOption = driver.findElements(By.xpath("//input[@id='txtStationFrom']"));
			//listOption.get(4).click(); 
			List<WebElement> optionsList = driver.findElements(By.xpath("\"//div[contains(@id,'Autocomplete_')]/descendant::div[@title]/div[contains(@style,'hidden')]\""));
			System.out.println(optionsList);
			//Step6 : Create an excel file where put some expected station names.
			HomeTest bt = new HomeTest();
			bt.ExcelWriter();
			/*Step 7: get the list of the data from the drop-down list & write it into an excel file & compare it with
			the existing expected station name (ref: step 6) */
			bt.SelectFutureDate(driver);
	
	
	}
	public void ExcelWriter() throws IOException
	{
		Workbook workbook = new XSSFWorkbook();
        Sheet sheet = (Sheet) workbook.createSheet("Stations");
     // Define expected station names
        String[] stationNames = {"Delhi", "Delhi Cantt", "Delhi Azadpur"};

        // Write station names to Excel
        for (int i = 0; i < stationNames.length; i++) {
            Row row = ((org.apache.poi.ss.usermodel.Sheet) sheet).createRow(i);
            Cell cell = (Cell) row.createCell(0);
            ((org.apache.poi.ss.usermodel.Cell) cell).setCellValue(stationNames[i]);
        }
        FileOutputStream fileOut = new FileOutputStream("C:\\Users\\DELL\\stations.xlsx");
            try {
				workbook.write(fileOut);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            System.out.println("Excel file 'stations.xlsx' created successfully!");
        
	}
	
	public void SelectFutureDate(WebDriver driver)
	{
		// Calculate 30 days from today
        LocalDate futureDate = LocalDate.now().plusDays(30);
        int day = futureDate.getDayOfMonth();
        String monthYear = futureDate.format(DateTimeFormatter.ofPattern("MMMM yyyy")); // Format as "September 2022"

        // Click to open the date picker
        WebElement datePicker = driver.findElement(By.xpath("//input[@type='button' and @title='Select Departure date for availability']")); // Replace with actual locator
        datePicker.click();

        // Loop until the correct month and year appear
        while (true) {
            WebElement currentMonthYear = driver.findElement(By.xpath("//div[@class='ui-datepicker-title']")); // Update as per your UI
            if (currentMonthYear.getText().equals(monthYear)) {
                break;
            }
            // Click 'Next' button to move to next month
            driver.findElement(By.xpath("//a[@title='Next']")).click();
        }

        // Select the day from the calendar
        List<WebElement> days = driver.findElements(By.xpath("//table[@class='ui-datepicker-calendar']//td/a")); // Update as per your UI
        for (WebElement d : days) {
            if (d.getText().equals(String.valueOf(day))) {
                d.click();
                break;
            }
        }

        System.out.println("Successfully selected date: " + futureDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
	}
	
}