package utils;

import java.sql.*;

public class DatabaseUtil {
    private static final String DB_URL = "jdbc:mysql://143.110.253.54:3306/sport_challenge";
    private static final String DB_USER = "developer";
    private static final String DB_PASSWORD = "2%z?yTd4s=LDuEm9G!nhUn6";

    public static String fetchDataFromDatabase(String query, String columnName, Object... params) {
        String result = null;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set dynamic parameters in the query
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]); // Set parameters dynamically
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            // Fetch first matching record
            if (resultSet.next()) {
                result = resultSet.getString(columnName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

//    public static void main(String[] args) {
//        // Example: Fetch OTP for a given email
//        String email = "wasimahamedplyer@yopmail.com";
//        String query = "SELECT * FROM `user` WHERE `email` = ? ORDER BY updated_at DESC LIMIT 1";
//        String otp = fetchDataFromDatabase(query, "otp", email);
//        System.out.println("✅ OTP: " + otp);
//
//        // Example: Fetch username for a given user ID
//        String userId = "12345";
//        String userQuery = "SELECT username FROM `user` WHERE `id` = ?";
//        String username = fetchDataFromDatabase(userQuery, "username", userId);
//        System.out.println("✅ Username: " + username);
//    }
}
