package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

public class FirstTest {

	@Test
	public void test1() {
		
		Response response =	get("https://reqres.in/api/users?page=2");
		Assert.assertEquals(response.statusCode(), 200);
		
	}
	
	@Test
	public void test2() {
		baseURI = "https://reqres.in/api";
		given().get("/users?page=2").then().statusCode(200);
		
		
	}
	
}
