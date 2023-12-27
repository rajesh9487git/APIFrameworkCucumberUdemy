package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.TestDataBuild;
import resources.Utils;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

public class EcomStepDefinition extends Utils{
	
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	String token;
	RequestSpecification res1;
	String userId;
	String productId;
	
	
	@Given("login with valid details using {string} as the resource and {string} call")
	public void login_with_valid_details_using_as_the_resource_and_call(String string, String string2) throws IOException {
	  
		res= given().spec(requestSpecificationEcom())
				.body(data.loginPayload());
		
		response=res.when().post("/api/ecom/auth/login");
		assertEquals(response.getStatusCode(), 200);
		
		token=getJsonPath(response, "token");
		userId=getJsonPath(response, "userId");
		
		System.out.println("The token is ===============");
		
		System.out.println(token);
		
	}

	@When("creating the product using {string} and {string} call")
	public void creating_the_product_using_and_call(String string, String string2) throws IOException {
	 
//		res1= new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl")).addHeader("authorization", token).build();
//		
//		RequestSpecification addProduct=  given().log().all().spec(res1).param("productName", "Laptop")
//		.param("productAddedBy", userId).param("productCategory", "fashion")
//		.param("productSubCategory", "shirts").param("productPrice", "11500")
//		.param("productDescription", "Lenova").param("productFor", "men")
//		.multiPart("productImage", new File("D:\\Image\\LaptopImage.jpeg"));
		
		res=given().log().all().spec(requestSpecificationEcom1(token)).param("productName", "Laptop")
		.param("productAddedBy", userId).param("productCategory", "fashion")
		.param("productSubCategory", "shirts").param("productPrice", "11500")
		.param("productDescription", "Lenova").param("productFor", "men")
		.multiPart("productImage", new File("D:\\Image\\LaptopImage.jpeg"));
		
		
		
		Response addProductResponse=res.when().post("/api/ecom/product/add-product").then().log().all().assertThat().statusCode(201).extract().response();
		
		productId=getJsonPath(addProductResponse, "productId");
		
		System.out.println("the product id is =========="+productId);
		
	}

	@Then("verify product id using {string} as resource and {string} call")
	public void verify_product_id_using_as_resource_and_call(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("create order using {string} and {string} call")
	public void create_order_using_and_call(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("delete order using {string} and {string} call")
	public void delete_order_using_and_call(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}


	

}
