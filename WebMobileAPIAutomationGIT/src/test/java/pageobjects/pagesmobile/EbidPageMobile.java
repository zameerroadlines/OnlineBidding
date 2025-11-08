package pageobjects.pagesmobile;

import com.aventstack.extentreports.ExtentTest;
import com.bosch.automation.testbase.DriverActions;
import com.bosch.automation.testdataproperties.ColumnNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.Map;

public class EbidPageMobile extends DriverActions {

    public EbidPageMobile(WebDriver driver, ExtentTest logger, Map<String, String> testData) {
        super(driver, logger);
        this.testData=testData;
        columnNames = new ColumnNames();
    }
    By byShowSearchButton = By.xpath("//*[contains(text(),'Show Search')]");
    By bySuccessMessage = By.xpath("//*[contains(text(),'Success')]");
    By byPlantArrow = By.xpath("//span[@id='__xmlview0--ididUtclVCShipFromPlant-arrow']");
    By byFirstShipOption = By.xpath("//div[@id='__popover0-cont']/div/ul/li");
    By bySearchButton = By.xpath("//*[text()='Search']");

    By byTableRecords = By.xpath("//tbody//tr[contains(@id,'idUtclVCVendorAssignmentTable-')]");

    By byTableFirstRecord = By.xpath("//tbody//tr[contains(@id,'idUtclVCVendorAssignmentTable-')]/td[contains(@id,'idUtclVCVendorAssignmentTable-0_cell0')]");


    By bySaveBid = By.xpath("//*[contains(text(),'Save')]/ancestor::button");

    By byConfirmYesButton = By.xpath("//*[contains(text(),'Yes')]");

    By byNoRecordsFound = By.xpath("//*[contains(text(),'No data')]");




    public void selectPlantAndSearch(){
        click(byShowSearchButton,20);
        click(byPlantArrow,5);
        click(byFirstShipOption,5);
        click(bySearchButton);
        hardWait(4000);
        verifyResult(findAndReturnElementsCount(byTableFirstRecord)>0,"Record shown successfully, number of records : "+findAndReturnElementsCount(byTableRecords));
    }

    public void selectPlantAndSearchUntilFound(){
        click(byShowSearchButton, 280);
        click(byPlantArrow, 25);
        click(byFirstShipOption, 15);
        while(findAndReturnElementsCount(byTableFirstRecord)==0) {
            click(bySearchButton,120);
            hardWait(15000);
        }
        verifyResult(findAndReturnElementsCount(byTableFirstRecord)>0,"Record shown successfully, number of records : "+findAndReturnElementsCount(byTableRecords));
    }
}
