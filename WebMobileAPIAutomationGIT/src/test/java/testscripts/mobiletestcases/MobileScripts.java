package testscripts.mobiletestcases;

import com.bosch.automation.testbase.BaseClass;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageobjects.pagesmobile.SelectionPage;
import pageobjects.pagesweb.EBidPage;
import pageobjects.pagesweb.HomePage;
import pageobjects.pagesweb.LoginPage;

@Parameters({"deviceName","platformVersion","portNumber"})
public class MobileScripts extends BaseClass {


    @Test(description = "bitbar - ->Select correct testing option and verify message")
    public void SelectCorrectTestingOption(){
        SelectionPage selectionPage = new SelectionPage(getDriver(),getLogger(),testData);
        selectionPage.selectCorrectIncorrectOptionVerifyResult(true);
    }

    @Test(description = "bitbar - ->Select incorrect testing option and verify message not shown")
    public void SelectIncorrectTestingOption(){
        SelectionPage selectionPage = new SelectionPage(getDriver(),getLogger(),testData);
        selectionPage.selectCorrectIncorrectOptionVerifyResult(false);
    }
}
