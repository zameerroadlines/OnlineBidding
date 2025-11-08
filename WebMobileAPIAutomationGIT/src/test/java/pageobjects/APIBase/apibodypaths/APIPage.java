package pageobjects.APIBase.apibodypaths;

import com.aventstack.extentreports.ExtentTest;
import com.bosch.automation.APIHelper.RestAPIHelper;
import com.bosch.automation.testdataproperties.ColumnNames;
import com.gargoylesoftware.htmlunit.HttpMethod;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import pageobjects.APIBase.RequestBodyFiles;
import java.util.Map;


public class APIPage extends RestAPIHelper {
    public ExtentTest logger;
   // public Map<String, String> testData;

    public APIPage(ExtentTest logger, Map<String, String> testData){
        super(logger);
        this.testData=testData;
        columnNames = new ColumnNames();
    }

  

    public void postJSONRequestGetValue(String requestBody,String key){
        String endPointURL ="https://"+testData.get(columnNames.BaseURL)
                +testData.get(columnNames.EndPointURL);
        requestBody =requestBody.equals("")?"":getRequestBody(requestBody);
        Response response = getAPIResponse(HttpMethod.POST.toString(),endPointURL,getHeaders(),requestBody);
        log("Value for key : "+getNodeText(response,key));
    }

    public void getJSONRequestPrintCombinations(){
        String endPointURL ="https://"+testData.get(columnNames.BaseURL)
                +testData.get(columnNames.EndPointURL);
        Response response = getAPIResponse(HttpMethod.GET.toString(),endPointURL,getHeaders(),"");

        JsonPath jsonPath = getJsonPath(response);

        //To get a specific field value of an indexed element
        log("Getting title with index 2 "+ jsonPath.get("[2].title"));
        // To get whole indexed element
        log("Getting full element with index 3 "+ jsonPath.getJsonObject("[3]"));
        // get particular value for all records
        log("Id of all elements are "+jsonPath.getList("id"));
        // get conditional output
        log("ID where userID is 9 "+jsonPath.getList("findAll{it.id == 23}.title"));
        // We can get size of array using size() or size
        log("Total number of records "+jsonPath.getString("size()"));
    }

    public void putJSONResourcePrintUpdate(String requestBody,String index){
        String endPointURL ="https://"+testData.get(columnNames.BaseURL)
                +testData.get(columnNames.EndPointURL)+index;
        requestBody =requestBody.equals("")?"":getRequestBody(requestBody);
        Response response = getAPIResponse(HttpMethod.PUT.toString(),endPointURL,getHeaders(),requestBody);

    }

    public void deleteJSONResourceVerifyResponseCode(String requestBody,String index){
        String endPointURL ="https://"+testData.get(columnNames.BaseURL)
                +testData.get(columnNames.EndPointURL)+index;
        requestBody =requestBody.equals("")?"":getRequestBody(requestBody);
        Response response = getAPIResponse(HttpMethod.DELETE.toString(),endPointURL,getHeaders(),requestBody);
        verifyResponseCode(response.getStatusCode(),200);
        log("Successfully deleted the resource at index "+index);
    }

    public void postChatCompletionAPI(){
        String endPointURL ="https://"+testData.get(columnNames.BaseURL)
        +testData.get(columnNames.EndPointURL);
        String requestBody = RequestBodyFiles.OpenAI_ChatCompletionRequestBody;
        Response response = getAPIResponse(HttpMethod.POST.toString(),endPointURL,getHeaders(),getRequestBody(requestBody));
        log(getNodeText(response,"content"));
        log(getExpectedValue(response.toString(),"content",0));
    }

}
