package com.gymsystem.payment.service;

import java.util.List;
import Dao.VoucherDAO;
import Dao.OrderDAO;
import com.gymsystem.payment.model.Order;
import com.gymsystem.payment.model.Voucher;

/**
 * Validation Service - Dịch vụ xác thực
 * Kiểm tra tính hợp lệ của Voucher, Order và các điều kiện thanh toán trước khi xử lý
 */
public class ValidationService {

    private VoucherDAO voucherDAO;
    private OrderDAO orderDAO;

    public ValidationService() {
        this.voucherDAO = new VoucherDAO();
        this.orderDAO = new OrderDAO();
    }

    /**
     * Xác thực Order trước khi thanh toán
     * Kiểm tra:
     * - Order có tồn tại không
     * - Order có trạng thái PENDING không
     * - Số tiền thanh toán có hợp lệ không
     */
    public ValidationResult validateOrder(String orderId, double amount) {
        // Kiểm tra Order tồn tại
        Order order = orderDAO.getOrderById(orderId);
        if (order == null) {
            return new ValidationResult(false, "Đơn hàng không tồn tại!");
        }

        // Kiểm tra trạng thái Order
        if (!"PENDING".equalsIgnoreCase(order.getOrderStatus())) {
            return new ValidationResult(false, "Đơn hàng không ở trạng thái chờ xử lý (PENDING)!");
        }

        // Kiểm tra số tiền
        if (amount <= 0) {
            return new ValidationResult(false, "Số tiền thanh toán phải lớn hơn 0!");
        }

        // Kiểm tra số tiền có khớp với đơn hàng không
        if (Math.abs(order.getFinalAmount() - amount) > 0.01) { // Cho phép sai số 0.01
            return new ValidationResult(false, 
                String.format("Số tiền không khớp! Yêu cầu: %.0f, thực tế: %.0f", 
                    order.getFinalAmount(), amount));
        }

        return new ValidationResult(true, "Đơn hàng hợp lệ");
    }

    /**
     * Xác thực Voucher
     * Kiểm tra:
     * - Voucher có tồn tại không
     * - Voucher còn lượt sử dụng không
     * - Voucher đã hết hạn chưa
     * - Voucher có active không
     * - Số tiền tối thiểu có đủ không
     */
    public ValidationResult validateVoucher(String voucherCode, double orderAmount) {
        // Trường hợp không có voucher
        if (voucherCode == null || voucherCode.trim().isEmpty()) {
            return new ValidationResult(true, "Không sử dụng voucher");
        }

        // Kiểm tra Voucher tồn tại
        Voucher voucher = voucherDAO.getVoucherByCode(voucherCode);
        if (voucher == null) {
            return new ValidationResult(false, "Mã voucher không hợp lệ!");
        }

        // Kiểm tra Voucher active
        if (!"Active".equalsIgnoreCase(voucher.getStatus())) {
            return new ValidationResult(false, "Voucher này không còn hoạt động!");
        }

        // Kiểm tra Voucher đã hết hạn
        if (isVoucherExpired(voucher.getExpireDate())) {
            return new ValidationResult(false, "Voucher này đã hết hạn!");
        }

        // Kiểm tra số lượng còn lại
        if (voucher.getUsedCount() >= voucher.getQuantity()) {
            return new ValidationResult(false, "Voucher này đã được sử dụng hết!");
        }

        // Kiểm tra số tiền tối thiểu
        if (orderAmount < voucher.getMinimumAmount()) {
            return new ValidationResult(false, 
                String.format("Số tiền không đủ để sử dụng voucher này! Tối thiểu: %.0f", 
                    voucher.getMinimumAmount()));
        }

        return new ValidationResult(true, "Voucher hợp lệ");
    }

    /**
     * Xác thực toàn bộ yêu cầu thanh toán
     */
    public ValidationResult validateCheckout(String orderId, double amount, String voucherCode) {
        // Bước 1: Xác thực Order
        ValidationResult orderValidation = validateOrder(orderId, amount);
        if (!orderValidation.isValid()) {
            return orderValidation;
        }

        // Bước 2: Xác thực Voucher (nếu có)
        ValidationResult voucherValidation = validateVoucher(voucherCode, amount);
        if (!voucherValidation.isValid()) {
            return voucherValidation;
        }

        return new ValidationResult(true, "Yêu cầu thanh toán hợp lệ");
    }

    /**
     * Xác thực phương thức thanh toán
     */
    public ValidationResult validatePaymentMethod(String paymentMethod) {
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            return new ValidationResult(false, "Phương thức thanh toán không được để trống!");
        }

        String method = paymentMethod.trim().toUpperCase();
        if (!method.equals("MOMO") && !method.equals("BANK_TRANSFER")) {
            return new ValidationResult(false, 
                "Phương thức thanh toán không hỗ trợ! Chỉ hỗ trợ: MOMO, BANK_TRANSFER");
        }

        return new ValidationResult(true, "Phương thức thanh toán hợp lệ");
    }

    /**
     * Xác thực amount input
     */
    public ValidationResult validateAmount(double amount) {
        if (amount <= 0) {
            return new ValidationResult(false, "Số tiền thanh toán phải lớn hơn 0!");
        }

        // Giới hạn tối đa
        if (amount > 999999999) {
            return new ValidationResult(false, "Số tiền thanh toán vượt quá giới hạn!");
        }

        return new ValidationResult(true, "Số tiền hợp lệ");
    }

    /**
     * Kiểm tra Voucher đã hết hạn
     */
    private boolean isVoucherExpired(String expireDate) {
        try {
            java.time.LocalDate expire = java.time.LocalDate.parse(expireDate);
            java.time.LocalDate today = java.time.LocalDate.now();
            return today.isAfter(expire);
        } catch (Exception e) {
            e.printStackTrace();
            return true; // Coi như hết hạn nếu không parse được
        }
    }

    /**
     * Inner class để trả về kết quả validation
     */
    public static class ValidationResult {
        private boolean valid;
        private String message;

        public ValidationResult(boolean valid, String message) {
            this.valid = valid;
            this.message = message;
        }

        public boolean isValid() {
            return valid;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "ValidationResult{" +
                    "valid=" + valid +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
