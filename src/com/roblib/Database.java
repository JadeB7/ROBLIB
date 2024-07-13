package com.roblib;

import java.sql.*;

public class Database {

        private static final String DB_URL = "jdbc:mysql://sql5.freesqldatabase.com:3306/sql5670955";
        private static final String DB_USER = "sql5670955";
        private static final String DB_PASSWORD = "IWVruy5Li1";
    public class UserRegistrationException extends Exception {
        public UserRegistrationException(String message) {
            super(message);
        }
    }

    public void saveUserWithExceptionHandling(String username, String password, String email, String gender, String birthdate, String cin, byte[] image) throws UserRegistrationException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO ROBLIB (username, password, email, gender, birthdate, cin, image) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.setString(4, gender);
            pstmt.setDate(5, Date.valueOf(birthdate));
            pstmt.setString(6, cin);
            pstmt.setBytes(7, image);
            pstmt.executeUpdate();


        } catch (SQLIntegrityConstraintViolationException e) {
            if (e.getErrorCode() == 1062) {
                String errorMessage = e.getMessage();
                if (errorMessage.contains("ROBLIB.username")) {
                    throw new UserRegistrationException("Username is already in use.");
                } else if (errorMessage.contains("ROBLIB.email")) {
                    throw new UserRegistrationException("Email is already in use.");
                } else if (errorMessage.contains("ROBLIB.cin")) {
                    throw new UserRegistrationException("CIN is already in use.");
                } else {
                    e.printStackTrace();
                    throw new UserRegistrationException("An error occurred. Please try again later.");
                }
            } else {
                e.printStackTrace();
                throw new UserRegistrationException("An error occurred. Please try again later.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserRegistrationException("An error occurred. Please try again later.");
        }
    }
    public void updatePasswordByEmail(String email, String newPassword) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE ROBLIB SET password = ? WHERE email = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, newPassword);
                pstmt.setString(2, email);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    public String getEmailByUsername(String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT email FROM ROBLIB WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("email");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception or handle it as needed
        }
        return ""; // Return an empty string or another default value
    }

    public byte[] getUserImageBytesByUsername(String username) {
        try {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "SELECT image FROM ROBLIB WHERE username = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, username);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            return rs.getBytes("image");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log or handle the exception as needed
        }
        return null;
    }

    public String getGenderByUsername(String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT gender FROM ROBLIB WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("gender");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving gender", e);
        }
        return null;
    }

    public String getBirthdateByUsername(String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT birthdate FROM ROBLIB WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("birthdate");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving birthdate", e);
        }
        return null;
    }

    public String getCinByUsername(String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT cin FROM ROBLIB WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("cin");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving CIN", e);
        }
        return null;
    }

    public String getPasswordByUsername(String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT password FROM ROBLIB WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("password");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving password", e);
        }
        return null;
    }

    public void updateUserImage(String username, byte[] newImageBytes) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE ROBLIB SET image = ? WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setBytes(1, newImageBytes);
                pstmt.setString(2, username);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception or handle it as needed
        }
    }
    public int getUserIdByUsername(String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT id FROM ROBLIB WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
        return -1; // Return a default value or handle the case when the user ID is not found
    }
}
