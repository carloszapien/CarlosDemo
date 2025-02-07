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
        Response response = ApiClient.patch("/comments", null); // PATCH is not supported for /comments
        Assert.assertEquals(response.getStatusCode(), 405, "Expected status code 405 for invalid HTTP method");
    }

    @Test
    public void testUnsupportedContentType() {
        Post newPost = new Post(1, 101, "Test Title", "Test Body");
        Response response = RestAssured.given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .contentType("text/plain") // Unsupported Content-Type
                .body(newPost)
                .post("/posts");
        Assert.assertEquals(response.getStatusCode(), 415, "Expected status code 415 for unsupported Content-Type");
    }


}