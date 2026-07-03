package com.gymsystem.payment.model;

public class MomoPayment extends Payment {

	public MomoPayment(String paymentId, String orderId, double amount) {
		super(paymentId, orderId, amount);
	}

	@Override
	public void processPayment() {
		// Logic thực tế: Gọi API đối tác Momo để lấy link thanh toán/QR Code
		System.out.println("[MOMO API] Đang khởi tạo link thanh toán qua Ví MoMo...");
	}

}
