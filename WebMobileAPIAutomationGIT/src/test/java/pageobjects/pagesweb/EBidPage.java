package pageobjects.pagesweb;

import com.aventstack.extentreports.ExtentTest;
import com.bosch.automation.APIHelper.RestAPIHelper;
import com.bosch.automation.testbase.DriverActions;
import com.bosch.automation.testdataproperties.ColumnNames;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.*;

public class EBidPage extends DriverActions {

    public EBidPage(WebDriver driver, ExtentTest logger, Map<String, String> testData) {
        super(driver, logger);
        this.testData = testData;
        columnNames = new ColumnNames();
    }
    List<Map<String, Object>> matchedRows = new ArrayList<>();
    List<Map<String, Object>> finalSelection = new ArrayList<>();
    By byShowSearchButton = By.xpath("//*[contains(text(),'Show Search')]");
    By bySuccessMessage = By.xpath("//*[contains(text(),'Success')]");

    By byTwoSecondsLeft = By.xpath("//*[text()='Starts in 0:0:1']");

    By byPlantArrow = By.xpath("//span[@id='__xmlview0--ididUtclVCShipFromPlant-arrow']");
    By byFirstShipOption = By.xpath("//div[@id='__popover0-cont']/div/ul/li");
    By byinputplant = By.xpath("//input[@id=\"__xmlview0--ididUtclVCShipFromPlant-inner\"]");
    By bySearchButton = By.xpath("//*[text()='Search']");

    By byTableRecords = By.xpath("//tbody//tr[contains(@id,'idUtclVCVendorAssignmentTable-')]");

    By byTableFirstRecord = By.xpath("//td[text()='No data']");

    By bySaveBid = By.xpath("//*[contains(text(),'Save')]/ancestor::button");

    By byConfirmYesButton = By.xpath("//*[contains(text(),'Yes')]");

    By byNoRecordsFound = By.xpath("//*[contains(text(),'No data')]");


    public void selectPlantAndSearch() {
        click(byShowSearchButton, 20);
        click(byPlantArrow, 5);
        click(byFirstShipOption, 5);
        click(bySearchButton);
        hardWait(4000);
        verifyResult(findAndReturnElementsCount(byTableFirstRecord) > 0, "Record shown successfully, number of records : " + findAndReturnElementsCount(byTableRecords));
    }

    public void selectPlantAndSearchUntilFound() {
        hardWait(4000);
        click(byShowSearchButton,"Search button", 1200000);
        hardWait(2000);
        click(byinputplant,"Plant arrow", 25);
        hardWait(2000);
        enterTextAfterClick(byinputplant,"GINIGERA GRINDING UNIT","plant",25,false);
       // click(byFirstShipOption,"First option", 15);

        hardWait(2000);
        while (findAndReturnElementsCount(byTableFirstRecord) > 0) {
            click(bySearchButton,"Search button", 120);
            log("Waiting for the Data...No Destinations available for Bidding :");

            hardWait(Integer.parseInt(testData.get(columnNames.PlantSearchWaitMilli)));
        }
       // verifyResult(findAndReturnElementsCount(byTableFirstRecord) > 0, "Record shown successfully, number of records : " + findAndReturnElementsCount(byTableRecords));
    }

    public void selectPlantAndSearchUntilFoundExisting() {
        hardWait(1000);
        click(byShowSearchButton, 380);
        hardWait(1000);
        click(byPlantArrow, 25);
        hardWait(1000);
        click(byFirstShipOption, 15);
        hardWait(1000);
        while (findAndReturnElementsCount(byTableFirstRecord) == 0) {
            click(bySearchButton, 120);
            hardWait(25000);
        }

        verifyResult(findAndReturnElementsCount(byTableFirstRecord) > 0, "Record shown successfully, number of records : " + findAndReturnElementsCount(byTableRecords));
    }


    public boolean enterBidSetEnhanced(String destinationBulkSet1, String destinationBulkSet2,String destinationBagSet1, String destinationBagSet2,String destinationBagSet3, String quantityBulkMin1,String quantityBulkMax1, String quantityBulkMin2,String quantityBulkMax2, String quantityBagMin1,String quantityBagMax1, String quantityBagMin2,String quantityBagMax2, String quantityBagMin3,String quantityBagMax3)
    {
        int  countBag1 = 0, countBulk1 = 0, countBag2 = 0, countBulk2 = 0, countBag3= 0;


        var limitBulk1 = Integer.parseInt(testData.get(columnNames.LimitBulk1));
        var limitBulk2 = Integer.parseInt(testData.get(columnNames.LimitBulk2));
        var limitBag1 = Integer.parseInt(testData.get(columnNames.LimitBag1));
        var limitBag2 = Integer.parseInt(testData.get(columnNames.LimitBag2));
        var limitBag3 = Integer.parseInt(testData.get(columnNames.LimitBag3));

        String[] destinationBulkArray1=null,destinationBulkArray2=null,destinationBagArray1=null,destinationBagArray2=null,destinationBagArray3=null;

        if(testData.get(columnNames.LimitBulk1)!=null|| !testData.get(columnNames.LimitBulk1).isEmpty()) {
             destinationBulkArray1 = destinationBulkSet1.split(",");
        }
        if(testData.get(columnNames.LimitBulk2)!=null|| !testData.get(columnNames.LimitBulk2).isEmpty()) {
             destinationBulkArray2 = destinationBulkSet2.split(",");
        }
        if(testData.get(columnNames.LimitBag1)!=null|| !testData.get(columnNames.LimitBag1).isEmpty()) {
            destinationBagArray1 = destinationBagSet1.split(",");
        }
        if(testData.get(columnNames.LimitBag2)!=null|| !testData.get(columnNames.LimitBag2).isEmpty()) {
             destinationBagArray2 = destinationBagSet2.split(",");
        }
        if(testData.get(columnNames.LimitBag3)!=null|| !testData.get(columnNames.LimitBag3).isEmpty()) {
             destinationBagArray3 = destinationBagSet3.split(",");
        }


        try{
       //     By bySearchTime = By.xpath("//*[text()='Starts in 0:0:" + testData.get(columnNames.SearchTimeBeforeRows) + "']");

// Wait up to 600 seconds (or as needed) for the element to appear
//            if (findElementPresenceReturnBool(bySearchTime, 600)) {
//                log("Auction start time matched: " + testData.get(columnNames.SearchTimeBeforeRows) + " seconds remaining.");
//                click(bySearchButton,"Search button",4);
//                log("Search button clicked after our timings matched : ");
//
//            } else {
//                log("Auction start time element not found within wait time. Skipping data collection.");
//                return false; // Or handle as needed (exit early, throw exception, etc.)
//            }
            WebElement table = driver.findElement(By.id("__xmlview0--idUtclVCVendorAssignmentTable"));

        List<WebElement> rows = table.findElements(By.xpath(".//tr[contains(@id, '__item7-')]"));

            // Process rows one by one
            for (int i = 0; i < rows.size(); i++) {
                Map<String, Object> rowDetails = new HashMap<>();
                WebElement row = rows.get(i);
                // Extract data from the row
                List<WebElement> cells = row.findElements(By.tagName("td"));
                String destination = cells.get(5).getText();  // Assume destination is in column 0
                int quantity = (int) Double.parseDouble(cells.get(11).getText());  // Assume quantity is in column 1
                String temp = cells.get(12).getText();  // Assume type ("BULK"/"BAG") is in column 2
                String freight = cells.get(13).getText();
                String[] arr = temp.split("-");
                String type = arr[1];
              //  System.out.println(arr[1]);
                log("Destination : " + destination + "quantity :"+quantity+"");
                if (type.contains("BULK")) {
                    // Bulk 1 conditions

                    if (quantity >= Integer.parseInt(quantityBulkMin1) && quantity <= Integer.parseInt(quantityBulkMax1) && limitBulk1 > 0) {
                        log("Limit bulk1: " + limitBulk1);
                            if (isDestinationInArray(destination, destinationBulkArray1)) {
                                log("Match found for Bulk 1: " + destination);
                                countBulk1++;
                                float actualAmount = convertStringToInt(freight);
                                String bid = calculateBidAmount(actualAmount);
                                rowDetails.put("rowIndex", i); // Store row index
                                rowDetails.put("destination", destination);
                                rowDetails.put("bid", bid);
                                rowDetails.put("matchedBulk", "bulk1");
                                matchedRows.add(rowDetails);
                                continue;
                            }

                  else{
                      continue;
                        }

                    }

                    // Bulk 2 conditions
                    if (quantity >= Integer.parseInt(quantityBulkMin2) && quantity <= Integer.parseInt(quantityBulkMax2) && limitBulk2 > 0) {
                        log("Limit bulk2: " + limitBulk2);
                            if (isDestinationInArray(destination, destinationBulkArray2)) {
                                log("Match found for Bulk 2: " + destination);
                               countBulk2++;
                                float actualAmount = convertStringToInt(freight);
                                String bid = calculateBidAmount(actualAmount);
                                rowDetails.put("rowIndex", i); // Store row index
                                rowDetails.put("destination", destination);
                                rowDetails.put("bid", bid);
                                rowDetails.put("matchedBulk", "bulk2");
                                matchedRows.add(rowDetails);
                                continue;
                            }// Exit the loop entirely

                        else{
                            continue;
                        }

                    }

                    System.out.println("No match found for Bulk: " + destination);
                } else if (type.contains("BAG")) {
                    // Bag 1 conditions
                    if (quantity >= Integer.parseInt(quantityBagMin1) && quantity <= Integer.parseInt(quantityBagMax1) && limitBag1 > 0) {
                        log("Limit bag1: " + limitBag1);
                            if (isDestinationInArray(destination, destinationBagArray1)) {
                                log("Match found for Bag 1: " + destination);
                                countBag1++;

                                float actualAmount = convertStringToInt(freight);
                                String bid = calculateBidAmount(actualAmount);
                                rowDetails.put("rowIndex", i); // Store row index
                                rowDetails.put("destination", destination);
                                rowDetails.put("bid", bid);
                                rowDetails.put("matchedBag", "bag1");
                                matchedRows.add(rowDetails);
                                continue;
                            }

                        else{
                            continue;
                        }

                    }

                    // Bag 2 conditions
                    if (quantity >= Integer.parseInt(quantityBagMin2) && quantity <= Integer.parseInt(quantityBagMax2) && limitBag2 > 0) {
                        log("Limit bag2: " + limitBag2);
                            if (isDestinationInArray(destination, destinationBagArray2)) {
                                log("Match found for Bag 2: " + destination);
                              countBag2++;
                                float actualAmount = convertStringToInt(freight);
                                String bid = calculateBidAmount(actualAmount);
                                rowDetails.put("rowIndex", i); // Store row index
                                rowDetails.put("destination", destination);
                                rowDetails.put("bid", bid);
                                rowDetails.put("matchedBag", "bag2");
                                matchedRows.add(rowDetails);
                                continue;
                            }

                       else{
                           continue;
                        }
                    }

                    // Bag 3 conditions
                    if (quantity >= Integer.parseInt(quantityBagMin3) && quantity <= Integer.parseInt(quantityBagMax3) && limitBag3 > 0) {
                        log("Limit bag3: " + limitBag3);
                            if (isDestinationInArray(destination, destinationBagArray3)) {
                                log("Match found for Bag 3: " + destination);
                               countBag3++;
                                float actualAmount = convertStringToInt(freight);
                                String bid = calculateBidAmount(actualAmount);
                                rowDetails.put("rowIndex", i); // Store row index
                                rowDetails.put("destination", destination);
                                rowDetails.put("bid", bid);
                                rowDetails.put("matchedBag", "bag3");
                                matchedRows.add(rowDetails);
                                continue;
                            }// Exit the loop entirely

                       else{
                           continue;
                        }
                    }

                   log("No match found for Bag: " + destination);

                } else {
                    System.out.println("Unknown type for: " + destination);
                }
            }

    } catch (Exception e) {
        e.printStackTrace();
    }

        // Define limits for each category
        Map<String, Integer> limits = new HashMap<>();
        limits.put("bulk1", Integer.parseInt(testData.get(columnNames.LimitBulk1)));
        limits.put("bulk2", Integer.parseInt(testData.get(columnNames.LimitBulk2)));
        limits.put("bag1", Integer.parseInt(testData.get(columnNames.LimitBag1)));
        limits.put("bag2", Integer.parseInt(testData.get(columnNames.LimitBag2)));
        limits.put("bag3", Integer.parseInt(testData.get(columnNames.LimitBag3)));

// Store selected destinations per category
        Map<String, List<String>> matchedDestinations = new HashMap<>();
        matchedDestinations.put("bulk1", new ArrayList<>());
        matchedDestinations.put("bulk2", new ArrayList<>());
        matchedDestinations.put("bag1", new ArrayList<>());
        matchedDestinations.put("bag2", new ArrayList<>());
        matchedDestinations.put("bag3", new ArrayList<>());

// Group matched destinations by category
        for (Map<String, Object> matchedRow : matchedRows) {
            String category = null;
            if (matchedRow.containsKey("matchedBag")) {
                category = (String) matchedRow.get("matchedBag");
            } else if (matchedRow.containsKey("matchedBulk")) {
                category = (String) matchedRow.get("matchedBulk");
            }

            if (category != null) {
                String destination = (String) matchedRow.get("destination");
                if (matchedDestinations.containsKey(category)) {
                    matchedDestinations.get(category).add(destination);
                }
            }
        }

// Store final selection


// Process each category based on sequence and limit
        for (String category : matchedDestinations.keySet()) {
            List<String> allDestinations = new ArrayList<>();
            switch (category) {
                case "bulk1":
                    allDestinations = destinationBulkArray1 != null ? Arrays.asList(destinationBulkArray1) : new ArrayList<>();
                    break;
                case "bulk2":
                    allDestinations = destinationBulkArray2 != null ? Arrays.asList(destinationBulkArray2) : new ArrayList<>();
                    break;
                case "bag1":
                    allDestinations = destinationBagArray1 != null ? Arrays.asList(destinationBagArray1) : new ArrayList<>();
                    break;
                case "bag2":
                    allDestinations = destinationBagArray2 != null ? Arrays.asList(destinationBagArray2) : new ArrayList<>();
                    break;
                case "bag3":
                    allDestinations = destinationBagArray3 != null ? Arrays.asList(destinationBagArray3) : new ArrayList<>();
                    break;
                default:
                    allDestinations = new ArrayList<>();
            }

            List<String> matched = matchedDestinations.getOrDefault(category, new ArrayList<>());
            int limit = limits.getOrDefault(category, 0);
            int count = 0;

            for (String seqDestination : allDestinations) {
                if (matched.contains(seqDestination) && count < limit) {
                    for (Map<String, Object> matchedRow : matchedRows) {
                        String rowCategory = matchedRow.containsKey("matchedBag")
                                ? (String) matchedRow.get("matchedBag")
                                : (String) matchedRow.get("matchedBulk");

                        if (category.equals(rowCategory) && seqDestination.equalsIgnoreCase((String) matchedRow.get("destination"))) {
                            finalSelection.add(new HashMap<>(matchedRow));
                            count++;
                            log("Selected " + seqDestination + " for " + category + " (Total selected: " + count + "/" + limit + ")");
                            if (count >= limit) break;
                        }
                    }
                }

                if (count >= limit) break; // stop if limit reached
            }
        }

        enterBid();

        return countBulk1>0||countBag1>0||countBulk2>0||countBag2>0||countBag3>0;
     //   return countBulk1>0||countBag1>0||countBulk2>0||countBag2>0||countBag3>0;
    }

    private static boolean isDestinationInArray(String destination, String[] destinationArray) {
        for (String dest : destinationArray) {
            if (dest.trim().equalsIgnoreCase(destination)) {
                return true;
            }
        }
        return false;
    }

    public void waitUntilLoaderAppearAndDisappear() {
        try {
            // Hardcoded title 'Please wait' and timeout 3000 milliseconds
            String loaderXPath = "//*[@title='Please wait']";  // XPath with hardcoded title
            long timeoutMillis = 30000;  // Hardcoded timeout in milliseconds

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeoutMillis));

            // Wait for loader to appear (visible state)
            WebElement loader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loaderXPath)));

            // Wait until the loader disappears (invisible state)
            wait.until(ExpectedConditions.invisibilityOf(loader));

        } catch (TimeoutException e) {
            // Do nothing, just continue execution if loader does not appear
        }
    }

    public void enterBid(){
        log("matched rows: "+matchedRows);
        log("Final selection of rows: "+finalSelection);
       for (Map<String, Object> row : matchedRows) {
            System.out.println(row);
        }
        WebElement table = driver.findElement(By.id("__xmlview0--idUtclVCVendorAssignmentTable"));
        List<WebElement> rows = table.findElements(By.xpath(".//tr[contains(@id, '__item7-')]"));

        By bySearchTime = By.xpath("//*[text()='Starts in 0:0:"+testData.get(columnNames.SearchTime)+"'] | //*[contains(text(), 'Expires in')]");

        if(findElementPresenceReturnBool(bySearchTime,600)){
            log("New milli sec added in test data to wait for search"+testData.get(columnNames.WaitTimeInMilliAfterSearch));
            hardWait(Integer.parseInt(testData.get(columnNames.WaitTimeInMilliAfterSearch)));
            click(bySearchButton,"Search button",4);
            waitUntilLoaderAppearAndDisappear();
            findElementPresenceReturnBool(By.xpath("//*[contains(text(), 'Expires in')]"), 30);
        }
        // Second pass: Enter bids for matched rows
//        for (Map<String, Object> matchedRow : matchedRows) {
//            log("Code entered in Bid amount entering section");
//            int rowIndex = (int) matchedRow.get("rowIndex");
//            String destination = (String) matchedRow.get("destination");
//            String bid = (String) matchedRow.get("bid");
//
//            // Locate the row
//            WebElement row = rows.get(rowIndex);
        for (Map<String, Object> selectedRow : finalSelection) {
            int rowIndex = (int) selectedRow.get("rowIndex");
            String destination = (String) selectedRow.get("destination");
            String bid = (String) selectedRow.get("bid");
            WebElement row = rows.get(rowIndex);
            // XPaths for bid amount input fields
            By byBidAmountInput = By.xpath(".//td[6]//*[text()='" + destination + "']/parent::td/following-sibling::td//input[contains(@disabled,'disabled')]");
            By byBidAmountInputActivated = By.xpath(".//td[6]//*[text()='" + destination + "']/parent::td/following-sibling::td//input[not(contains(@disabled, 'disabled'))]");
            log("destination xpath of " + destination + " : " + byBidAmountInputActivated);
            // Ensure the input field is active and enter the bid

            if (findElementAbsenceReturnBool(byBidAmountInput, 600)) {

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
                wait.until(ExpectedConditions.elementToBeClickable(byBidAmountInputActivated));

                WebElement input = row.findElement(byBidAmountInputActivated);
                log("destination xpath of Entering bid "+input);
                hardWait(Integer.parseInt(testData.get(columnNames.WaitTimeInMilliBeforeEnteringBid)));
                enterTextAfterClickFast(input, bid);
                // submitBidSaveYes();

            }
        }
        submitBidSaveYes();
     //   System.out.println("Unknown type for: " );
    }

  /*  public int enterBidForBulkBag(String destin, String spi, int limit, int quantityRangeMin, int quantityRangeMax)
    {
        int returnLimit = 0;

        if (limit != 0) {
            By bySPI = By.xpath("//td[6]//*[text()='" + destin + "']/parent::td/following-sibling::td/span[contains(text(),'" + spi + "')]");
            if ((findAndReturnElementsCount(bySPI) > 0)) {
                log("Destination found"+destin);
                By byOrderQuantity = By.xpath("//td[6]//*[text()='" + destin + "']/parent::td/following-sibling::td[6]/span");
                By byFreight = By.xpath("//td[6]//*[text()='" + destin + "']/parent::td/following-sibling::td[12]/span");

                String actualQuantity = findElementGetText(byOrderQuantity);
                String actualq[] = actualQuantity.split("\\.");
                int actualQuant = convertStringToInt(actualq[0]);

                float actualAmount = convertStringToInt(findElementGetText(byFreight));

                if (actualQuant >= quantityRangeMin && actualQuant <= quantityRangeMax) {
                    By byBidAmountInput = By.xpath("//td[6]//*[text()='" + destin + "']/parent::td/following-sibling::td//input[contains(@disabled,'disabled')]");

                    By byBidAmountInputActivated = By.xpath("//td[6]//*[text()='" + destin + "']/parent::td/following-sibling::td//input");
                    String bid = calculateBidAmount(actualAmount);

                    By bySearchTime = By.xpath("//*[text()='Starts in 0:0:"+testData.get(columnNames.SearchTime)+"']");

                    if(findElementPresenceReturnBool(bySearchTime,600)){
                        click(bySearchButton,"Search button",2);
                    }

                    if (findElementAbsenceReturnBool(byBidAmountInput, 600)) {
                        for (WebElement w  :findAndReturnElements(byBidAmountInputActivated))
                        {
                            hardWait(Integer.parseInt(testData.get(columnNames.WaitTimeInMilliBeforeEnteringBid)));
                            enterTextAfterClickFast(w, bid);
                            returnLimit++;
                            if(returnLimit>=limit)
                                break;
                        }

                    }

                }
                else
                {
                    log("Range did not match actual "+actualQuant+" Expected range is "+quantityRangeMin+quantityRangeMax);
                }
            } else {
                log("Destination Not Found Expected is : " + destin);
            }
        } else {
            log("limit of "+spi+" is "+ 0);
        }
        return returnLimit;
    }*/
/////////////////



    //////////////////////
    public  void submitBidSaveYes(){
        click(bySaveBid,"Save button",1);
        hardWait(Integer.parseInt(testData.get(columnNames.WaitTimeInMilliBeforeYesConfirm)));
        click(byConfirmYesButton,"Confirm Button");
        click(byConfirmYesButton);
        verifyResult(findElementPresenceReturnBool(bySuccessMessage, 8), "Success Message validated");
        log("Bid submitted successfully");

    }

    public String calculateBidAmount(By byFreight,int actualQuantity){
        float actualAmount = convertStringToInt(findElementGetText(byFreight));
        var minus = String.valueOf((actualAmount * (5.0f / 100.0f))).split("\\.");
        var minusAmount = Integer.parseInt(minus[0]);
        int bidAmount = actualQuantity - minusAmount;
        var bidAmountarray = String.valueOf(bidAmount).split("\\.");
        return bidAmountarray[0];
    }

    public String calculateBidAmount(float actualAmount){

        var minus = String.valueOf((actualAmount * (5.0f / 100.0f))).split("\\.");
        var minusAmount = Integer.parseInt(minus[0]);
        int bidAmount = (int) (actualAmount - minusAmount);
        var bidAmountarray = String.valueOf(bidAmount).split("\\.");
        return bidAmountarray[0];
    }
}



