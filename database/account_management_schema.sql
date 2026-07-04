-- ============================================================
-- USER ACCOUNT MANAGEMENT SCHEMA IMPROVEMENTS
-- ============================================================

-- ============================================================
-- TABLE: users (Updated)
-- Cập nhật để hỗ trợ account management
-- ============================================================
ALTER TABLE users ADD COLUMN IF NOT EXISTS 
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Thời gian tạo tài khoản';

ALTER TABLE users ADD COLUMN IF NOT EXISTS 
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Lần cập nhật cuối';

ALTER TABLE users ADD COLUMN IF NOT EXISTS 
    last_login DATETIME COMMENT 'Lần đăng nhập cuối cùng';

ALTER TABLE users ADD COLUMN IF NOT EXISTS 
    last_login_ip VARCHAR(45) COMMENT 'IP lần đăng nhập cuối';

ALTER TABLE users ADD COLUMN IF NOT EXISTS 
    failed_login_attempts INT DEFAULT 0 COMMENT 'Số lần đăng nhập thất bại liên tiếp';

ALTER TABLE users ADD COLUMN IF NOT EXISTS 
    locked_until DATETIME COMMENT 'Thời gian khóa tài khoản (NULL = không khóa)';

ALTER TABLE users ADD COLUMN IF NOT EXISTS 
    password_changed_at DATETIME COMMENT 'Lần thay đổi mật khẩu cuối';

ALTER TABLE users ADD COLUMN IF NOT EXISTS 
    two_factor_enabled BOOLEAN DEFAULT FALSE COMMENT 'Bật xác thực 2 yếu tố';

-- ============================================================
-- TABLE: user_login_history
-- Ghi nhật ký tất cả lần đăng nhập
-- ============================================================
CREATE TABLE IF NOT EXISTS user_login_history (
    login_id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID lịch sử đăng nhập',
    user_id INT NOT NULL COMMENT 'ID người dùng',
    login_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Thời gian đăng nhập',
    ip_address VARCHAR(45) COMMENT 'Địa chỉ IP',
    device_info VARCHAR(255) COMMENT 'Thông tin thiết bị (User-Agent)',
    login_method VARCHAR(20) COMMENT 'Phương thức: LOCAL, GOOGLE, FACEBOOK',
    status VARCHAR(20) COMMENT 'Trạng thái: SUCCESS, FAILED',
    failure_reason VARCHAR(255) COMMENT 'Lý do thất bại (nếu có)',
    
    INDEX idx_user_id (user_id),
    INDEX idx_login_time (login_time),
    INDEX idx_status (status),
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci 
COMMENT='Lịch sử tất cả các lần đăng nhập';

-- ============================================================
-- TABLE: user_account_changes
-- Ghi nhật ký các thay đổi tài khoản
-- ============================================================
CREATE TABLE IF NOT EXISTS user_account_changes (
    change_id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID thay đổi',
    user_id INT NOT NULL COMMENT 'ID người dùng',
    change_type VARCHAR(50) COMMENT 'Loại thay đổi: PROFILE_UPDATE, PASSWORD_CHANGE, EMAIL_CHANGE, ACCOUNT_DISABLED, ACCOUNT_REACTIVATED',
    old_value TEXT COMMENT 'Giá trị cũ',
    new_value TEXT COMMENT 'Giá trị mới',
    changed_by INT COMMENT 'ID người thực hiện thay đổi (NULL = người dùng tự đổi)',
    changed_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Thời gian thay đổi',
    ip_address VARCHAR(45) COMMENT 'IP thực hiện thay đổi',
    
    INDEX idx_user_id (user_id),
    INDEX idx_change_type (change_type),
    INDEX idx_changed_at (changed_at),
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci 
COMMENT='Ghi nhật ký các thay đổi trên tài khoản người dùng';

-- ============================================================
-- TABLE: user_security_questions
-- Câu hỏi bảo mật cho khôi phục tài khoản
-- ============================================================
CREATE TABLE IF NOT EXISTS user_security_questions (
    question_id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID câu hỏi',
    user_id INT NOT NULL COMMENT 'ID người dùng',
    question VARCHAR(255) NOT NULL COMMENT 'Câu hỏi bảo mật',
    answer_hash VARCHAR(255) NOT NULL COMMENT 'Câu trả lời (đã hash)',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Thời gian tạo',
    
    INDEX idx_user_id (user_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci 
COMMENT='Câu hỏi bảo mật của người dùng';

-- ============================================================
-- TABLE: password_reset_tokens
-- Token để reset mật khẩu qua email
-- ============================================================
CREATE TABLE IF NOT EXISTS password_reset_tokens (
    token_id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID token',
    user_id INT NOT NULL COMMENT 'ID người dùng',
    token VARCHAR(255) NOT NULL UNIQUE COMMENT 'Reset token (hashed)',
    expires_at DATETIME NOT NULL COMMENT 'Hết hạn (thường 1 giờ)',
    used BOOLEAN DEFAULT FALSE COMMENT 'Token đã được sử dụng',
    used_at DATETIME COMMENT 'Thời gian sử dụng',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Thời gian tạo',
    
    INDEX idx_user_id (user_id),
    INDEX idx_token (token),
    INDEX idx_expires_at (expires_at),
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci 
COMMENT='Token để reset mật khẩu';

-- ============================================================
-- TABLE: user_sessions
-- Quản lý session người dùng
-- ============================================================
CREATE TABLE IF NOT EXISTS user_sessions (
    session_id VARCHAR(255) PRIMARY KEY COMMENT 'ID session (hashed)',
    user_id INT NOT NULL COMMENT 'ID người dùng',
    token VARCHAR(500) NOT NULL UNIQUE COMMENT 'JWT/Session token',
    ip_address VARCHAR(45) COMMENT 'IP tạo session',
    user_agent VARCHAR(500) COMMENT 'User-Agent',
    expires_at DATETIME NOT NULL COMMENT 'Hết hạn session',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Thời gian tạo',
    last_activity DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Hoạt động cuối',
    
    INDEX idx_user_id (user_id),
    INDEX idx_expires_at (expires_at),
    INDEX idx_token (token),
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci 
COMMENT='Quản lý session người dùng';

-- ============================================================
-- VIEW: user_security_summary
-- Tóm tắt bảo mật tài khoản
-- ============================================================
CREATE OR REPLACE VIEW user_security_summary AS
SELECT 
    u.id,
    u.username,
    u.email,
    u.active,
    u.two_factor_enabled,
    u.last_login,
    u.last_login_ip,
    u.failed_login_attempts,
    u.locked_until,
    CASE 
        WHEN u.locked_until IS NOT NULL AND NOW() < u.locked_until THEN 'LOCKED'
        WHEN u.active = 0 THEN 'DISABLED'
        WHEN u.active = 1 THEN 'ACTIVE'
        ELSE 'UNKNOWN'
    END as account_status,
    DATEDIFF(NOW(), u.password_changed_at) as days_since_password_change,
    (SELECT COUNT(*) FROM user_login_history WHERE user_id = u.id AND login_time > DATE_SUB(NOW(), INTERVAL 30 DAY)) as logins_last_30_days
FROM users u;

-- ============================================================
-- VIEW: failed_login_attempts
-- Theo dõi các lần đăng nhập thất bại
-- ============================================================
CREATE OR REPLACE VIEW failed_login_attempts AS
SELECT 
    COUNT(*) as failed_count,
    user_id,
    DATE(login_time) as login_date,
    ip_address
FROM user_login_history
WHERE status = 'FAILED' 
  AND login_time > DATE_SUB(NOW(), INTERVAL 7 DAY)
GROUP BY DATE(login_time), user_id, ip_address
HAVING failed_count > 3
ORDER BY login_date DESC;

-- ============================================================
-- STORED PROCEDURES
-- ============================================================

-- Procedure: Lock account sau N lần thất bại
DELIMITER $$
CREATE PROCEDURE IF NOT EXISTS sp_lock_account_on_failed_login(
    IN p_user_id INT,
    IN p_failed_attempts INT
)
BEGIN
    IF p_failed_attempts >= 5 THEN
        UPDATE users 
        SET locked_until = DATE_ADD(NOW(), INTERVAL 15 MINUTE)
        WHERE id = p_user_id;
    END IF;
END$$
DELIMITER ;

-- Procedure: Get user security summary
DELIMITER $$
CREATE PROCEDURE IF NOT EXISTS sp_get_user_security_info(
    IN p_user_id INT
)
BEGIN
    SELECT 
        u.id,
        u.username,
        u.email,
        u.active,
        u.last_login,
        u.last_login_ip,
        u.failed_login_attempts,
        u.password_changed_at,
        COUNT(DISTINCT h.login_id) as total_logins,
        MAX(h.login_time) as last_login_time
    FROM users u
    LEFT JOIN user_login_history h ON u.id = h.user_id
    WHERE u.id = p_user_id
    GROUP BY u.id;
END$$
DELIMITER ;

-- ============================================================
-- INDEXES cho Performance
-- ============================================================
CREATE INDEX idx_users_active ON users(active);
CREATE INDEX idx_users_last_login ON users(last_login);
CREATE INDEX idx_users_locked_until ON users(locked_until);
CREATE INDEX idx_login_history_user_time ON user_login_history(user_id, login_time);
CREATE INDEX idx_account_changes_user ON user_account_changes(user_id, change_type);

-- ============================================================
-- END OF SCHEMA UPDATE
-- ============================================================
