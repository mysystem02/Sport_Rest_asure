package base;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import utils.ConfigReader;

import static io.restassured.RestAssured.given;

public class BaseTest {

    // Fetch the base URL from config.properties
    protected static final String BASE_URL = ConfigReader.getProperty("base_url");

    // Method to get authentication token using credentials from properties file
    public static String getAuthToken() {
        String requestBody = "{\n" +
                "    \"email\": \"" + ConfigReader.getProperty("email") + "\",\n" +
                "    \"password\": \"" + ConfigReader.getProperty("password") + "\",\n" +
                "    \"role\": \"" + ConfigReader.getProperty("role") + "\"\n" +
                "}";

        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("User-Agent", "RestAssured/Java")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(BASE_URL + "/auth/login");

        // Print full response for debugging
//        System.out.println("Login Response Status Code: " + response.getStatusCode());
//        System.out.println("Login Response Body: " + response.getBody().asString());

        // Check for status code
        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Login failed! Status Code: " + response.getStatusCode());
        }

        // Correct token extraction
        String token = response.jsonPath().getString("data.token");

        Assert.assertNotNull(token, "Token should not be null");

        return token;
    }

    // Reusable method to send a GET request with authentication
    public static Response sendGetRequest(String endpoint, String token) {
        return given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")  // Ensure API accepts JSON response
                .header("User-Agent", "RestAssured")   // Some APIs require User-Agent
                .when()
                .get(BASE_URL + endpoint)
                .then()
                .log().all()  // Log request & response
                .extract()
                .response();
    }

    public static Response sendPostRequest(String endpoint, String token, String body) {
        RequestSpecification request = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")  // Set content type
                .header("User-Agent", "RestAssured")
                .body(body);

        // Only add Authorization header if the token is not null
        if (token != null && !token.isEmpty()) {
            request.header("Authorization", "Bearer " + token);
        } else {
            System.out.println("⚠️ Warning: Token is null or empty. API may return 401 Unauthorized.");
        }

        return request.when()
                .post(BASE_URL + endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }
}
