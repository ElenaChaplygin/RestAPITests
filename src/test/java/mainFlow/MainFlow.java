package mainFlow;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class MainFlow {
	Response resp = null;
	String userId;
	String token;
	Properties prop = new Properties();
  
	@BeforeTest
	public void getProperties() throws IOException{
		FileInputStream fis = new FileInputStream("C:\\Users\\Elena Chaplygina\\automation\\RestApiTests\\src\\test\\resources\\properties\\env.properties");
		prop.load(fis);
		
	}
	
	@BeforeMethod
	public void updateValues(){
		
	}
	
	@Test(priority = 1)
	public void doLogin() {
		RestAssured.baseURI = prop.getProperty("HOST");
		resp = given().header("Authorization", "Basic YWRtaW46YWRtaW4=").when().get(sources.getRequestLogin());
		resp.then().assertThat().contentType(ContentType.JSON).extract().response();
		JsonPath json = ReusableMethods.dataToJson(resp);
		userId = json.get("userId");
		token = resp.getHeader("x-access-token");
		System.out.println(userId + "...." + token);
	}

	@Test (priority=2)
	public void adTrip() throws IOException{
		RestAssured.baseURI = prop.getProperty("HOST");
		String request = ReusableMethods.generateStringFromResource("src\\test\\resources\\properties\\body.json");
		resp = given().header("x-access-token",token).
				header("Content-Type","application/json").
				body(request).
				when().put("/trip");				
		resp.then().assertThat().statusCode(200);
		resp.then().extract().response();
		JsonPath j = ReusableMethods.dataToJson(resp); 
		String validation = j.get("destination");
		Assert.assertEquals(validation, "Berlin");
		
		
	}
}
