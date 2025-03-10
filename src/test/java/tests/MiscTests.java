package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class MiscTests extends BaseTest {

    @Test
    public void testGetUserGallery() {
        String token = getAuthToken();
        Response response = sendGetRequest("/user/gallery", token);
        assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void testSearchPlayer() {
        String token = getAuthToken();
        String requestBody = "{ \"name\": \"Virat Kohli\", \"city\": \"kolkata\", \"page\": 0, \"size\": 10 }";
        Response response = sendPostRequest("/user/search-user", token, requestBody);
        assertEquals(response.getStatusCode(), 200);
    }
}
