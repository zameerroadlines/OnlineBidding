
package com.bosch.automation.testbase;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.bosch.automation.APIHelper.RestAPIHelper;
import com.bosch.automation.testdataproperties.ColumnNames;
import com.bosch.automation.utilities.ApplicationProperties;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class BaseClass {
//region Initializations and constants
    public static final Logger loggerSLF = LoggerFactory.getLogger(BaseClass.class);
    public static ApplicationProperties properties = new ApplicationProperties();
    public UiAutomator2Options capabilities = new UiAutomator2Options();
    public static final String author = "author";
    public static final String category = "category";
    public static final String reportPath = "reportPath";
    public static final String reportPathAppendDate = "reportPathAppendDate";
    public String reportName;
    public static final String platformName = "platformName";
    public static final String platformVersion = "platformVersion";
    public String logMessage = "";
    public static final String automationName = "automationName";
    public static final String sheetname = "sheetname";
    public static final String app = "app";
    public static final String appPackage = "appPackage";
    public static final String appActivity = "appActivity";
    public static final String chromeDriverPath = "chromeDriverPath";
    public static final String edgeDriverPath = "edgeDriverPath";
    public static final String webUrl = "webUrl";
    public static final String webUrlConfirmation = "webUrlConfirmation";
    public static final String webUrlMaintenance = "webUrlMaintenance";



//    public static final String noReset = "noReset";
    public static final String remoteAddress = "remoteAddress";
    public DriverActions driverActions;
    public TestDataReader testDataReader;
    public ColumnNames columnNames;

    RestAPIHelper restAPIHelper;
    public  ExtentReports extentReports;
    File file;
    String webDriver;
    String testDataPath;
    String url = "url";
    public Map<String, String> testData;
    // public WebDriver driver;
    public ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public ThreadLocal<ExtentTest> logger = new ThreadLocal<>();
    public static ThreadLocal<ExtentReports> extentReport = new ThreadLocal<>();

    //endregion
    @Parameters({"deviceName","platformVersion","portNumber"})

    public void setDriver(WebDriver driver){
        this.driver.set(driver);
    }
    public void setLogger(ExtentTest log){
        this.logger.set(log);
    }
    public ExtentTest getLogger(){
        return this.logger.get();
    }

    public void setReport(ExtentReports report){
        extentReport.set(report);
    }
    public ExtentReports getReport(){
        return extentReport.get();
    }
    public void setDriver(AppiumDriver driver){
        this.driver.set(driver);
    }
    public WebDriver getDriver(){
        return this.driver.get();
    }
    @BeforeTest
    public void initializeBrowserEnvironment(ITestContext context)
    {
        properties = new ApplicationProperties();
        initializeBrowserProperties();
        initializeEnvironment();
        testDataReader = new TestDataReader();
        columnNames = new ColumnNames();
        this.initializationsReport(context);
    }
    public String getDetailsFromPOMOrProperties(String propertyName){
       return System.getProperty(propertyName)!=null?
                System.getProperty(propertyName):properties.readProperty(propertyName);
    }
    public void initializeBrowserProperties(){
        String selectBrowserFromPOMORProperties=getDetailsFromPOMOrProperties("browser");

        String webURL = selectBrowserFromPOMORProperties.equalsIgnoreCase("QA")?properties.readProperty(webUrlConfirmation):properties.readProperty(webUrlMaintenance);

        boolean browserToInitiate=selectBrowserFromPOMORProperties.equalsIgnoreCase("Chrome");
            file = browserToInitiate?
                    new File(properties.readProperty(chromeDriverPath)):
                    new File(properties.readProperty(edgeDriverPath));
            webDriver=browserToInitiate? "webdriver.chrome.driver":"webdriver.edge.driver";
    }

    public void initiateBrowser(){
        WebDriver driver;
        System.setProperty(webDriver, file.getAbsolutePath());

        if(System.getProperty("browser").equalsIgnoreCase("Chrome")) {
            driver = new ChromeDriver();
        }
        else{
            driver = new EdgeDriver();
        }
        driver.manage().window().maximize();
        driver = launchURLBasedOnEnvironment(driver);
        getLogger().info("Web URL hosted is {}"+properties.readProperty(webUrl));
        setDriver(driver);
    }

    public void initializeEnvironment(){
        String selectEnvironmentFromPOMORProperties=getDetailsFromPOMOrProperties("environment");
        // select QA for test and Dev for dev environment
        testDataPath = selectEnvironmentFromPOMORProperties.equalsIgnoreCase("QA")?
                "src/main/resources/TestData/TestEnvironment/":
                "src/main/resources/TestData/DevEnvironment/";

        url = selectEnvironmentFromPOMORProperties.equalsIgnoreCase("QA")?properties.readProperty(webUrlConfirmation):properties.readProperty(webUrlMaintenance);
    }

    @BeforeMethod
    @Parameters({"deviceName","platformVersion","portNumber"})
    public  void initiateDriverCaptureTestReport(Method context,@Optional String deviceName,ITestContext testContext,@Optional String platformVersion,@Optional String portNumber)
    {
        try
        {
            testData=ReadTestData(context.getName(),testDataPath,"TestData.xlsx");
            setLoggerForApp(context, testData.get(columnNames.App), deviceName, platformVersion, portNumber);
            getLogger().info(" Stating Test, current class name is" + context.getDeclaringClass());
            getLogger().info("Description : " + context.getAnnotation(Test.class).description());
            getLogger().info(logMessage);
                initializeWebMobileAPIApp(testData.get(columnNames.App), deviceName, platformVersion, portNumber);
        }
        catch (Exception e) {
            getLogger().fail("Driver Initialization failed"+e.getMessage());
            getReport().flush();
        }
        driverActions = new DriverActions(getDriver(),getLogger());
    }
    public void initializeWebMobileAPIApp(String app, String deviceName, String platformVersion, String portNumber){
        ITestResult result=null;
        try {
            switch (app) {
                case "Web":
                    initializeWebApp();
                    break;
                case "WebExistingWindow":
                    initializeWebAppOnExistingWindow();
                    break;
                case "Mobile":
                    initializeMobileApp(deviceName, platformVersion, portNumber);
                    break;
                case "API":
                    restAPIHelper = new RestAPIHelper(getLogger());
                    break;
            }
        }
        catch (Exception e)
        {
            getLogger().fail("Failed to launch the application, Exception "+e.getMessage());
            Assert.fail("Failed to launch the application, Exception "+e.getMessage());
            getReport().flush();
            //  driver.quit();
        }
    }

    public void setLoggerForApp( Method context,String app,String deviceName,String platformVersion,String portNumber){
        switch (app){
            case "Web":
                setLogger(getReport().createTest(context.getName()).assignAuthor(properties.readProperty(author))
                        .assignCategory("Web Automation tests")
                        .assignDevice("Web Automation"));
                getLogger().info("Web automation test started");
                break;
            case "WebExistingWindow":
                setLogger(getReport().createTest(context.getName()).assignAuthor(properties.readProperty(author))
                        .assignCategory("Web Automation tests existing window")
                        .assignDevice("Web Automation existing window"));
                getLogger().info("Web automation test started on existing window");
                break;
            case "Mobile":
                setLogger(getReport().createTest(context.getName()).assignAuthor(properties.readProperty(author))
                        .assignCategory("Platform Version:"+platformVersion+"..PortNumber:"+portNumber)
                        .assignDevice(deviceName));
                getLogger().info("Mobile automation test started");
                break;
            case "API":
                setLogger(getReport().createTest(context.getName()).assignAuthor(properties.readProperty(author))
                        .assignCategory("API Tests")
                        .assignDevice("API Automation"));
                getLogger().info("API automation test started");
                break;

                default:
                getLogger().info("App in test data is incorrect choose either Mobile, Web or API ");
                break;
        }
        getLogger().info("Description : "+context.getAnnotation(Test.class).description());
    }
    public WebDriver initializeWebMobileAppReturnDriver(String app,String deviceName,String platformVersion,String portNumber)
    {
        WebDriver secondDriver=null;
        try {
            if(app.equalsIgnoreCase("Web")){
                secondDriver=initializeWebAppReturnDriver();}
            else{
                secondDriver=initializeMobileAppReturnDriver(deviceName,platformVersion,portNumber);
            }
        }
        catch (Exception e)
        {
            getReport().flush();
            Assert.fail("Failed to launch the application, Exception "+e.getMessage());
            //  driver.quit();
        }
        return secondDriver;
    }
    @AfterMethod
    public void getResult(ITestResult result,ITestContext context) throws Exception {
        switch (result.getStatus()) {
            case ITestResult.FAILURE:
                //captureScreenshot(result);
                getLogger().log(Status.FAIL, MarkupHelper.createLabel( " Test case FAILED  below are details:", ExtentColor.RED));
                if(!testData.get(columnNames.App).equals("API")) {
                    getLogger().fail("Failed Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(result, context)).build());
                }
                getLogger().fail(result.getThrowable());
                break;
            case ITestResult.SUCCESS:
                getLogger().log(Status.PASS, MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
            break;

            case ITestResult.SKIP:
                getLogger().log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.BLUE));
                break;
            default:
                getLogger().log(Status.FAIL, MarkupHelper.createLabel( " Test case FAILED  below are details:", ExtentColor.RED));
                getLogger().fail(result.getThrowable());
        }

        if(!testData.get(columnNames.App).equals("API"))
            if(driver!=null){
            getDriver().quit();}
        else {
                getLogger().fail("Driver initialization failed");
            }
        getReport().flush();
    }
    public void getResultFailed() throws Exception {
                getLogger().log(Status.FAIL, MarkupHelper.createLabel( " Test case FAILED  below are details:", ExtentColor.RED));
                getLogger().fail("Failed Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
        getReport().flush();
        getDriver().quit();

    }
    public void initializationsReport(ITestContext context) {

        if(properties.readProperty(reportPathAppendDate).equalsIgnoreCase("Yes")) {
            reportName = context.getName() + " FinalReport" + getCurrentDateTimeAsString("dd_MM_yyyy_HH_mm_ss") + ".html";
        }
        else{
            reportName=context.getName()+"FinalReport.html";
        }
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(properties.readProperty(reportPath)+ "/"+reportName);
        sparkReporter.config().setReportName(reportName);
        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        setReport(extentReports);
    }
    public String getCurrentDateTimeAsString(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return dateFormat.format(date);
    }
    public void initializeMobileApp(String deviceName,String platformVersion,String portNumber) throws MalformedURLException {
        capabilities.setDeviceName(properties.readProperty(platformName));
        capabilities.setPlatformName(platformVersion);
        capabilities.setUdid(deviceName);
        capabilities.setAutomationName(properties.readProperty(automationName));
        //capabilities.setApp(properties.readProperty(app));
        capabilities.setChromedriverExecutable(file.getAbsolutePath());
        //capabilities.setAppActivity(properties.readProperty(appActivity));
        capabilities.withBrowserName("chrome");
       setDriver(new AndroidDriver(new URL(properties.readProperty(remoteAddress).replace("portNumber",portNumber)),capabilities));
       getDriver().get(url);
    }
    public WebDriver initializeMobileAppReturnDriver(String deviceName,String platformVersion,String portNumber) throws MalformedURLException {

        capabilities.setDeviceName(properties.readProperty(platformName));
        capabilities.setPlatformName(platformVersion);
        capabilities.setUdid(deviceName);
        capabilities.setAutomationName(properties.readProperty(automationName));
        capabilities.setApp(properties.readProperty(app));
        capabilities.setChromedriverExecutable(file.getAbsolutePath());
        capabilities.setAppActivity(properties.readProperty(appActivity));
        capabilities.setAppPackage(properties.readProperty(appPackage));

        return (new AndroidDriver(new URL(properties.readProperty(remoteAddress).replace("portNumber",portNumber)),capabilities));
    }
    public WebDriver launchURLBasedOnEnvironment(WebDriver driver){
        if(System.getProperty("environment").equalsIgnoreCase("Confirmation")) {
            driver.get(properties.readProperty(webUrlConfirmation));
        }
        else{
            driver.get(properties.readProperty(webUrlMaintenance));
        }
        return driver;
    }

    public void initializeWebAppOnExistingWindow() {
        System.setProperty(webDriver, file.getAbsolutePath());
        String userDataDir = "C:\\Users\\C5351545\\AppData\\Local\\Google\\Chrome\\User Data";
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("user-data-dir=" + userDataDir);
        chromeOptions.addArguments("profile-directory=Default");

        setDriver(new ChromeDriver(chromeOptions));
         getDriver().get(url);
    }
    public void initializeWebApp() {
        WebDriverManager.chromedriver().setup();
    //    System.setProperty(webDriver, file.getAbsolutePath());
//        String userDataDir = "C:\\Users\\C5351545\\AppData\\Local\\Google\\Chrome\\User Data";
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--start-maximized");
//        chromeOptions.addArguments("user-data-dir=" + userDataDir);
//        chromeOptions.addArguments("profile-directory=Default");

//        setDriver(new ChromeDriver(chromeOptions));
       // getDriver().get(url);
        WebDriver driverToLaunch= getDetailsFromPOMOrProperties("browser").equalsIgnoreCase("Chrome")?
                new ChromeDriver():new EdgeDriver();
        driverToLaunch.manage().window().maximize();
        setDriver(driverToLaunch);
        getDriver().get(testData.get(columnNames.URL));
    }
    public WebDriver initializeWebAppReturnDriver() {
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(properties.readProperty(webUrl));
        getLogger().info("Web URL hosted is {}"+properties.readProperty(webUrl));
        return driver;
    }

    public void setCapability(String capType, String property) {
        if(property!=null && !property.equals("")) {
            capabilities.setCapability(capType, property);
        }
    }

    public void tearDownMobileApp(){
        getDriver().close();
    }
    public String captureScreenshot(ITestResult result,ITestContext context) throws IOException {
        String currentDateTime = getCurrentDateTimeAsString("dd_MM_yyyy_HH_mm_ss");
        File file = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File(properties.readProperty(reportPath)+ "/" +context.getName()+result.getName()+currentDateTime+".png"));
        //File fileSaved = new File(properties.readProperty(reportPath)+ "/" +context.getName()+result.getName()+currentDateTime+".png");
        //return fileSaved.getAbsolutePath();
        return "./" +context.getName()+result.getName()+currentDateTime+".png";
    }

    public String captureScreenshot() throws IOException {
        String currentDateTime = getCurrentDateTimeAsString("dd_MM_yyyy_HH_mm_ss");
        File file = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File(properties.readProperty(reportPath)+ "/FailedScreenshot"+currentDateTime+".png"));
        return "./FailedScreenshot"+currentDateTime+".png";
    }
    public Map<String, String> ReadTestData(String tcName,String testDataPath,String testSheetName) {
        try {
            testData = testDataReader.getTestData(tcName,properties.readProperty(sheetname),testDataPath,testSheetName);
            logMessage="Test data read for test case "+tcName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return  testData;
    }

    public void initializeWebMobileAppForTestData(String testDataIdInExcel,String deviceName,String platformVersion,String portNumber) {
        testData = ReadTestData(testDataIdInExcel,testDataPath,"TestData"+testData.get(columnNames.App));
        try {
            this.initializeWebMobileAPIApp(testData.get(columnNames.App),deviceName,platformVersion,portNumber);
        } catch (Exception e) {
            getLogger().fail("Failed to launch the application, Exception "+e.getMessage());
            getReport().flush();
        }
        driverActions = new DriverActions(getDriver(), getLogger());
    }
    public WebDriver initializeWebMobileAppForTestDataReturnDriver(String testDataIdInExcel,String deviceName,String platformVersion,String portNumber) {
        WebDriver secondDriver;
        testData = ReadTestData(testDataIdInExcel,testDataPath,"TestData.xlsx");
       secondDriver= this.initializeWebMobileAppReturnDriver(testData.get(columnNames.App),deviceName,platformVersion,portNumber);
        driverActions = new DriverActions(getDriver(), getLogger());
        return secondDriver;
    }
}