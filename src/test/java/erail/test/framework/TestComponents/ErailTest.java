package erail.test.framework.TestComponents;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import erail.test.framework.pageobjects.ErailPage;
import erail.test.framework.resources.ExcelUtils;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ErailTest {
	
	
	WebDriver driver;
    ErailPage erailPage;
    ExcelUtils excelUtils;
    String excelFilePath = "src/test/resources/TestData.xlsx";
    String sheetName = "Sheet1";

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://erail.in/");
        driver.manage().window().maximize();
        erailPage = new ErailPage(driver);
        excelUtils = new ExcelUtils(excelFilePath, sheetName);
    }

    @Test
    public void testStationNames() throws InterruptedException, IOException {
        erailPage.enterStationFrom("DEL");

        List<WebElement> optionsList = erailPage.getOptionsList();

        System.out.println("Station name at 4th position: " + optionsList.get(3).getText());

        excelUtils.writeOptionsToExcel(optionsList);
        excelUtils.compareValuesInExcel();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        System.out.println("WebDriver closed.");
    }


}
