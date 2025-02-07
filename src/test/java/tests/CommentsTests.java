package tests;

import core.ApiClient;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CommentsTests {
    //The goal of this test is to check that there are comments associated with the post with postId = 1.
    //Summary:
    //The test sends a GET request to /comments?postId=1, fetching all comments for post ID 1.
    //It expects the API to return a 200 OK status code.
    //It then checks that the list of comments in the response is not empty by using an assertion.
    //If there are no comments for the given postId, the test will fail and show the message "List of comments should not be empty".
    @Test
    public void testGetCommentsByPostId() {
        int postId = 1;
        Response response = ApiClient.getRequestSpecification()
                .when()
                .get("/comments?postId=" + postId)
                .then()
                .statusCode(200)
                .extract()
                .response();

        Assert.assertFalse(response.jsonPath().getList("").isEmpty(), "List of comments should not be empty");
    }
}