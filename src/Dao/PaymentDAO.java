package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.gymsystem.payment.model.Payment;
import com.gymsystem.payment.model.MomoPayment;
import com.gymsystem.payment.model.BankTransferPayment;

/**
 * PaymentDAO - Data Access Object để ghi nhật ký giao dịch thanh toán chi tiết
 * Quản lý lịch sử đầy đủ các giao dịch thanh toán: tạo, cập nhật, truy vấn
 * 
 * Khác với PaymentRepository:
 * - PaymentDAO: Tập trung vào logging/tracking chi tiết từng giao dịch
 * - PaymentRepository: Quản lý trạng thái payment cơ bản
 */
public class PaymentDAO {

    private Connection conn;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public PaymentDAO() {
        try {
            conn = DBConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ghi nhật ký giao dịch thanh toán mới
     * Lưu tất cả thông tin chi tiết của một giao dịch
     */
    public boolean logPaymentTransaction(Payment payment, String voucherCode, String ipAddress) {
        String sql = "INSERT INTO payment_transaction_log("
                + "payment_id, order_id, amount, payment_method, payment_status, "
                + "voucher_code, ip_address, user_agent, transaction_time, created_at) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, payment.getPaymentId());
            ps.setString(2, payment.getOrderId());
            ps.setDouble(3, payment.getAmount());
            ps.setString(4, getPaymentMethod(payment));
            ps.setString(5, payment.getPaymentStatus());
            ps.setString(6, voucherCode);
            ps.setString(7, ipAddress);
            ps.setString(8, ""); // User-Agent nếu cần
            ps.setString(9, LocalDateTime.now().format(DATE_FORMATTER));
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Cập nhật nhật ký giao dịch (khi có webhook callback)
     */
    public boolean updatePaymentLog(String paymentId, String newStatus, String responseData) {
        String sql = "UPDATE payment_transaction_log "
                + "SET payment_status = ?, response_data = ?, updated_at = NOW() "
                + "WHERE payment_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newStatus);
            ps.setString(2, responseData);
            ps.setString(3, paymentId);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Lấy chi tiết nhật ký giao dịch theo paymentId
     */
    public Payment getPaymentLog(String paymentId) {
        String sql = "SELECT * FROM payment_transaction_log WHERE payment_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, paymentId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToPayment(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lấy tất cả nhật ký giao dịch của một đơn hàng
     */
    public List<Payment> getPaymentLogsByOrderId(String orderId) {
        List<Payment> logs = new ArrayList<>();
        String sql = "SELECT * FROM payment_transaction_log WHERE order_id = ? ORDER BY created_at DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, orderId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                logs.add(mapResultSetToPayment(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logs;
    }

    /**
     * Lấy tất cả giao dịch theo trạng thái
     */
    public List<Payment> getPaymentLogsByStatus(String status) {
        List<Payment> logs = new ArrayList<>();
        String sql = "SELECT * FROM payment_transaction_log WHERE payment_status = ? ORDER BY created_at DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                logs.add(mapResultSetToPayment(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logs;
    }

    /**
     * Lấy giao dịch theo phương thức thanh toán
     */
    public List<Payment> getPaymentLogsByMethod(String method) {
        List<Payment> logs = new ArrayList<>();
        String sql = "SELECT * FROM payment_transaction_log WHERE payment_method = ? ORDER BY created_at DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, method);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                logs.add(mapResultSetToPayment(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logs;
    }

    /**
     * Lấy tất cả giao dịch
     */
    public List<Payment> getAllPaymentLogs() {
        List<Payment> logs = new ArrayList<>();
        String sql = "SELECT * FROM payment_transaction_log ORDER BY created_at DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                logs.add(mapResultSetToPayment(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logs;
    }

    /**
     * Lấy giao dịch trong khoảng thời gian
     */
    public List<Payment> getPaymentLogsByDateRange(String startDate, String endDate) {
        List<Payment> logs = new ArrayList<>();
        String sql = "SELECT * FROM payment_transaction_log WHERE created_at BETWEEN ? AND ? ORDER BY created_at DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                logs.add(mapResultSetToPayment(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logs;
    }

    /**
     * Lấy thống kê giao dịch theo trạng thái
     */
    public int countPaymentsByStatus(String status) {
        String sql = "SELECT COUNT(*) as count FROM payment_transaction_log WHERE payment_status = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Lấy tổng doanh thu theo phương thức thanh toán
     */
    public double getTotalRevenueByMethod(String method) {
        String sql = "SELECT SUM(amount) as total FROM payment_transaction_log WHERE payment_method = ? AND payment_status = 'SUCCESS'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, method);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                double total = rs.getDouble("total");
                return total > 0 ? total : 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Lấy thống kê toàn bộ doanh thu
     */
    public double getTotalRevenue() {
        String sql = "SELECT SUM(amount) as total FROM payment_transaction_log WHERE payment_status = 'SUCCESS'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                double total = rs.getDouble("total");
                return total > 0 ? total : 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Xóa nhật ký cũ (hơn N ngày)
     */
    public boolean deleteOldPaymentLogs(int daysOld) {
        String sql = "DELETE FROM payment_transaction_log WHERE created_at < DATE_SUB(NOW(), INTERVAL ? DAY)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, daysOld);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Kiểm tra giao dịch đã tồn tại
     */
    public boolean exists(String paymentId) {
        return getPaymentLog(paymentId) != null;
    }

    /**
     * Lấy số lượng giao dịch
     */
    public int getTotalPaymentCount() {
        String sql = "SELECT COUNT(*) as count FROM payment_transaction_log";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Ánh xạ ResultSet sang Payment object
     */
    private Payment mapResultSetToPayment(ResultSet rs) throws Exception {
        String paymentId = rs.getString("payment_id");
        String orderId = rs.getString("order_id");
        double amount = rs.getDouble("amount");
        String paymentMethod = rs.getString("payment_method");
        String paymentStatus = rs.getString("payment_status");

        Payment payment;
        if ("MOMO".equalsIgnoreCase(paymentMethod)) {
            payment = new MomoPayment(paymentId, orderId, amount);
        } else if ("BANK_TRANSFER".equalsIgnoreCase(paymentMethod)) {
            payment = new BankTransferPayment(paymentId, orderId, amount);
        } else {
            return null;
        }

        payment.setPaymentStatus(paymentStatus);
        return payment;
    }

    /**
     * Lấy tên phương thức thanh toán từ Payment object
     */
    private String getPaymentMethod(Payment payment) {
        if (payment instanceof MomoPayment) {
            return "MOMO";
        } else if (payment instanceof BankTransferPayment) {
            return "BANK_TRANSFER";
        }
        return "UNKNOWN";
    }
}
