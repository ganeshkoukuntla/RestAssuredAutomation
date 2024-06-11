package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.TestDataUtil;
import utils.commonMethods;
import utils.ConfigManager;
import utils.LoggerUtil;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;

import static org.testng.Assert.assertEquals;
@Feature("API POST Request Tests")
public class NewPost_Test {

private String baseUrl;

    @BeforeClass
    public void setUp() {
        baseUrl = ConfigManager.getProperty("base_url1");
        LoggerUtil.info("Setup complete.");
    }

    @Test(description = "Users POST request")
    @Description("this test will create a user")
    public void testPost() {
        logStep("Starting POST request test");
        // Read the test data from the JSON file
        JSONObject requestParams = TestDataUtil.readTestData("test-data/user.json");
        Response response = commonMethods.postRequest(baseUrl, ConfigManager.getProperty("post_end_point"), requestParams.toJSONString());
        logResponse(response);

        // Verify the status code is 201 (Created)
        Assert.assertEquals(response.getStatusCode(), 201);

        // Extract and verify specific fields from the response
        JsonPath jsonPath = response.jsonPath();
        String name = jsonPath.getString("name");
        String job = jsonPath.getString("job");
        Assert.assertEquals(name, requestParams.get("name"));
        Assert.assertEquals(job, requestParams.get("job"));
        logStep("POST request test passed");
    }


    @AfterClass
    public void tearDown() {
        LoggerUtil.info("Teardown complete.");
    }

    @Step("Logging Step: {0}")
    private void logStep(String step) {
        LoggerUtil.info(step);
    }

    @Attachment(value = "Response", type = "application/json")
    private String logResponse(Response response) {
        return response.asString();
    }

}
