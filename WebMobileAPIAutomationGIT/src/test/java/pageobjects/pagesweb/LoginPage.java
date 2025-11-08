package pageobjects.pagesweb;

import com.aventstack.extentreports.ExtentTest;
import com.bosch.automation.testbase.DriverActions;
import com.bosch.automation.testdataproperties.ColumnNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class LoginPage extends DriverActions {
    public LoginPage(WebDriver driver, ExtentTest logger, Map<String, String> testData) {
        super(driver, logger);
        this.testData=testData;
        columnNames = new ColumnNames();
    }

    //region xpath's

    By byUserEmail = By.xpath("//input[@id='USERNAME_FIELD-inner']");
    By byPassword = By.xpath("//input[@id='PASSWORD_FIELD-inner']");
    By byLogonButton = By.xpath("//*[contains(text(),'Log On')]");

    By byEBiddingTile = By.xpath("//*[contains(text(),'E-Bidding')]");

    //endregion

    public void login(){
        enterTextAfterClick(byUserEmail,testData.get(columnNames.UserName),"UserEmail",25,false);
        enterTextAfterClick(byPassword,testData.get(columnNames.Password),"Password",5,false);
        click(byLogonButton,"SubmitButton",5);
        verifyResult(findElementPresenceReturnBool(byEBiddingTile,60),"Logged inn, Bidding Tile is visible");
    }
}
