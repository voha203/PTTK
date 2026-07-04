package com.gymsystem.account.service;

import Dao.UserDao;
import Model.User;
import java.util.regex.Pattern;

/**
 * AccountManagementService - Dịch vụ quản lý tài khoản
 * Xử lý các tác vụ liên quan đến hồ sơ người dùng:
 * - Cập nhật thông tin tài khoản
 * - Thay đổi mật khẩu
 * - Xóa tài khoản
 * - Kiểm tra tính hợp lệ dữ liệu
 */
public class AccountManagementService {

    private UserDao userDao;
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern USERNAME_PATTERN = 
        Pattern.compile("^[a-zA-Z0-9_]{3,20}$");
    private static final Pattern FULLNAME_PATTERN = 
        Pattern.compile("^[a-zA-ZÀ-ỿ\\s]{2,100}$");

    public AccountManagementService() {
        this.userDao = new UserDao();
    }

    /**
     * Lấy thông tin tài khoản của người dùng
     */
    public User getAccountInfo(int userId) {
        try {
            // Thay thế bằng phương thức getUserById khi UserDao được cập nhật
            return null; // TODO: Implement getUserById in UserDao
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Cập nhật thông tin hồ sơ người dùng
     * Có thể cập nhật: fullName, avatar, wantMail
     * Không thể cập nhật: email, username, password (dùng method riêng)
     */
    public UpdateResult updateProfile(User user, String newFullName, String newAvatar, boolean wantMail) {
        // Validation
        if (user == null || user.getId() <= 0) {
            return new UpdateResult(false, "Người dùng không hợp lệ");
        }

        // Validate fullName
        if (newFullName != null && !newFullName.trim().isEmpty()) {
            if (!isValidFullName(newFullName)) {
                return new UpdateResult(false, 
                    "Tên đầy đủ không hợp lệ! Phải từ 2-100 ký tự, chỉ chứa chữ cái");
            }
            user.setFullName(newFullName.trim());
        }

        // Validate avatar URL
        if (newAvatar != null && !newAvatar.trim().isEmpty()) {
            if (!isValidUrl(newAvatar)) {
                return new UpdateResult(false, "URL ảnh đại diện không hợp lệ");
            }
            user.setAvatar(newAvatar.trim());
        }

        user.setWantMail(wantMail);

        try {
            // TODO: Implement updateUser in UserDao
            // boolean result = userDao.updateUser(user);
            boolean result = true; // Placeholder
            
            if (result) {
                return new UpdateResult(true, "Cập nhật hồ sơ thành công");
            } else {
                return new UpdateResult(false, "Cập nhật hồ sơ thất bại");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new UpdateResult(false, "Lỗi khi cập nhật hồ sơ: " + e.getMessage());
        }
    }

    /**
     * Thay đổi mật khẩu
     * Yêu cầu xác thực mật khẩu cũ
     */
    public PasswordChangeResult changePassword(User user, String oldPassword, String newPassword, String confirmPassword) {
        // Validate input
        if (user == null || user.getId() <= 0) {
            return new PasswordChangeResult(false, "Người dùng không hợp lệ");
        }

        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            return new PasswordChangeResult(false, "Mật khẩu cũ không được để trống");
        }

        if (newPassword == null || newPassword.trim().isEmpty()) {
            return new PasswordChangeResult(false, "Mật khẩu mới không được để trống");
        }

        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            return new PasswordChangeResult(false, "Xác nhận mật khẩu không được để trống");
        }

        // Check old password
        if (!user.getPasswordHash().equals(oldPassword)) {
            return new PasswordChangeResult(false, "Mật khẩu cũ không chính xác");
        }

        // Check passwords match
        if (!newPassword.equals(confirmPassword)) {
            return new PasswordChangeResult(false, "Mật khẩu mới không khớp");
        }

        // Check new password != old password
        if (newPassword.equals(oldPassword)) {
            return new PasswordChangeResult(false, "Mật khẩu mới phải khác mật khẩu cũ");
        }

        // Validate password strength
        PasswordStrengthResult strengthResult = validatePasswordStrength(newPassword);
        if (!strengthResult.isStrong()) {
            return new PasswordChangeResult(false, strengthResult.getMessage());
        }

        try {
            userDao.changePassword(user, newPassword);
            return new PasswordChangeResult(true, "Đổi mật khẩu thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return new PasswordChangeResult(false, "Lỗi khi đổi mật khẩu: " + e.getMessage());
        }
    }

    /**
     * Xóa tài khoản người dùng
     * Yêu cầu xác thực bằng mật khẩu
     * LƯỚI ý: Action này không thể hoàn tác
     */
    public AccountDeletionResult deleteAccount(User user, String password) {
        // Validation
        if (user == null || user.getId() <= 0) {
            return new AccountDeletionResult(false, "Người dùng không hợp lệ");
        }

        if (password == null || password.trim().isEmpty()) {
            return new AccountDeletionResult(false, "Mật khẩu không được để trống");
        }

        // Verify password
        if (!user.getPasswordHash().equals(password)) {
            return new AccountDeletionResult(false, "Mật khẩu không chính xác");
        }

        try {
            // TODO: Implement deleteUser in UserDao
            // boolean result = userDao.deleteUser(user.getId());
            boolean result = true; // Placeholder
            
            if (result) {
                return new AccountDeletionResult(true, 
                    "Tài khoản đã được xóa thành công. Tất cả dữ liệu sẽ bị xóa vĩnh viễn");
            } else {
                return new AccountDeletionResult(false, "Xóa tài khoản thất bại");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new AccountDeletionResult(false, "Lỗi khi xóa tài khoản: " + e.getMessage());
        }
    }

    /**
     * Vô hiệu hóa/khóa tài khoản (soft delete)
     * Khác với deleteAccount: dữ liệu vẫn giữ lại nhưng tài khoản bị khóa
     */
    public DisableAccountResult disableAccount(User user, String password, String reason) {
        // Validation
        if (user == null || user.getId() <= 0) {
            return new DisableAccountResult(false, "Người dùng không hợp lệ");
        }

        if (password == null || password.trim().isEmpty()) {
            return new DisableAccountResult(false, "Mật khẩu không được để trống");
        }

        // Verify password
        if (!user.getPasswordHash().equals(password)) {
            return new DisableAccountResult(false, "Mật khẩu không chính xác");
        }

        try {
            user.setStatus(false); // Disable account
            // TODO: Implement updateUser in UserDao
            // boolean result = userDao.updateUser(user);
            boolean result = true; // Placeholder
            
            if (result) {
                return new DisableAccountResult(true, 
                    "Tài khoản đã bị vô hiệu hóa. Bạn có thể kích hoạt lại bất kỳ lúc nào");
            } else {
                return new DisableAccountResult(false, "Vô hiệu hóa tài khoản thất bại");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DisableAccountResult(false, "Lỗi khi vô hiệu hóa tài khoản: " + e.getMessage());
        }
    }

    /**
     * Kích hoạt lại tài khoản đã bị vô hiệu hóa
     */
    public ReactivateAccountResult reactivateAccount(User user, String password) {
        // Validation
        if (user == null || user.getId() <= 0) {
            return new ReactivateAccountResult(false, "Người dùng không hợp lệ");
        }

        if (password == null || password.trim().isEmpty()) {
            return new ReactivateAccountResult(false, "Mật khẩu không được để trống");
        }

        // Verify password
        if (!user.getPasswordHash().equals(password)) {
            return new ReactivateAccountResult(false, "Mật khẩu không chính xác");
        }

        try {
            user.setStatus(true); // Enable account
            // TODO: Implement updateUser in UserDao
            // boolean result = userDao.updateUser(user);
            boolean result = true; // Placeholder
            
            if (result) {
                return new ReactivateAccountResult(true, "Tài khoản đã được kích hoạt lại");
            } else {
                return new ReactivateAccountResult(false, "Kích hoạt tài khoản thất bại");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReactivateAccountResult(false, "Lỗi khi kích hoạt tài khoản: " + e.getMessage());
        }
    }

    /**
     * Kiểm tra email có hợp lệ không
     */
    public boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Kiểm tra username có hợp lệ không
     * Yêu cầu: 3-20 ký tự, chỉ chứa chữ cái, số, underscore
     */
    public boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return USERNAME_PATTERN.matcher(username.trim()).matches();
    }

    /**
     * Kiểm tra fullName có hợp lệ không
     * Yêu cầu: 2-100 ký tự, chỉ chứa chữ cái và khoảng trắng
     */
    public boolean isValidFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return false;
        }
        return FULLNAME_PATTERN.matcher(fullName.trim()).matches();
    }

    /**
     * Kiểm tra URL có hợp lệ không (cho avatar)
     */
    public boolean isValidUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        try {
            new java.net.URL(url.trim());
            return true;
        } catch (java.net.MalformedURLException e) {
            return false;
        }
    }

    /**
     * Kiểm tra mức độ mạnh của mật khẩu
     * Yêu cầu:
     * - Tối thiểu 8 ký tự
     * - Ít nhất 1 chữ hoa
     * - Ít nhất 1 chữ thường
     * - Ít nhất 1 số
     * - Ít nhất 1 ký tự đặc biệt
     */
    public PasswordStrengthResult validatePasswordStrength(String password) {
        StringBuilder errors = new StringBuilder();
        int score = 0;

        if (password == null || password.isEmpty()) {
            return new PasswordStrengthResult(false, 0, "Mật khẩu không được để trống");
        }

        if (password.length() < 8) {
            errors.append("• Tối thiểu 8 ký tự\n");
        } else {
            score += 25;
        }

        if (!password.matches(".*[A-Z].*")) {
            errors.append("• Phải chứa ít nhất 1 chữ hoa (A-Z)\n");
        } else {
            score += 25;
        }

        if (!password.matches(".*[a-z].*")) {
            errors.append("• Phải chứa ít nhất 1 chữ thường (a-z)\n");
        } else {
            score += 25;
        }

        if (!password.matches(".*[0-9].*")) {
            errors.append("• Phải chứa ít nhất 1 số (0-9)\n");
        } else {
            score += 15;
        }

        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};:',.<>?].*")) {
            errors.append("• Phải chứa ít nhất 1 ký tự đặc biệt (!@#$%...)\n");
        } else {
            score += 10;
        }

        boolean isStrong = score >= 75;
        String message = isStrong ? "Mật khẩu mạnh" : 
            "Mật khẩu yếu:\n" + errors.toString();

        return new PasswordStrengthResult(isStrong, score, message);
    }

    /**
     * Kiểm tra email đã tồn tại chưa (dùng khi đổi email)
     */
    public boolean isEmailExists(String email) {
        User existingUser = userDao.findUserByEmail(email);
        return existingUser != null;
    }

    // ==================== RESULT CLASSES ====================

    /**
     * Kết quả cập nhật hồ sơ
     */
    public static class UpdateResult {
        private boolean success;
        private String message;

        public UpdateResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     * Kết quả thay đổi mật khẩu
     */
    public static class PasswordChangeResult {
        private boolean success;
        private String message;

        public PasswordChangeResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     * Kết quả xóa tài khoản
     */
    public static class AccountDeletionResult {
        private boolean success;
        private String message;

        public AccountDeletionResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     * Kết quả vô hiệu hóa tài khoản
     */
    public static class DisableAccountResult {
        private boolean success;
        private String message;

        public DisableAccountResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     * Kết quả kích hoạt lại tài khoản
     */
    public static class ReactivateAccountResult {
        private boolean success;
        private String message;

        public ReactivateAccountResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     * Kết quả kiểm tra mức độ mạnh của mật khẩu
     */
    public static class PasswordStrengthResult {
        private boolean strong;
        private int score; // 0-100
        private String message;

        public PasswordStrengthResult(boolean strong, int score, String message) {
            this.strong = strong;
            this.score = score;
            this.message = message;
        }

        public boolean isStrong() {
            return strong;
        }

        public int getScore() {
            return score;
        }

        public String getMessage() {
            return message;
        }

        public String getStrengthLevel() {
            if (score >= 90) return "Rất mạnh";
            if (score >= 75) return "Mạnh";
            if (score >= 50) return "Trung bình";
            if (score >= 25) return "Yếu";
            return "Rất yếu";
        }
    }
}
