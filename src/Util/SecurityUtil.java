package com.gymsystem.account.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * SecurityUtil - Tiện ích bảo mật
 * Cung cấp các phương thức:
 * - Hash password (SHA-256 + salt)
 * - Verify password
 * - Generate salt
 * - Input sanitization
 * 
 * LƯU Ý: Trong sản xuất nên dùng BCrypt thay vì SHA-256
 * SHA-256 là ví dụ giáo dục, không nên dùng cho mật khẩu
 */
public class SecurityUtil {

    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 10000;

    /**
     * Hash password với salt
     * Format: salt:hash
     */
    public static String hashPassword(String password) {
        try {
            // Generate salt ngẫu nhiên
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);

            // Hash password với salt
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());

            // Encode salt + hash thành Base64
            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashBase64 = Base64.getEncoder().encodeToString(hashedPassword);

            return saltBase64 + ":" + hashBase64;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Verify password
     */
    public static boolean verifyPassword(String password, String storedHash) {
        try {
            // Split salt và hash
            String[] parts = storedHash.split(":");
            if (parts.length != 2) {
                return false;
            }

            byte[] salt = Base64.getDecoder().decode(parts[0]);

            // Hash password với salt
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            String hashBase64 = Base64.getEncoder().encodeToString(hashedPassword);

            // Compare
            return hashBase64.equals(parts[1]);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sanitize input để tránh SQL injection
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        
        // Escape special SQL characters
        return input.replace("'", "''")
                   .replace("\"", "\"\"")
                   .replace("\\", "\\\\");
    }

    /**
     * Validate email format
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    /**
     * Generate random token (cho reset password, verify email)
     */
    public static String generateRandomToken(int length) {
        SecureRandom random = new SecureRandom();
        byte[] tokenBytes = new byte[length];
        random.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

    /**
     * Generate OTP
     */
    public static String generateOTP(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}
