package Activites;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class Activity3 {
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    int petId;

    @BeforeClass
    public void setUp(){
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io/v2/pet")
                .setContentType(ContentType.JSON)
                .build();
        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType("application/json")
                .expectBody("status",equalTo("alive"))
                .build();


    }
    @Test(priority = 1)
    public void addPets(){
            String reqBody = "{\"id\": 78886, \"name\": \"puppy\", \"status\": \"alive\"}";
        //Generate response
        Response response = given().spec(requestSpec)
                .body(reqBody)
                .when().post();
        //add 2nd pet
        reqBody = "{\"id\": 78887, \"name\": \"Tommy\", \"status\": \"alive\"}";
         response = given().spec(requestSpec)
                .body(reqBody)
                .when().post();
        //Assertions
        response.then().spec(responseSpec);
    }


    @DataProvider
    public Object [][] petDetailsProvider(){
        Object[][] testdata = new Object[][]{
                {78886,"puppy","alive"},
                {78887,"Tommy","alive"}

                };
        return testdata;
        }


    @Test(dataProvider = "petDetailsProvider",priority = 2)
    public void getPets(int id, String name, String status ){
        Response response = given().spec(requestSpec)
                .pathParam("petId",id)
                .when().get("/{petId}");
        System.out.println(response.getBody().asString());
        //assertion
        response.then().spec(responseSpec)
                .body("name",equalTo(name));
    }


    @Test(dataProvider = "petDetailsProvider",priority = 3)
    public void deletePets(int id,String name, String status) {
        Response response = given().spec(requestSpec)
                .pathParam("petId",id)
                .when().delete("/{petId}");
        //Print response
        System.out.println(response.getBody().asString());
        //Assertion
        response.then().body("code",equalTo(200));

    }



    }
