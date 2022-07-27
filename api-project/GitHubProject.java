package LiveProject;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;



import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GitHubProject {
    RequestSpecification requestSpec;
    String SSHkey;
    int Id;

    @BeforeClass
    public void setUp() {
        SSHkey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCIHaKcUFjM4K6KiOC+mWJ2PyUYXm13AehrDtYPUTgndSlyBsu0m4Gm/9tLXD2eKnL6436dXrEXSWNPMVIgC/KYkKm/h/HPYoB0I08tTskZaAlWt2KLbTbHXMBqxYS6oH0K/mleKbYmARAZxl00KPIv9ul1ZSN1PT4W/lohoKFvXDacmKUmvxafSlt0yygrqODD2FcWeO9xTrUmxmHhzkoi9jGWG6iSmiK/un4sc/P4atDHpST9WmC3C0hnyC95VZNg8/qYWJQULVLkYAU4ucXvm45nuvQ6/6EEZmg7o2upCfabZ4gZ+WTsAl+nlEt4Lu41OI850K5cAGKom7oy0Hwx";
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://api.github.com")
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "token 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")
                .build();
    }


    @Test(priority = 1)
    public void POST() {
        String reqBody = "{\"title\":\"TestAPIKey\",\"key\": \"" + SSHkey + "\"}";
        //Generate response
        Response response = given().spec(requestSpec)
                .body(reqBody)
                .when().post("/user/keys");
        System.out.println(response.getBody().asString());
        Id = response.then().extract().path("id");
        System.out.println("id : " + Id);
        //Assertions
        response.then().statusCode(201);

    }

    @Test(priority = 2)
    public void GET() {
        Response response = given().spec(requestSpec)
                .pathParam("keyId", Id)
                .when().get("/user/keys/{keyId}");
        System.out.println(response.getBody().asString());
        response.then().statusCode(200);

    }

    @Test(priority = 3)
    public void DELETE() {
        Response response = given().spec(requestSpec)
                .pathParam("keyId", Id)
                .when().delete("/user/keys/{keyId}");;
        //Print response
        System.out.println(response.getBody().asString());
        response.then().statusCode(204);

    }
}