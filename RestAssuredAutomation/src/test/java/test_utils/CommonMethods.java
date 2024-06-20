package test_utils;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import main_utils.LoggerUtil;
import org.junit.AfterClass;
import org.junit.Assert;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class CommonMethods extends BaseTest{

    @Step("GET Request")
    public static Response GetRequest(String endpoint, String token) {
        RestAssured.baseURI = baseUrl;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token);
        return request.get(endpoint);
    }

    @Step("POST Request")
    public static Response PostRequest(String endpoint, String token, Object body) {
        RestAssured.baseURI = baseUrl;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.header("Authorization", "Bearer " + token);
        request.body(body);
        return request.post(endpoint);
    }

    @Step("DELETE Request")
    public static Response DeleteRequest(String endpoint, String token) {
        RestAssured.baseURI = baseUrl;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token);
        return request.delete(endpoint);
    }

    @Step("PUT Request")
    public static Response putRequest(String endpoint, String token, Object body) {
        RestAssured.baseURI = baseUrl;

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.header("Authorization", "Bearer " + token);
        request.body(body);

        return request.put(endpoint);
    }
    @Step
    public static String GenerateAuthToken(){
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestParams.get("TokenUser"));
        Response response = request.post(baseUrl +requestParams.get("TokenEndpoint"));
        Assert.assertEquals(response.getStatusCode(), 200);
        JsonPath jsonPath = response.jsonPath();
        String token = jsonPath.getString("accessToken");
        return token;
    }

    @AfterClass
    public static void TearDown() {
        LoggerUtil.info("Teardown complete.");
    }

    @Step("Logging Step: {0}")
    public static void LogStep(String step) {
        LoggerUtil.info(step);
    }

    @Attachment(value = "Response", type = "application/json")
    public static String LogResponse(Response response) {
        return response.asString();
    }

    @Attachment(value = "Test Log", type = "text/plain")
    public static byte[] AttachLog() {
        try {
            return Files.readAllBytes(Paths.get("logs/app.log"));
        } catch (Exception e) {
            e.printStackTrace();
            return "Unable to attach log".getBytes();
        }
    }

    @Step("Verify Status code")
    public static void VerifyStatusCode(Response response,Integer statusCode){
        assertEquals(response.getStatusCode(), statusCode);
    }
    @Step("Verify JSON schema")
    public static void VerifyResponseSchema(Response response,String filePath){
        assertThat(response.asString(), JsonSchemaValidator.matchesJsonSchemaInClasspath(filePath));
    }

    @Step("Validate JSON Response")
    public static void ValidateJsonResponseKey(Response response, String keyName, String expectedValue){
        JsonPath jsonPath = response.jsonPath();
        String name = jsonPath.getString(keyName);
        assertEquals(name, expectedValue);
    }
}
