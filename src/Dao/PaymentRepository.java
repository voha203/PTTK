package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gymsystem.payment.model.Payment;
import com.gymsystem.payment.model.MomoPayment;
import com.gymsystem.payment.model.BankTransferPayment;

/**
 * Repository để quản lý dữ liệu Payment trong database
 * Cung cấp các phương thức CRUD cho Payment
 */
public class PaymentRepository {

    private Connection conn;

    public PaymentRepository() {
        try {
            conn = DBConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lưu payment mới vào database
     */
    public boolean save(Payment payment) {
        String sql = "INSERT INTO payment(payment_id, order_id, amount, payment_method, payment_status, payment_time) "
                + "VALUES(?, ?, ?, ?, ?, NOW())";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, payment.getPaymentId());
            ps.setString(2, payment.getOrderId());
            ps.setDouble(3, payment.getAmount());
            ps.setString(4, getPaymentMethod(payment));
            ps.setString(5, payment.getPaymentStatus());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Cập nhật trạng thái payment
     */
    public boolean update(Payment payment) {
        String sql = "UPDATE payment SET payment_status = ?, payment_time = NOW() WHERE payment_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, payment.getPaymentStatus());
            ps.setString(2, payment.getPaymentId());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Tìm payment theo paymentId
     */
    public Payment findByPaymentId(String paymentId) {
        String sql = "SELECT * FROM payment WHERE payment_id = ?";
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
     * Tìm payment theo orderId
     */
    public Payment findByOrderId(String orderId) {
        String sql = "SELECT * FROM payment WHERE order_id = ? ORDER BY payment_time DESC LIMIT 1";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, orderId);
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
     * Lấy tất cả payments theo status
     */
    public List<Payment> findByStatus(String status) {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payment WHERE payment_status = ? ORDER BY payment_time DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                payments.add(mapResultSetToPayment(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payments;
    }

    /**
     * Lấy tất cả payments
     */
    public List<Payment> findAll() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payment ORDER BY payment_time DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                payments.add(mapResultSetToPayment(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payments;
    }

    /**
     * Xóa payment theo paymentId
     */
    public boolean delete(String paymentId) {
        String sql = "DELETE FROM payment WHERE payment_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, paymentId);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Kiểm tra payment đã tồn tại không
     */
    public boolean exists(String paymentId) {
        return findByPaymentId(paymentId) != null;
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
     * Lấy payment method từ Payment object
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
