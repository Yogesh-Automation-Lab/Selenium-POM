package erail.test.framework.pageobjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ErailPage {

    WebDriver driver;

    @FindBy(xpath = "//input[contains(@id,'txtStationFrom')]")
    WebElement stationFromEle;

    @FindBy(xpath = "//div[contains(@id,'Autocomplete_')]/descendant::div[@title]/div[contains(@style,'hidden')]")
    List<WebElement> optionsList;

    public ErailPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterStationFrom(String station) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOf(stationFromEle));
            stationFromEle.clear();
            stationFromEle.sendKeys(station);
        } catch (Exception e) {
            System.out.println("Element is not visible: " + e.getMessage());
        }
    }

    public List<WebElement> getOptionsList() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(optionsList));
        } catch (Exception e) {
            System.out.println("Options list is not visible: " + e.getMessage());
        }
        return optionsList;
    }

	
	
	
}
