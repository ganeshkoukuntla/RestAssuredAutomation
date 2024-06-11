package tests;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;
import utils.commonMethods;
import utils.ConfigManager;
import utils.LoggerUtil;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.qameta.allure.Feature;
import io.qameta.allure.Description;

import java.nio.file.Files;
import java.nio.file.Paths;

@Feature("API GET Request Tests")
public class NewGET_Test
{
    private String baseUrl;
    private String authToken;

    @BeforeClass
    public void setUp() {
        baseUrl = ConfigManager.getProperty("base_url");
        logStep("Generating Auth token");
        authToken = commonMethods.generateAuthToken();;
        LoggerUtil.info("Setup complete.");
    }

//    @Test(description = "Users Get request")
//    @Description("this test will get page2 users list")
//    public void testGet() {
//        logStep("Starting GET request test");
//        Response response = commonMethods.getRequest(baseUrl, ConfigManager.getProperty("get_end_point"));
//        logResponse(response);
//        assertEquals(response.getStatusCode(), 200);
//        logStep("POST request test passed");
//    }

    @Test(description = "Tenantw Get request")
    @Description("this test will get all tenants")
    public void testTenants() {
        logStep("Starting Tenants request test");
        Response response = commonMethods.getRequest(baseUrl, ConfigManager.getProperty("tenants_endpoint"), authToken);
        logResponse(response);
        assertEquals(response.getStatusCode(), 200);
        assertThat(response.asString(), JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/tenants-schema.json"));
        logStep("Tenants request test passed");
    }

    @Test(description = "Notifications Count Get request")
    @Description("this test will get Notifications Count")
    public void testNotificationsCount() {
        logStep("Starting Notifications count request test");
        Response response = commonMethods.getRequest(baseUrl, ConfigManager.getProperty("notificationsCount_endpoint"), authToken);
        logResponse(response);
        assertEquals(response.getStatusCode(), 200);
        assertThat(response.asString(), JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/notifications-count-schema.json"));
        logStep("Notifications Count request test passed");
    }

    @Test(description = "Auth-Service Me Get request")
    @Description("this test will get Auth-service Me details")
    public void testAuthServiceMe() {
        logStep("Starting Auth-Service Me request test");
        Response response = commonMethods.getRequest(baseUrl, ConfigManager.getProperty("authServiceMe_endpoint"), authToken);
        logResponse(response);
        assertEquals(response.getStatusCode(), 200);
        logStep("Auth-Service Me request test passed");
    }

    @AfterMethod
    public void afterMethod() {
        attachLog();
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
    @Attachment(value = "Test Log", type = "text/plain")
    public byte[] attachLog() {
        try {
            return Files.readAllBytes(Paths.get("logs/test.log"));
        } catch (Exception e) {
            e.printStackTrace();
            return "Unable to attach log".getBytes();
        }
    }
}
