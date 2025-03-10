package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.ConfigReader;

import static org.testng.Assert.assertNotNull;

public class AuthTests extends BaseTest {

    @Test
    public void testLoginWithValidCredentials() {
        String token = getAuthToken();
        assertNotNull(token, "Auth token should not be null");
        System.out.println("Generated Token: " + token);
    }
}
