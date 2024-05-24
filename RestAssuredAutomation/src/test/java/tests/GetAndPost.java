package tests;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.ExtentReportManager;
import org.testng.Assert;
import io.restassured.path.json.JsonPath;
import utils.TestDataUtil;

public class GetAndPost {
	ExtentTest test;

    @BeforeClass
    public void setUp() {
        ExtentReportManager.initializeReport();
    }

	@Test
	public void testGet() {
		test = ExtentReportManager.createTest("getUsersTest");
        test.info("Starting GET request to /users endpoint");
		Response response = RestAssured.get("https://reqres.in/api/users?page=2");
		test.info("Response received");
		JsonPath jsonPath = response.jsonPath();
        String firstName = jsonPath.getString("data[0].first_name");
        response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/user-page-2-schema.json")).statusCode(200);
		Assert.assertEquals(firstName, "Michael");
		Assert.assertEquals(response.getStatusCode(), 200);
        test.info("Response: " + response.asString());
        test.pass("Verified the status code is 200");
	}
	
	@Test
	public void testPost() {
		
		// Read the test data from the JSON file
        JSONObject requestParams = TestDataUtil.readTestData("test-data/user.json");

        // Log the request payload
        test.info("Request payload: " + requestParams.toJSONString());
		
		// Perform the POST request
        Response response = given()
            .header("Content-Type", "application/json")
            .body(requestParams.toJSONString())
            .post("https://reqres.in/api/users");
        // Log the response received
        test.info("Response received");
        test.info("Response: " + response.asString());

        // Verify the status code is 201 (Created)
        Assert.assertEquals(response.getStatusCode(), 201);
        
        
        // Extract and verify specific fields from the response
        JsonPath jsonPath = response.jsonPath();
        String name = jsonPath.getString("name");
        String job = jsonPath.getString("job");
       
        Assert.assertEquals(name, requestParams.get("name"));
        Assert.assertEquals(job, requestParams.get("job"));
        test.pass("Verified the status code is 201"); 
		
	}
	
	@AfterClass
    public void tearDown() {
        ExtentReportManager.flushReport();
    }
}
