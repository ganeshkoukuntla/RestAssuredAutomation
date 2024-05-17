package tests;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

public class GetAndPost {

	@Test
	public void testGet() {
		baseURI = "https://reqres.in/api";
		given().get("/users?page=2").then().statusCode(200).body("data[0].first_name", equalTo("Michael"));
	}
	
	@Test
	public void testPost() {
		JSONObject req = new JSONObject();
		req.put("name", "Ganesh");
		req.put("Job", "Employee");
		System.out.println(req.toJSONString());
		baseURI = "https://reqres.in/api";
		given().body(req.toJSONString()).when().post("/users").then().statusCode(201).log().all();
		
	}
}
