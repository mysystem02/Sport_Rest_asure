package tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OTPVerificationTest {

    private static final String DB_URL = "jdbc:mysql://143.110.253.54:3306/sport_challenge";
    private static final String DB_USER = "developer";
    private static final String DB_PASSWORD = "2%z?yTd4s=LDuEm9G!nhUn6";

    public static String getOTPFromDatabase(String userEmail) {
        String otp = null;
        String query = "SELECT * FROM `user` WHERE `email` LIKE ? ORDER BY updated_at DESC LIMIT 1";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userEmail);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                otp = resultSet.getString("otp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return otp;
    }

    public static void main(String[] args) {
        String userEmail = "wasimahamedplyer@yopmail.com";
        String otp = getOTPFromDatabase(userEmail);

        if (otp != null) {
//            PlayerTests.testVerifyOTP(otp);
        } else {
            System.out.println("OTP not found in the database!");
        }
    }
}