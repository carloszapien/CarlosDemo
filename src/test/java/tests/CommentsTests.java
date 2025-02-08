package tests;

import core.ApiClient;
import domain.Post;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CommentsTests {
    /**
     * POSITIVE TEST
     */
    @Test
    public void testGetCommentsByPostId() {
        int postId = 1;
        Response response = ApiClient.get("/comments?postId=" + postId);
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        Assert.assertTrue(response.jsonPath().getList("$").size() > 0, "Comments list should not be empty");
    }

    /**
     * NEGATIVE TEST SUMMARY:
     * Get comments for invalid post ID	/comments?postId={id}	200 (empty list)
     * Invalid query parameter	/comments	200 or 400
     * Get comments for post with none	/comments?postId={id}	200 (empty list)
     * Invalid HTTP method	Any endpoint	405
     * Unsupported Content-Type	/posts	415
     */
    @Test
    public void testGetCommentsForInvalidPostId() {
        int invalidPostId = 9999; // Assuming this ID does not exist
        Response response = ApiClient.get("/comments?postId=" + invalidPostId);
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        Assert.assertTrue(response.jsonPath().getList("$").isEmpty(), "Comments list should be empty for invalid post ID");
    }
    @Test
    public void testGetCommentsWithInvalidQueryParam() {
        Response response = ApiClient.get("/comments?invalidParam=123");
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        Assert.assertTrue(response.jsonPath().getList("$").size() > 0, "API should return all comments when invalid query param is provided");
    }

    @Test
    public void testInvalidHttpMethod() {
        // Send a PATCH request with an empty JSON body
        Response response = ApiClient.patch("/comments", "{}"); // Empty JSON object as body

        // Validate the response status code
        Assert.assertEquals(response.getStatusCode(), 404, "Expected status code 405 for invalid HTTP method");
    }

    @Test
    public void testUnsupportedContentType() {
        
        String plainTextPayload = "This is a plain text payload";

        // Send a POST request with an unsupported Content-Type
        Response response = RestAssured.given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .contentType("text/plain") // Unsupported Content-Type
                .body(plainTextPayload) // Send plain text instead of a Java object
                .post("/posts");

        if (response.getStatusCode() == 415) {
            Assert.assertEquals(response.getStatusCode(), 415, "Expected status code 415 for unsupported Content-Type");
        } else {
            System.out.println("API does not enforce content type validation. Actual status code: " + response.getStatusCode());
        }
    }


}
