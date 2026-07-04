-- ============================================================
-- DATABASE SCHEMA FOR PAYMENT SYSTEM
-- Project: PTTK (Gym Management System)
-- ============================================================

-- ============================================================
-- TABLE: payment
-- Mục đích: Lưu trữ thông tin các giao dịch thanh toán
-- ============================================================
CREATE TABLE IF NOT EXISTS payment (
    payment_id VARCHAR(50) PRIMARY KEY COMMENT 'ID giao dịch thanh toán (PAY-xxxxxxxx)',
    order_id VARCHAR(50) NOT NULL COMMENT 'ID đơn hàng liên quan',
    amount DOUBLE NOT NULL COMMENT 'Số tiền thanh toán',
    payment_method VARCHAR(20) NOT NULL COMMENT 'Phương thức thanh toán (MOMO, BANK_TRANSFER)',
    payment_status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT 'Trạng thái: PENDING, SUCCESS, FAILED',
    payment_time DATETIME COMMENT 'Thời gian tạo giao dịch',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Lần cập nhật cuối cùng',
    
    INDEX idx_order_id (order_id),
    INDEX idx_payment_method (payment_method),
    INDEX idx_payment_status (payment_status),
    INDEX idx_payment_time (payment_time),
    
    FOREIGN KEY (order_id) REFERENCES `order`(order_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci 
COMMENT='Bảng lưu trữ giao dịch thanh toán chính';

-- ============================================================
-- TABLE: order
-- Mục đích: Lưu trữ thông tin đơn hàng/gói tập của thành viên
-- ============================================================
CREATE TABLE IF NOT EXISTS `order` (
    order_id VARCHAR(50) PRIMARY KEY COMMENT 'ID đơn hàng',
    member_id INT NOT NULL COMMENT 'ID thành viên đặt hàng',
    original_amount DOUBLE NOT NULL COMMENT 'Giá gốc (trước chiết khấu)',
    final_amount DOUBLE NOT NULL COMMENT 'Giá cuối cùng (sau chiết khấu)',
    order_status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT 'Trạng thái: PENDING, COMPLETED, CANCELLED',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Thời gian tạo đơn',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Lần cập nhật cuối cùng',
    
    INDEX idx_member_id (member_id),
    INDEX idx_order_status (order_status),
    INDEX idx_created_at (created_at),
    
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci 
COMMENT='Bảng lưu trữ đơn hàng/gói tập';

-- ============================================================
-- TABLE: payment_transaction_log
-- Mục đích: Ghi nhật ký chi tiết từng giao dịch thanh toán
-- Dùng để tracking, audit, thống kê và debug
-- ============================================================
CREATE TABLE IF NOT EXISTS payment_transaction_log (
    log_id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID nhật ký',
    payment_id VARCHAR(50) NOT NULL COMMENT 'ID giao dịch thanh toán',
    order_id VARCHAR(50) NOT NULL COMMENT 'ID đơn hàng',
    amount DOUBLE NOT NULL COMMENT 'Số tiền giao dịch',
    payment_method VARCHAR(20) NOT NULL COMMENT 'Phương thức: MOMO, BANK_TRANSFER',
    payment_status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT 'Trạng thái: PENDING, SUCCESS, FAILED',
    voucher_code VARCHAR(50) COMMENT 'Mã voucher được sử dụng (nếu có)',
    ip_address VARCHAR(45) COMMENT 'Địa chỉ IP của người dùng',
    user_agent TEXT COMMENT 'User Agent của trình duyệt',
    request_data JSON COMMENT 'Dữ liệu request (JSON)',
    response_data JSON COMMENT 'Dữ liệu response từ gateway (JSON)',
    transaction_time DATETIME COMMENT 'Thời gian thực hiện giao dịch',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Thời gian ghi nhật ký',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Lần cập nhật cuối cùng',
    
    INDEX idx_payment_id (payment_id),
    INDEX idx_order_id (order_id),
    INDEX idx_payment_method (payment_method),
    INDEX idx_payment_status (payment_status),
    INDEX idx_created_at (created_at),
    INDEX idx_voucher_code (voucher_code),
    
    FOREIGN KEY (payment_id) REFERENCES payment(payment_id) ON DELETE CASCADE,
    FOREIGN KEY (order_id) REFERENCES `order`(order_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci 
COMMENT='Bảng ghi nhật ký chi tiết giao dịch thanh toán';

-- ============================================================
-- TABLE: payment_webhook_log
-- Mục đích: Ghi nhật ký webhook callback từ payment gateway (Momo, Bank)
-- ============================================================
CREATE TABLE IF NOT EXISTS payment_webhook_log (
    webhook_id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID webhook',
    payment_id VARCHAR(50) NOT NULL COMMENT 'ID giao dịch thanh toán',
    webhook_status VARCHAR(20) COMMENT 'Trạng thái xử lý webhook: RECEIVED, PROCESSED, FAILED',
    webhook_data JSON NOT NULL COMMENT 'Dữ liệu webhook nhận từ gateway (JSON)',
    webhook_signature VARCHAR(255) COMMENT 'Chữ ký HMAC để xác thực',
    signature_verified BOOLEAN DEFAULT FALSE COMMENT 'Xác thực chữ ký thành công',
    error_message TEXT COMMENT 'Thông báo lỗi (nếu có)',
    received_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Thời gian nhận webhook',
    processed_at DATETIME COMMENT 'Thời gian xử lý webhook',
    
    INDEX idx_payment_id (payment_id),
    INDEX idx_webhook_status (webhook_status),
    INDEX idx_received_at (received_at),
    
    FOREIGN KEY (payment_id) REFERENCES payment(payment_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci 
COMMENT='Bảng ghi nhật ký webhook callback từ payment gateway';

-- ============================================================
-- TABLE: payment_retry_log
-- Mục đích: Ghi nhật ký các lần thử lại giao dịch thất bại
-- ============================================================
CREATE TABLE IF NOT EXISTS payment_retry_log (
    retry_id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID lần thử lại',
    payment_id VARCHAR(50) NOT NULL COMMENT 'ID giao dịch thanh toán',
    attempt_number INT NOT NULL DEFAULT 1 COMMENT 'Lần thứ mấy',
    retry_reason VARCHAR(255) COMMENT 'Lý do thử lại',
    retry_status VARCHAR(20) COMMENT 'Kết quả: SUCCESS, FAILED',
    retry_response TEXT COMMENT 'Phản hồi từ gateway',
    retried_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Thời gian thử lại',
    
    INDEX idx_payment_id (payment_id),
    INDEX idx_attempt_number (attempt_number),
    INDEX idx_retried_at (retried_at),
    
    FOREIGN KEY (payment_id) REFERENCES payment(payment_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci 
COMMENT='Bảng ghi nhật ký retry giao dịch thất bại';

-- ============================================================
-- VIEW: payment_summary
-- Mục đích: Thống kê tổng hợp giao dịch thanh toán
-- ============================================================
CREATE OR REPLACE VIEW payment_summary AS
SELECT 
    DATE(p.payment_time) as transaction_date,
    p.payment_method,
    p.payment_status,
    COUNT(*) as transaction_count,
    SUM(p.amount) as total_amount,
    AVG(p.amount) as avg_amount,
    MIN(p.amount) as min_amount,
    MAX(p.amount) as max_amount
FROM payment p
GROUP BY DATE(p.payment_time), p.payment_method, p.payment_status
ORDER BY transaction_date DESC;

-- ============================================================
-- VIEW: payment_daily_report
-- Mục đích: Báo cáo doanh thu hàng ngày
-- ============================================================
CREATE OR REPLACE VIEW payment_daily_report AS
SELECT 
    DATE(p.payment_time) as report_date,
    COUNT(DISTINCT p.order_id) as order_count,
    COUNT(DISTINCT o.member_id) as member_count,
    SUM(CASE WHEN p.payment_status = 'SUCCESS' THEN p.amount ELSE 0 END) as success_amount,
    SUM(CASE WHEN p.payment_status = 'FAILED' THEN p.amount ELSE 0 END) as failed_amount,
    SUM(CASE WHEN p.payment_status = 'PENDING' THEN p.amount ELSE 0 END) as pending_amount,
    COUNT(CASE WHEN p.payment_status = 'SUCCESS' THEN 1 END) as success_count,
    COUNT(CASE WHEN p.payment_status = 'FAILED' THEN 1 END) as failed_count
FROM payment p
LEFT JOIN `order` o ON p.order_id = o.order_id
GROUP BY DATE(p.payment_time)
ORDER BY report_date DESC;

-- ============================================================
-- INDEXES cho performance optimization
-- ============================================================
CREATE INDEX idx_payment_order_status ON payment(order_id, payment_status);
CREATE INDEX idx_payment_time_status ON payment(payment_time, payment_status);
CREATE INDEX idx_order_member_status ON `order`(member_id, order_status);
CREATE INDEX idx_log_payment_status ON payment_transaction_log(payment_id, payment_status);

-- ============================================================
-- STORED PROCEDURES
-- ============================================================

-- Procedure: Get payment statistics
DELIMITER $$
CREATE PROCEDURE IF NOT EXISTS sp_get_payment_stats(
    IN p_start_date DATETIME,
    IN p_end_date DATETIME
)
BEGIN
    SELECT 
        p.payment_method,
        p.payment_status,
        COUNT(*) as count,
        SUM(p.amount) as total,
        AVG(p.amount) as average
    FROM payment p
    WHERE p.payment_time BETWEEN p_start_date AND p_end_date
    GROUP BY p.payment_method, p.payment_status;
END$$
DELIMITER ;

-- ============================================================
-- SAMPLE DATA (tùy chọn)
-- ============================================================
-- Chỉ thêm nếu bạn muốn test với dữ liệu mẫu
-- INSERT INTO `order` (order_id, member_id, original_amount, final_amount, order_status)
-- VALUES ('ORD-001', 1, 1200000, 1200000, 'PENDING');

-- ============================================================
-- END OF SCHEMA
-- ============================================================
