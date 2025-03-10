package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class PostTests extends BaseTest {

    @Test
    public void testCreatePost() {
        String token = getAuthToken();
        String requestBody = "{\n" +
                "    \"title\":\"Cricket image\",\n" +
                "    \"content\":\"https://images.pexels.com/photos/3628912/pexels-photo-3628912.jpeg\"\n" +
                "}";

        Response response = sendPostRequest("/user/post", token, requestBody);
        response.then().statusCode(200);
        System.out.println("Create Post Response: " + response.getBody().asString());
    }

    @Test
    public void testGetUserPosts() {
        String token = getAuthToken();
        Response response = sendGetRequest("/user/posts", token);
        response.then().statusCode(200);
        System.out.println("User Posts: " + response.getBody().asString());
    }
}
