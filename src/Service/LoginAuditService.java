package com.gymsystem.account.service;

import Model.User;
import Dao.UserDao;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * LoginAuditService - Dịch vụ ghi nhật ký đăng nhập
 * Theo dõi:
 * - Lần đăng nhập cuối cùng
 * - Số lần thất bại
 * - IP address
 * - Thời gian
 * - Trạng thái khóa tài khoản
 * 
 * Dùng để:
 * - Phát hiện hoạt động bất thường
 * - Ngăn chặn brute force attack
 * - Audit compliance
 */
public class LoginAuditService {

    private static final int MAX_LOGIN_ATTEMPTS = 5;
    private static final int LOCKOUT_DURATION_MINUTES = 15;
    
    // In-memory store cho failed attempts (dùng Database trong production)
    private static Map<String, LoginAttempt> loginAttempts = new HashMap<>();

    /**
     * Ghi nhật ký đăng nhập thành công
     */
    public void logSuccessfulLogin(User user, String ipAddress) {
        try {
            // Reset failed attempts
            loginAttempts.remove(user.getEmail());

            // TODO: Save to database
            System.out.println("[LOGIN_SUCCESS] " + user.getEmail() + " from IP: " + ipAddress + " at " + LocalDateTime.now());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ghi nhật ký đăng nhập thất bại
     * Nếu vượt quá số lần cho phép, khóa tài khoản tạm thời
     */
    public LoginAttemptResult logFailedLogin(String email, String ipAddress) {
        try {
            LoginAttempt attempt = loginAttempts.getOrDefault(email, new LoginAttempt(email));
            
            // Check nếu tài khoản bị khóa
            if (attempt.isLocked()) {
                if (!attempt.isLockoutExpired()) {
                    return new LoginAttemptResult(false, 
                        "Tài khoản bị khóa tạm thời. Vui lòng thử lại sau " + 
                        attempt.getRemainingLockoutMinutes() + " phút");
                } else {
                    // Hết thời gian khóa, reset
                    attempt.reset();
                }
            }

            // Increment failed attempts
            attempt.incrementFailedAttempts();
            attempt.setLastAttemptIP(ipAddress);
            attempt.setLastAttemptTime(LocalDateTime.now());

            loginAttempts.put(email, attempt);

            System.out.println("[LOGIN_FAILED] " + email + " from IP: " + ipAddress + 
                             " Attempts: " + attempt.getFailedAttempts() + "/" + MAX_LOGIN_ATTEMPTS);

            // Check nếu vượt quá số lần
            if (attempt.getFailedAttempts() >= MAX_LOGIN_ATTEMPTS) {
                attempt.lock();
                System.out.println("[ACCOUNT_LOCKED] " + email + " due to multiple failed login attempts");
                return new LoginAttemptResult(false, 
                    "Tài khoản bị khóa vì quá nhiều lần đăng nhập thất bại. " +
                    "Vui lòng thử lại sau " + LOCKOUT_DURATION_MINUTES + " phút");
            }

            int remainingAttempts = MAX_LOGIN_ATTEMPTS - attempt.getFailedAttempts();
            return new LoginAttemptResult(false, 
                "Sai mật khẩu. Còn " + remainingAttempts + " lần thử");

        } catch (Exception e) {
            e.printStackTrace();
            return new LoginAttemptResult(false, "Lỗi khi ghi nhật ký đăng nhập");
        }
    }

    /**
     * Kiểm tra tài khoản có bị khóa không
     */
    public boolean isAccountLocked(String email) {
        LoginAttempt attempt = loginAttempts.get(email);
        if (attempt == null) {
            return false;
        }
        return attempt.isLocked() && !attempt.isLockoutExpired();
    }

    /**
     * Lấy thông tin đăng nhập cuối cùng
     */
    public LastLoginInfo getLastLoginInfo(User user) {
        // TODO: Query from database
        return new LastLoginInfo();
    }

    /**
     * Reset failed attempts cho email
     */
    public void resetFailedAttempts(String email) {
        loginAttempts.remove(email);
        System.out.println("[FAILED_ATTEMPTS_RESET] " + email);
    }

    // ==================== INNER CLASSES ====================

    /**
     * Thông tin một lần đăng nhập thất bại
     */
    public static class LoginAttempt {
        private String email;
        private int failedAttempts;
        private LocalDateTime lastAttemptTime;
        private String lastAttemptIP;
        private boolean locked;
        private LocalDateTime lockedUntil;

        public LoginAttempt(String email) {
            this.email = email;
            this.failedAttempts = 0;
            this.locked = false;
        }

        public void incrementFailedAttempts() {
            this.failedAttempts++;
        }

        public void lock() {
            this.locked = true;
            this.lockedUntil = LocalDateTime.now().plusMinutes(LOCKOUT_DURATION_MINUTES);
        }

        public boolean isLocked() {
            return locked;
        }

        public boolean isLockoutExpired() {
            return LocalDateTime.now().isAfter(lockedUntil);
        }

        public int getRemainingLockoutMinutes() {
            if (lockedUntil == null) return 0;
            long minutes = java.time.temporal.ChronoUnit.MINUTES.between(LocalDateTime.now(), lockedUntil);
            return (int) Math.max(0, minutes);
        }

        public void reset() {
            this.failedAttempts = 0;
            this.locked = false;
            this.lockedUntil = null;
            this.lastAttemptTime = null;
        }

        public int getFailedAttempts() {
            return failedAttempts;
        }

        public void setLastAttemptTime(LocalDateTime time) {
            this.lastAttemptTime = time;
        }

        public void setLastAttemptIP(String ip) {
            this.lastAttemptIP = ip;
        }
    }

    /**
     * Kết quả kiểm tra lần đăng nhập
     */
    public static class LoginAttemptResult {
        private boolean allowed;
        private String message;

        public LoginAttemptResult(boolean allowed, String message) {
            this.allowed = allowed;
            this.message = message;
        }

        public boolean isAllowed() {
            return allowed;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     * Thông tin đăng nhập cuối cùng
     */
    public static class LastLoginInfo {
        private LocalDateTime lastLoginTime;
        private String lastLoginIP;
        private String lastLoginDevice;
        private LocalDateTime previousLoginTime;

        public LastLoginInfo() {}

        public LocalDateTime getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(LocalDateTime time) {
            this.lastLoginTime = time;
        }

        public String getLastLoginIP() {
            return lastLoginIP;
        }

        public void setLastLoginIP(String ip) {
            this.lastLoginIP = ip;
        }

        public String getLastLoginDevice() {
            return lastLoginDevice;
        }

        public void setLastLoginDevice(String device) {
            this.lastLoginDevice = device;
        }

        public LocalDateTime getPreviousLoginTime() {
            return previousLoginTime;
        }

        public void setPreviousLoginTime(LocalDateTime time) {
            this.previousLoginTime = time;
        }
    }
}
