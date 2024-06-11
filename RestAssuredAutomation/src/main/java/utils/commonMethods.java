package utils;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.junit.Assert;

public class commonMethods {

//    @Step("GET Request1")
//    public static Response getRequest(String baseUri, String endpoint) {
//        RestAssured.baseURI = baseUri;
//        RequestSpecification request = RestAssured.given();
//        return request.get(endpoint);
//    }

    @Step("GET Request1")
    public static Response getRequest(String baseUri, String endpoint, String token) {
        RestAssured.baseURI = baseUri;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token);
        return request.get(endpoint);
    }

    @Step("POST Request")
    public static Response postRequest(String baseUri, String endpoint, Object body) {
        RestAssured.baseURI = baseUri;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(body);
        return request.post(endpoint);
    }
//
//    @Step("POST Request")
//    public static Response postRequest(String baseUri, String endpoint, String token, Object body) {
//        RestAssured.baseURI = baseUri;
//        RequestSpecification request = RestAssured.given();
//        request.header("Content-Type", "application/json");
//        request.header("Authorization", "Bearer " + token);
//        request.body(body);
//        return request.post(endpoint);
//    }
//
    @Step
    public static String generateAuthToken(){
        RequestSpecification request = RestAssured.given();
        JSONObject requestParams = TestDataUtil.readTestData("test-data/user.json");
        request.header("Content-Type", "application/json");
        request.body(requestParams.get("Tokenuser"));
        Response response = request.post("https://projectx-qa-api.sourcef.us/auth-service/auth/login-token");
        Assert.assertEquals(response.getStatusCode(), 200);
        JsonPath jsonPath = response.jsonPath();
        String token = jsonPath.getString("accessToken");
        return token;
    }
}
