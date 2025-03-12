package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.DatabaseUtil;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class PlayerTests extends BaseTest {

    @Test
    public void testRegisterPlayer() {
        String requestBody = "{\n" +
                "    \"name\": \"Bret Lee\",\n" +
                "    \"email\": \"bretlee01@yopmail.com\",\n" +
                "    \"password\": \"Wasim@800\",\n" +
                "    \"confirmPassword\": \"Wasim@800\"\n" +
                "}";

        Response response = sendPostRequest("/auth/register/step1", null, requestBody);
        response.then().statusCode(201);
        System.out.println("Player Registration Response: " + response.getBody().asString());
    }

    @Test
    public static void testVerifyOTP() {
        String email = "wasimahamedplyer@yopmail.com";
        String query = "SELECT * FROM `user` WHERE `email` = ? ORDER BY updated_at DESC LIMIT 1";
        String otp = DatabaseUtil.fetchDataFromDatabase(query, "otp", email);
        String requestBody = "{ \"otp\":\""+otp+"\" }";
        Response response = sendPostRequest("/auth/verify", null, requestBody);
        response.then().statusCode(200);
        System.out.println("OTP Verification Response: " + response.getBody().asString());
    }

    @Test
    public void testGetUserDetails() {
        String token = getAuthToken();
        Response response = sendGetRequest("/user/details", token);
        response.then().statusCode(200);
        System.out.println("User Details: " + response.getBody().asString());
    }
}
