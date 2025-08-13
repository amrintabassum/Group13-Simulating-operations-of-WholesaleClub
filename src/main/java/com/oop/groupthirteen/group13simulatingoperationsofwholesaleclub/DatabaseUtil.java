package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DatabaseUtil {
    private static final String DB_URL = "jdbc:sqlite:users.db";

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String createUsersTable = """
                    CREATE TABLE IF NOT EXISTS users (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        full_name TEXT NOT NULL,
                        id_number TEXT UNIQUE NOT NULL,
                        user_type TEXT NOT NULL,
                        password_hash TEXT NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    )
                    """;

            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createUsersTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean registerUser(String fullName, String idNumber, String userType, String password) {
        String insertUser = "INSERT INTO users (full_name, id_number, user_type, password_hash) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertUser)) {

            pstmt.setString(1, fullName);
            pstmt.setString(2, idNumber);
            pstmt.setString(3, userType);
            pstmt.setString(4, hashPassword(password));

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static User authenticateUser(String idNumber, String userType, String password) {
        String selectUser = "SELECT * FROM users WHERE id_number = ? AND user_type = ? AND password_hash = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(selectUser)) {

            pstmt.setString(1, idNumber);
            pstmt.setString(2, userType);
            pstmt.setString(3, hashPassword(password));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("full_name"),
                            rs.getString("id_number"),
                            rs.getString("user_type")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean userExists(String idNumber) {
        String selectUser = "SELECT COUNT(*) FROM users WHERE id_number = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(selectUser)) {

            pstmt.setString(1, idNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    public static class User {
        private final int id;
        private final String fullName;
        private final String idNumber;
        private final String userType;

        public User(int id, String fullName, String idNumber, String userType) {
            this.id = id;
            this.fullName = fullName;
            this.idNumber = idNumber;
            this.userType = userType;
        }

        public int getId() {
            return id;
        }

        public String getFullName() {
            return fullName;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public String getUserType() {
            return userType;
        }
    }
}