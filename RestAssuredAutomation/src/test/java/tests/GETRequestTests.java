package tests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import test_utils.BaseTest;
import test_utils.CommonMethods;
import io.qameta.allure.Feature;
import io.qameta.allure.Description;

@Feature("ProjX API GET Request Tests")
public class GETRequestTests extends BaseTest{

    @Test(description = "Get Tenant Request")
    @Description("This test will verify all tenants")
    public void TestTenants() {
        CommonMethods.LogStep("Starting Tenants request test");
        Response response = CommonMethods.GetRequest(requestParams.get("tenants_endpoint").toString(), authToken);
        CommonMethods.LogResponse(response);
        CommonMethods.VerifyStatusCode(response,200);
        CommonMethods.VerifyResponseSchema(response,"schemas/tenants-schema.json");
        CommonMethods.LogStep("Tenants request test passed");
    }

    @Test(description = "Get Notifications Count Request")
    @Description("This test will get Notifications Count")
    public void TestNotificationsCount() {
        CommonMethods.LogStep("Starting Notifications count request test");
        Response response = CommonMethods.GetRequest(requestParams.get("notificationsCount_endpoint").toString(), authToken);
        CommonMethods.LogResponse(response);
        CommonMethods.VerifyStatusCode(response,200);
        CommonMethods.VerifyResponseSchema(response,"schemas/notifications-count-schema.json");
        CommonMethods.LogStep("Notifications Count request test passed");
    }

    @Test(description = "Get Auth-Service Me Request")
    @Description("This test will get Auth-service Me details")
    public void TestAuthServiceMe() {
        CommonMethods.LogStep("Starting Auth-Service Me request test");
        Response response = CommonMethods.GetRequest(requestParams.get("authServiceMe_endpoint").toString(), authToken);
        CommonMethods.LogResponse(response);
        CommonMethods.VerifyStatusCode(response,200);
        CommonMethods.LogStep("Auth-Service Me request test passed");
    }

    @AfterMethod
    public void AfterMethod() {
        CommonMethods.AttachLog();
    }

}
