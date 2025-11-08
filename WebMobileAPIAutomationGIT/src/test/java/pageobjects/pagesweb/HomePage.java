package pageobjects.pagesweb;

import com.aventstack.extentreports.ExtentTest;
import com.bosch.automation.testbase.DriverActions;
import com.bosch.automation.testdataproperties.ColumnNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class HomePage extends DriverActions {

    public HomePage(WebDriver driver, ExtentTest logger, Map<String, String> testData) {
        super(driver, logger);
        this.testData=testData;
        columnNames = new ColumnNames();
    }
    By byEBiddingTile = By.xpath("//span[@id=\"__tile19-title-inner\"]");

    public void clickBiddingTile(){
        click(byEBiddingTile," Bidding Option",2);
        hardWait(1000);
        switchWindowsTab(false);
    }
}
