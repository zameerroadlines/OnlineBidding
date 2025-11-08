package testscripts.apitestscripts;

import com.bosch.automation.testbase.BaseClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pageobjects.APIBase.RequestBodyFiles;
import pageobjects.APIBase.apibodypaths.APIPage;

public class APIScripts extends BaseClass {

    @Ignore
    @Test(description = "OpenAI : verify post call to chat completion api")
    public void TestChatCompletionAPI()
    {
        APIPage apiPage = new APIPage(getLogger(),testData);
        apiPage.postChatCompletionAPI();
    }
    @Test(description = "jsonplaceholder : Create a JSON Resource print value for specified key")
    public void CreateJSONResource()
    {
        APIPage apiPage = new APIPage(getLogger(),testData);
        apiPage.postJSONRequestGetValue(RequestBodyFiles.JSON_CreateResource1,"title");
        apiPage.postJSONRequestGetValue(RequestBodyFiles.JSON_CreateResource2,"body");
        apiPage.postJSONRequestGetValue(RequestBodyFiles.JSON_CreateResource3,"userId");
    }
    @Test(description = "jsonplaceholder : Get resource print different Gpath")
    public void GetJSONResourcePrintDifferentCombinations()
    {
        APIPage apiPage = new APIPage(getLogger(),testData);
        apiPage.getJSONRequestPrintCombinations();
    }
    @Test(description = "jsonplaceholder : Update a JSON Resource with a particular index")
    public void UpdateJSONResource()
    {
        APIPage apiPage = new APIPage(getLogger(),testData);
        apiPage.putJSONResourcePrintUpdate(RequestBodyFiles.JSON_UpdateResource,"2");
    }
    @Test(description = "jsonplaceholder : Delete a JSON Resource with a particular index")
    public void DeleteJSONResource()
    {
        APIPage apiPage = new APIPage(getLogger(),testData);
        apiPage.deleteJSONResourceVerifyResponseCode(RequestBodyFiles.JSON_UpdateResource,"2");
    }
}
