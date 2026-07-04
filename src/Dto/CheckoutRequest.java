package com.gymsystem.payment.dto;

/**
 * DTO để nhận request thanh toán từ client
 * Chứa thông tin cần thiết để khởi tạo một giao dịch thanh toán
 */
public class CheckoutRequest {
    
    private String orderId;              // ID đơn hàng
    private String voucherCode;          // Mã voucher (optional)
    private String paymentMethod;        // MOMO hoặc BANK_TRANSFER
    private double amount;               // Số tiền thanh toán
    private String description;          // Mô tả giao dịch
    private String notifyUrl;            // Webhook URL để nhận thông báo

    // Constructor mặc định
    public CheckoutRequest() {
    }

    // Constructor đầy đủ
    public CheckoutRequest(String orderId, String paymentMethod, double amount) {
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }

    // Getters & Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    /**
     * Validate request trước khi xử lý
     */
    public boolean isValid() {
        return orderId != null && !orderId.trim().isEmpty()
                && paymentMethod != null && !paymentMethod.trim().isEmpty()
                && amount > 0;
    }

    @Override
    public String toString() {
        return "CheckoutRequest{" +
                "orderId='" + orderId + '\'' +
                ", voucherCode='" + voucherCode + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", amount=" + amount +
                '}';
    }
}
