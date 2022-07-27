package Activites;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.attribute.FileAttributeView;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity2 {
    String BaseURI = "https://petstore.swagger.io/v2/user";

    @Test(priority=1)
    public void adduser() throws IOException {
        //import JSON File
        File file = new File("src/test/java/Activites/user.json");
        FileInputStream userJSON = new FileInputStream(file);
        //get all bytes from JSON file
        byte[] bytes= new byte[(int) file.length()];
        userJSON.read(bytes);
        String reqbody = new String(bytes, "UTF-8");
        System.out.println(reqbody);
        Response response =
                //Set headers
                given().contentType(ContentType.JSON)
                 //add reqbody
                .body(reqbody)
                  //send POST request
                .when().post(BaseURI);
        // Print response
        String body = response.getBody().asPrettyString();
        System.out.println(body);
        userJSON.close();
        // Assertion
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("1002"));
    }
    @Test(priority=2)
    public void getUser() throws IOException{
        File outputJSON= new File("src/test/java/Activites/output.json");


        Response response =
                //Set headers
                given().contentType(ContentType.JSON)
                  //get request body from file
                .pathParam("username","RamyaR")
                //Send GET request
                .when().get(BaseURI + "/{username}");
        //get response body
        String resbody = response.getBody().asPrettyString();
        try {
            //create JSON file
            outputJSON.createNewFile();
            FileWriter writer = new FileWriter(outputJSON.getPath());
            writer.write(resbody);
            writer.close();

            // Assertion
            response.then().body("id", equalTo(1002));
            response.then().body("username", equalTo("RamyaR"));
            response.then().body("firstName", equalTo("Ramya"));
            response.then().body("lastName", equalTo("ravi"));
            response.then().body("email", equalTo("Ragul@mail.com"));
            response.then().body("password", equalTo("password123"));
            response.then().body("phone", equalTo("9812763400"));

        }
        catch(IOException excp){
            excp.printStackTrace();
        }

    }
    @Test(priority=3)

        public void deleteUser() throws IOException
    {
        Response response =
                given().contentType(ContentType.JSON)
                 .pathParam("username","RamyaR")
                 .when().delete(BaseURI +"/{username}");
        //Assertion
        response.then().body("code",equalTo(200));
        response.then().body("message",equalTo("RamyaR"));

    }



    }

