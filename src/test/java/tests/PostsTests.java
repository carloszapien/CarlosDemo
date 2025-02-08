package tests;

import core.ApiClient;
import domain.Post;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PostsTests {
    /**SUMMARY OF POSITIVE TESTS:
    testGetAllPosts: Verifies that the API returns a non-empty list of posts.
    testGetPostById: Checks that the post with a specific ID can be retrieved and its ID matches.
    testCreatePost:  Validates that a new post can be created and the title matches what was sent.
     testUpdatePost: Validates that a post can be updated.
    testDeletePost:  Verifies that a post can be deleted successfully by its ID.
    */
    @Test
    public void testGetAllPosts() {
        Response response = ApiClient.get("/posts");
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        Assert.assertFalse(response.jsonPath().getList("$").isEmpty(), "Posts list should not be empty");
    }

    @Test
    public void testGetPostById() {
        int postId = 1;
        Response response = ApiClient.get("/posts/" + postId);
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        Assert.assertEquals(response.jsonPath().getInt("id"), postId, "Post ID should match");
    }

    @Test
    public void testCreatePost() {
        Post newPost = new Post(1, 101, "Test Title", "Test Body");
        Response response = ApiClient.post("/posts", newPost);
        Assert.assertEquals(response.getStatusCode(), 201, "Expected status code 201");
        Assert.assertEquals(response.jsonPath().getString("title"), "Test Title", "Title should match");
    }

    @Test
    public void testUpdatePost() {
        Post updatedPost = new Post(1, 1, "Updated Title", "Updated Body");
        Response response = ApiClient.put("/posts/1", updatedPost);
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        Assert.assertEquals(response.jsonPath().getString("title"), "Updated Title", "Title should be updated");
    }

    @Test
    public void testDeletePost() {
        Response response = ApiClient.delete("/posts/1");
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

    /**
     * SUMMARY OF NEGATIVE TESTS
     * testGetPostByInvalidId:            Verifies the behavior of the API when attempting to retrieve a post by an invalid ID (in this case, 9999).
     * testCreatePostWithMissingFields:   Verifies that the API correctly handles an invalid request to create a new post when the request is missing required fields.
     * testUpdatePostWithInvalidId:       Verifies that the API correctly handles an invalid request to update a post with an ID that does not exist.
     * testCreatePostWithInvalidDataTypes:Verifies that the API correctly hanfles an invalid request reate a post with invalid data types (e.g., passing a string for userId instead of an integer).
     *
     */
    @Test
    public void testGetPostByInvalidId() {
        int invalidPostId = 9999; // Assuming this ID does not exist
        Response response = ApiClient.get("/posts/" + invalidPostId);
        Assert.assertEquals(response.getStatusCode(), 404, "Expected status code 404 for invalid post ID");
    }
    @Test
    public void testCreatePostWithMissingFields() {
        // Create a Post object with missing fields
        Post invalidPost = new Post(0, 0, null, null); // Missing required fields

        // Send a POST request
        Response response = ApiClient.post("/posts", invalidPost);

        // Validate the response status code
        Assert.assertEquals(response.getStatusCode(), 201, "Expected status code 201 for missing fields");

        // Validate that the response contains the expected fields
        Assert.assertNotNull(response.jsonPath().getString("id"), "ID should not be null");
        Assert.assertNull(response.jsonPath().getString("title"), "Title should be null");
        Assert.assertNull(response.jsonPath().getString("body"), "Body should be null");
    }
    @Test
    public void testUpdatePostWithInvalidId() {
        Post updatedPost = new Post(1, 9999, "Updated Title", "Updated Body"); // Invalid post ID
        Response response = ApiClient.put("/posts/9999", updatedPost);
        Assert.assertEquals(response.getStatusCode(), 500, "Expected status code 500 for invalid post ID");
    }
    @Test
    public void testCreatePostWithInvalidDataTypes() {
        // Create a JSON payload with invalid data types
        String invalidPayload = "{ \"userId\": \"invalid\", \"id\": 101, \"title\": 123, \"body\": true }";

        // Send a POST request
        Response response = ApiClient.post("/posts", invalidPayload);

        // Validate the response status code
        Assert.assertEquals(response.getStatusCode(), 201, "Expected status code 201 for invalid data types");
        Assert.assertNotNull(response.jsonPath().getString("id"), "ID should not be null");
        Assert.assertNotNull(response.jsonPath().getString("title"), "Title should not be null");
        Assert.assertNotNull(response.jsonPath().getString("body"), "Body should not be null");
    }
}
