package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.ConfigReader;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class UserTests extends BaseTest {

    @Test
    public void testUserRegistration() {
        String requestBody = "{\n" +
                "    \"name\": \"Arif Test\",\n" +
                "    \"email\": \"test@yopmail.com\",\n" +
                "    \"password\": \"Test@321\",\n" +
                "    \"role\": { \"id\": 3 },\n" +
                "    \"dateOfBirth\": \"1998-10-01\",\n" +
                "    \"sports\": [{ \"id\": 2 }, {\"id\":3}, {\"id\":4}, {\"id\":5}],\n" +
                "    \"location\": \"kolkata\",\n" +
                "    \"latitude\": \"19.451054\",\n" +
                "    \"longitude\": \"79.748037\",\n" +
                "    \"about\": \"test test test\",\n" +
                "    \"imageLink\": \"https://www.jaisamand.co.in/Content/UserMenu/blogs/cricket/virat-kohli-cricket-photo/\"\n" +
                "}";

        Response response = sendPostRequest("/auth/register", null, requestBody);
        response.then().statusCode(201);

        String responseBody = response.getBody().asString();
        System.out.println("User Registration Response: " + responseBody);
    }

    @Test
    public void testGetRefereeDetails() {
        // Get authentication token dynamically
        String token = getAuthToken();

        // Fetch referee details
        String refereeId = "253";  // This can be fetched dynamically if needed
        Response response = sendGetRequest("/user/referee/" + refereeId, token);

        // Validate response
        response.then().statusCode(200);
        assertNotNull(response.getBody().asString(), "Response body should not be null");

        System.out.println("Referee Details: " + response.getBody().asString());
    }

    @Test
    public void testUserLogin() {
        Response response = sendPostRequest("/auth/login", null,
                "{\n" +
                        "    \"email\": \"" + ConfigReader.getProperty("remail") + "\",\n" +
                        "    \"password\": \"" + ConfigReader.getProperty("rpassword") + "\",\n" +
                        "    \"role\": \"" + ConfigReader.getProperty("role") + "\"\n" +
                        "}");

        // Validate response
        response.then().statusCode(200);
        String token = response.jsonPath().getString("token");
        assertNotNull(token, "Auth token should not be null");

        System.out.println("User Login Token: " + token);
    }
}
