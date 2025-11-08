
package testscripts.webtestcases;

import com.bosch.automation.testbase.BaseClass;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.pagesweb.EBidPage;
import pageobjects.pagesweb.HomePage;
import pageobjects.pagesweb.LoginPage;

public class WebScripts extends BaseClass {

    @Test(description = "eye2serve - eBidding")
    public void LoginAndSubmitBid1(){
        LoginPage loginPage = new LoginPage(getDriver(),getLogger(),testData);
        HomePage homePage = new HomePage(getDriver(),getLogger(),testData);
        EBidPage eBidPage = new EBidPage(getDriver(),getLogger(),testData);
        loginPage.login();
        homePage.clickBiddingTile();
        eBidPage.selectPlantAndSearchUntilFound();
        Assert.assertTrue(eBidPage.enterBidSetEnhanced(
                testData.get(columnNames.DestinationBulkRange1),
                testData.get(columnNames.DestinationBulkRange2),
                testData.get(columnNames.DestinationBagRange1),
                testData.get(columnNames.DestinationBagRange2),
                testData.get(columnNames.DestinationBagRange3),
                testData.get(columnNames.BulkRange1Min),
                testData.get(columnNames.BulkRange1Max),
                testData.get(columnNames.BulkRange2Min),
                testData.get(columnNames.BulkRange2Max),
                testData.get(columnNames.BagRange1Min),
                testData.get(columnNames.BagRange1Max),
                testData.get(columnNames.BagRange2Min),
                testData.get(columnNames.BagRange2Max),
                testData.get(columnNames.BagRange3Min),
                testData.get(columnNames.BagRange3Max)),"Expected destinations/Quantity were not shown in records");
    }









    @Test(description = "eye2serve - eBidding")
    public void LoginAndSubmitBid2(){
        LoginPage loginPage = new LoginPage(getDriver(),getLogger(),testData);
        HomePage homePage = new HomePage(getDriver(),getLogger(),testData);
        EBidPage eBidPage = new EBidPage(getDriver(),getLogger(),testData);
        loginPage.login();
        homePage.clickBiddingTile();
        eBidPage.selectPlantAndSearchUntilFound();
        Assert.assertTrue(eBidPage.enterBidSetEnhanced(
                testData.get(columnNames.DestinationBulkRange1),
                testData.get(columnNames.DestinationBulkRange2),
                testData.get(columnNames.DestinationBagRange1),
                testData.get(columnNames.DestinationBagRange2),
                testData.get(columnNames.DestinationBagRange3),
                testData.get(columnNames.BulkRange1Min),
                testData.get(columnNames.BulkRange1Max),
                testData.get(columnNames.BulkRange2Min),
                testData.get(columnNames.BulkRange2Max),
                testData.get(columnNames.BagRange1Min),
                testData.get(columnNames.BagRange1Max),
                testData.get(columnNames.BagRange2Min),
                testData.get(columnNames.BagRange2Max),
                testData.get(columnNames.BagRange3Min),
                testData.get(columnNames.BagRange3Max)));
    }
    @Test(description = "eye2serve - eBidding")
    public void LoginAndSubmitBid3(){
        LoginPage loginPage = new LoginPage(getDriver(),getLogger(),testData);
        HomePage homePage = new HomePage(getDriver(),getLogger(),testData);
        EBidPage eBidPage = new EBidPage(getDriver(),getLogger(),testData);
        loginPage.login();
        homePage.clickBiddingTile();
        eBidPage.selectPlantAndSearchUntilFound();
        Assert.assertTrue(eBidPage.enterBidSetEnhanced(
                testData.get(columnNames.DestinationBulkRange1),
                testData.get(columnNames.DestinationBulkRange2),
                testData.get(columnNames.DestinationBagRange1),
                testData.get(columnNames.DestinationBagRange2),
                testData.get(columnNames.DestinationBagRange3),
                testData.get(columnNames.BulkRange1Min),
                testData.get(columnNames.BulkRange1Max),
                testData.get(columnNames.BulkRange2Min),
                testData.get(columnNames.BulkRange2Max),
                testData.get(columnNames.BagRange1Min),
                testData.get(columnNames.BagRange1Max),
                testData.get(columnNames.BagRange2Min),
                testData.get(columnNames.BagRange2Max),
                testData.get(columnNames.BagRange3Min),
                testData.get(columnNames.BagRange3Max)));
    }
    @Test(description = "eye2serve - eBidding")
    public void LoginAndSubmitBid4(){
        LoginPage loginPage = new LoginPage(getDriver(),getLogger(),testData);
        HomePage homePage = new HomePage(getDriver(),getLogger(),testData);
        EBidPage eBidPage = new EBidPage(getDriver(),getLogger(),testData);
        loginPage.login();
        homePage.clickBiddingTile();
        eBidPage.selectPlantAndSearchUntilFound();
        Assert.assertTrue(eBidPage.enterBidSetEnhanced(
                testData.get(columnNames.DestinationBulkRange1),
                testData.get(columnNames.DestinationBulkRange2),
                testData.get(columnNames.DestinationBagRange1),
                testData.get(columnNames.DestinationBagRange2),
                testData.get(columnNames.DestinationBagRange3),
                testData.get(columnNames.BulkRange1Min),
                testData.get(columnNames.BulkRange1Max),
                testData.get(columnNames.BulkRange2Min),
                testData.get(columnNames.BulkRange2Max),
                testData.get(columnNames.BagRange1Min),
                testData.get(columnNames.BagRange1Max),
                testData.get(columnNames.BagRange2Min),
                testData.get(columnNames.BagRange2Max),
                testData.get(columnNames.BagRange3Min),
                testData.get(columnNames.BagRange3Max)));
    }
    @Test(description = "eye2serve - eBidding")
    public void LoginAndSubmitBidSet(){
        LoginPage loginPage = new LoginPage(getDriver(),getLogger(),testData);
        HomePage homePage = new HomePage(getDriver(),getLogger(),testData);
        EBidPage eBidPage = new EBidPage(getDriver(),getLogger(),testData);
        loginPage.login();
        homePage.clickBiddingTile();
        eBidPage.selectPlantAndSearchUntilFoundExisting();
        Assert.assertTrue(eBidPage.enterBidSetEnhanced(
                testData.get(columnNames.DestinationBulkRange1),
                testData.get(columnNames.DestinationBulkRange2),
                testData.get(columnNames.DestinationBagRange1),
                testData.get(columnNames.DestinationBagRange2),
                testData.get(columnNames.DestinationBagRange3),
                testData.get(columnNames.BulkRange1Min),
                testData.get(columnNames.BulkRange1Max),
                testData.get(columnNames.BulkRange2Min),
                testData.get(columnNames.BulkRange2Max),
                testData.get(columnNames.BagRange1Min),
                testData.get(columnNames.BagRange1Max),
                testData.get(columnNames.BagRange2Min),
                testData.get(columnNames.BagRange2Max),
                testData.get(columnNames.BagRange3Min),
                testData.get(columnNames.BagRange3Max)));
    }
}
