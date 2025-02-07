package core;
//By Carlos Hernandez
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ApiClient {

    //This method prepares a basic configuration for making API requests with RestAssured:
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com"; //Setting base URL for the API setting the content type as JSON

    public static RequestSpecification getRequestSpecification() {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .log().all();
    }
}