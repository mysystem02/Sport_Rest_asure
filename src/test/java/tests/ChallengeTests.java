package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class ChallengeTests extends BaseTest {

    @Test
    public void testCreateChallenge() {
        String token = getAuthToken();
        String requestBody = "{\n" +
                "    \"opponent\": { \"id\": 112 },\n" +
                "    \"sports\": { \"id\": 2 },\n" +
                "    \"type\": \"waged\",\n" +
                "    \"date\": \"2025-02-05\",\n" +
                "    \"location\": \"kolkata\",\n" +
                "    \"latitude\": \"19.451054\",\n" +
                "    \"longitude\": \"79.748037\",\n" +
                "    \"price\": 90,\n" +
                "    \"city\": \"kolkata\"\n" +
                "}";

        Response response = sendPostRequest("/challenge/create", token, requestBody);
        response.then().statusCode(201);
        System.out.println("Create Challenge Response: " + response.getBody().asString());
    }

    @Test
    public void testGetChallenges() {
        String token = getAuthToken();
        Response response = sendGetRequest("/challenge/list", token);
        response.then().statusCode(200);
        System.out.println("Challenges List: " + response.getBody().asString());
    }
}
