package com.gymsystem.payment.model;

public class BankTransferPayment extends Payment {

	public BankTransferPayment(String paymentId, String orderId, double amount) {
		super(paymentId, orderId, amount);
	}

	@Override
	public void processPayment() {
		// Logic thực tế: Tạo mã VietQR theo chuẩn Napas247
		System.out.println("[BANK API] Đang tạo mã VietQR chuyển khoản ngân hàng...");
	}
}
