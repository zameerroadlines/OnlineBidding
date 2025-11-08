package com.bosch.automation.APIHelper;

import com.aventstack.extentreports.ExtentTest;
import com.bosch.automation.testbase.BaseClass;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import net.minidev.json.JSONArray;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class  RestAPIHelper extends BaseClass {
    public ExtentTest logger;
    public RestAPIHelper(ExtentTest logger){
        this.logger=logger;
    }
    public Response getAPIResponse(String httpMethod,String endpoint,Map headers,String requestBody) {
        Response response =	null;
        if(httpMethod == null && endpoint == null) {
            Assert.fail("Either httpMethod or endpoint are null");
        }
        log("Get API response for call with httpMethod :"+httpMethod+ " and endpoint URL :"+endpoint);

        switch(httpMethod) {
            case "POST":
                response =	RestAssured.given().body(requestBody).headers(headers)
                        .baseUri(endpoint).when().post().thenReturn();
                break;

            case "GET":
                response =	RestAssured.given().headers(headers)
                        .baseUri(endpoint).when().get().thenReturn();
                break;

            case "PUT":
                response =	RestAssured.given().body(requestBody).headers(headers)
                        .baseUri(endpoint).when().put().thenReturn();
                break;

            case "PATCH":
                break;

            case "DELETE":
                response =	RestAssured.given().body(requestBody).headers(headers)
                        .baseUri(endpoint).when().delete().thenReturn();
                break;
            default: Assert.fail("Please choose valid HTTP method POST,GET,PUT or DELETE");
        }
        if (response != null) {
            log("Response received "+response.getBody().asString());
        }
        return response;
    }

    public Response getAPIResponseWithoutBody(String httpMethod,String endpoint,Map headers)
    {
        Response response;
        if(httpMethod == null && endpoint == null) {
            throw new Error("InSufficient API Request details");
        }
        response =	RestAssured.given().headers(headers)
                .baseUri(endpoint).when().get().thenReturn();
        return response;
    }

    public Map getHeaders() {
        Map<String,Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer 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");
        return headers;
    }

    public Map getHeadersWithoutAuth() {
        Map<String,Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }

    public Map getHeadersForWithToken(String token) {
        Map<String,Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }

public String getNodeText(Response response,String node){
    io.restassured.path.json.JsonPath jsonPath = response.jsonPath();
    return jsonPath.get(node);
}

    public io.restassured.path.json.JsonPath getJsonPath(Response response){
        return response.jsonPath();
    }

    public String getNodeTextList(Response response,String node){

        io.restassured.path.json.JsonPath jsonPath = response.jsonPath();
        return jsonPath.get(node);
    }

    public void verifyResponseCode(String responseCode){
        Assert.assertEquals(responseCode, "200", "Response code is not 200 instead its " + responseCode);
    }

    public void verifyResponseCode(int responseCode, int expectedCode){
        Assert.assertEquals(expectedCode, responseCode, "Response code is not" + expectedCode + " instead its " + responseCode);
        log("Response code is as expected "+responseCode);
    }

    public String getRequestBody(String requestBodyPath){
        String body=null;
        try {
            body= FileUtils.readFileToString(new File(requestBodyPath), "UTF-8");
        } catch (IOException e) {
            Assert.fail("Unabkle to read file"+requestBodyPath);
        }
        return body;

    }
    public void log(String message)
    {
        logger.info(message);
        loggerSLF.info(message);
    }

    public String getExpectedValue(String response, String expression, int index) {
        JSONArray jsonArray = JsonPath.read(response,expression);
        return String.valueOf(jsonArray.get(index));
    }
}
