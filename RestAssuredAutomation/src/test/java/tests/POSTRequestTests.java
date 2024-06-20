package tests;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import test_utils.BaseTest;
import test_utils.CommonMethods;

@Feature("API POST Request Tests")
public class POSTRequestTests extends BaseTest {
    String projectId;
    @Test (description = "Creating a new project")
    @Description("This test will create a new Project")
    public void TestCreateAProject(){
        CommonMethods.LogStep("Starting POST request test");
        JSONObject payload = (JSONObject) requestParams.get("CreateProject");
        Response response = CommonMethods.PostRequest( requestParams.get("createproject_endpoint").toString(), authToken,payload);
        CommonMethods.LogResponse(response);

        // Verify the status code is 201 (Created)
        CommonMethods.VerifyStatusCode(response,200);

        CommonMethods.VerifyResponseSchema(response,"schemas/create-project-shema.json");
        String projectname = payload.get("name").toString();
        JsonPath jsonPath = response.jsonPath();
        projectId= jsonPath.getString("id");
        //Verify project name in response
        CommonMethods.ValidateJsonResponseKey(response,"name", projectname);
        CommonMethods.LogStep("POST request test passed");
    }

    @Test (description = "Delete project")
    @Description("This test will delete Project")
    public void TestDeleteProject(){
        CommonMethods.LogStep("Starting DELETE request test");
        Response response = CommonMethods.DeleteRequest( requestParams.get("deleteproject_endpoint").toString()+projectId, authToken);
        CommonMethods.LogResponse(response);

        // Verify the status code is 204 (Deleted)
        CommonMethods.VerifyStatusCode(response,204);
        CommonMethods.LogStep("POST request test passed");
    }

    @AfterMethod
    public void AfterMethod() {
        CommonMethods.AttachLog();
    }
}
