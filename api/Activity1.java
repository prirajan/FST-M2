package Activites;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity1 {
    String BaseURI = "https://petstore.swagger.io/v2/pet";

    @Test(priority = 1)
    public void addPet(){
        //creating JSON request
        String reqbody ="{\"id\": 78899, \"name\": \"Ramu\", \"status\": \"alive\"}";

        Response response =
                //Set headers
                given().contentType(ContentType.JSON)
                //add reqbody
                .body(reqbody)
                //send POST request
                .when().post(BaseURI);
        //Assertion
        response.then().body("id", equalTo(78899));
        response.then().body("name", equalTo("Ramu"));
        response.then().body("status",equalTo("alive"));

    }
    @Test(priority=2)
    public void GetPet(){
        Response response =
                given().contentType(ContentType.JSON)
                .when().pathParam("petId","78899")
                .get(BaseURI + "/{petId}");
        //Assertion
        response.then().body("id", equalTo(78899));
        response.then().body("name", equalTo("Ramu"));
        response.then().body("status",equalTo("alive"));

    }
    @Test(priority=3)
    public void DeletePet(){
        Response response =
                given().contentType(ContentType.JSON)
                .when().pathParam("petId","78899")
                 .delete(BaseURI + "/{petId}");
        //Assertion
        response.then().body("code", equalTo(200));
        response.then().body("message",equalTo("78899"));
    }


}
