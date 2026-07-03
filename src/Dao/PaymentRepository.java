package DAO;

import com.gymsystem.payment.entity.Payment;
import java.util.HashMap;
import java.util.Map;

// Lớp này chịu trách nhiệm lưu/đọc dữ liệu từ Database
public class PaymentRepository {
    
    // Giả lập một bảng Database trong bộ nhớ (In-memory Database)
    private final Map<String, Payment> databaseEmulator = new HashMap<>();

    // Hàm lưu thông tin thanh toán vào DB (Insert / Update)
    public void save(Payment payment) {
        databaseEmulator.put(payment.getPaymentId(), payment);
        System.out.println("[Database] Đã lưu thành công giao dịch " + payment.getPaymentId() + " vào DB.");
    }

    // Hàm tìm kiếm thông tin thanh toán theo ID (Select)
    public Payment findById(String paymentId) {
        return databaseEmulator.get(paymentId);
    }
    
    // Hàm tìm kiếm thanh toán theo Mã đơn hàng
    public Payment findByOrderId(String orderId) {
        return databaseEmulator.values().stream()
                .filter(p -> p.getOrderId().equals(orderId))
                .findFirst()
                .orElse(null);
    }
}