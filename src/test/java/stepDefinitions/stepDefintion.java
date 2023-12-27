package stepDefinitions;
import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import static org.junit.Assert.*;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class stepDefintion extends Utils{
	
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	static String place_id;
	
	@Given("Add place payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
	
		 res=given().spec(requestSpecification())
		.body(data.addPlacePayload(name,language,address));
		
	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
	    
		
		APIResources resourceAPI=  APIResources.valueOf(resource);
		resspec= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if(method.equalsIgnoreCase("POST"))
			response=res.when().post(resourceAPI.getResource());
		else if(method.equalsIgnoreCase("GET"))
			response=res.when().get(resourceAPI.getResource());
		
		
	}

	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
	    assertEquals(response.getStatusCode(),200);
	}

	@And("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String Expectedvalue) {
	  
		assertEquals(getJsonPath(response, keyValue),Expectedvalue );
	}

	@And("verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
	   
		
		place_id=getJsonPath(response,"place_id");
		res=given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_http_request(resource, "GET");
		
		System.out.println("the response is =============="+ response);
		
		String actualName = getJsonPath(response, "name");
		
		System.out.println("the Actual name is "+ actualName);
		assertEquals(actualName,expectedName);
		
		
	}
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
		
		res= given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
	  
		
	}
	
	@Then("validate the json schema")
	public void validate_the_json_schema() throws IOException {
		
//		String response1=response.asString();
//		
//		System.out.println("===========================================================");
//		System.out.println("the response is "+response1);
//		
//		Assert.assertEquals(response1, matchesJsonSchemaInClasspath("D:\\APIFramework\\src\\test\\java\\resources\\products-schema.json") );\
		
		res=given().spec(requestSpecification()).queryParam("place_id", place_id);
		res.when().get("/maps/api/place/get/json").then().assertThat().body(matchesJsonSchemaInClasspath("products-schema.json"));

		
	}



}
