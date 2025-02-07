package tests;

import core.ApiClient;
import domain.Post;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PostsTests {
    //SUMMARY OF TEST:
    //testGetAllPosts: Verifies that the API returns a non-empty list of posts.
    //testGetPostById: Checks that the post with a specific ID can be retrieved and its ID matches.
    //testCreatePost:  Validates that a new post can be created and the title matches what was sent.
    //testDeletePost:  Verifies that a post can be deleted successfully by its ID.

    @Test
    public void testGetAllPosts() {
        Response response = ApiClient.getRequestSpecification()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .extract()
                .response();

        Assert.assertFalse(response.jsonPath().getList("").isEmpty(), "List of posts should not be empty");
    }

    @Test
    public void testGetPostById() {
        int postId = 1;
        Response response = ApiClient.getRequestSpecification()
                .when()
                .get("/posts/" + postId)
                .then()
                .statusCode(200)
                .extract()
                .response();

        Post post = response.as(Post.class);
        Assert.assertEquals(post.getId(), postId, "Post ID should match");
    }

    @Test
    public void testCreatePost() {
        Post newPost = new Post();
        newPost.setUserId(1);
        newPost.setTitle("Test Title");
        newPost.setBody("Test Body");

        Response response = ApiClient.getRequestSpecification()
                .body(newPost)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .extract()
                .response();

        Post createdPost = response.as(Post.class);
        Assert.assertEquals(createdPost.getTitle(), newPost.getTitle(), "Title should match");
    }

    @Test
    public void testDeletePost() {
        int postId = 1;
        ApiClient.getRequestSpecification()
                .when()
                .delete("/posts/" + postId)
                .then()
                .statusCode(200);
    }
}