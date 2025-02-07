package core;

import io.restassured.RestAssured;
import io.restassured.response.Response;

/**
 * This class provides a simple, reusable API client using RestAssured to perform HTTP operations such as GET, POST, PUT, PATCH, and DELETE.
 * Here we set the base URL "https://jsonplaceholder.typicode.com" and include methods to send requests.
 * */
public class ApiClient {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static Response get(String endpoint) {
        return RestAssured.given().baseUri(BASE_URL).get(endpoint);
    }

    public static Response post(String endpoint, Object body) {
        return RestAssured.given().baseUri(BASE_URL).contentType("application/json").body(body).post(endpoint);
    }

    public static Response put(String endpoint, Object body) {
        return RestAssured.given().baseUri(BASE_URL).contentType("application/json").body(body).put(endpoint);
    }

    public static Response patch(String endpoint, Object body) {
        return RestAssured.given().baseUri(BASE_URL).contentType("application/json").body(body).patch(endpoint);
    }

    public static Response delete(String endpoint) {
        return RestAssured.given().baseUri(BASE_URL).delete(endpoint);
    }
}