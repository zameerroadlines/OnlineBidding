package pageobjects.pagesmobile;

import com.aventstack.extentreports.ExtentTest;
import com.bosch.automation.testbase.DriverActions;
import com.bosch.automation.testdataproperties.ColumnNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class SelectionPage extends DriverActions {
    public SelectionPage(WebDriver driver, ExtentTest logger, Map<String, String> testData) {
        super(driver, logger);
        this.testData=testData;
        columnNames = new ColumnNames();
    }

    //region xpath's

    By byAnswerButton = By.xpath("//*[@text='Answer']");

    By byNameTextBox = By.xpath("//*[contains(@resource-id,'editText1')]");
    By byRightMessage = By.xpath("//*[contains(@text,'You are right!')]");

    By byWrongMessage = By.xpath("//*[contains(@text,'Wrong Answer!')]");
    By byNewTicketTile = By.xpath("//*[contains(@resource-id,'landingDetail--aboPanel-0')]");

    By byDailyInterlockSelection = By.xpath("//input[contains(@aria-activedescendant,'VehicleChecks--vcInterlockChecksId')]");
    By byVehicleChecksTile = By.xpath("//*[contains(@resource-id,'landingDetail--vehiclePanel-1')]");
    By byCheckboxSelected = By.xpath("//li[@aria-selected='true']");

    //endregion

    //region reusable methods

    public void selectCorrectIncorrectOptionVerifyResult(boolean correctOption)
    {
        clickElementWithContainsTextNative(testData.get(columnNames.OptionText),"TestingOptions",5);
        enterTextAfterClick(byNameTextBox,testData.get(columnNames.Name),"NameTextBox",5,false);
        navigateBack();
        click(byAnswerButton,"AnswerButton",5);

        By byExpectedMessage =  correctOption?byRightMessage:byWrongMessage;
        String messageToPrint= correctOption?"Success message You are right! is shown"
                :"Failure message Wrong Answer! is shown";
        verifyResult(verifyElementShown(byExpectedMessage,5), messageToPrint);
        if(correctOption)verifyResult(verifyElementWithTextShown(
                "Congratulations "+testData.get(columnNames.Name)+"!"),"Name is verified");

    }
}
