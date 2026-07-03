package com.gymsystem.payment.service;

import com.gymsystem.payment.model.*;
import com.gymsystem.payment.dto.CheckoutRequest;
import com.gymsystem.payment.Dao.PaymentRepository;
import java.util.UUID;

public class PaymentService {

	// Gọi tầng DAO/Repository để làm việc với DB
	private final PaymentRepository paymentRepository = new PaymentRepository();

	// Giả lập xử lý thanh toán (Trong thực tế sẽ tương tác thêm với Repositories)
	public Payment executeCheckout(CheckoutRequest request) {
		// 1. Giả lập tìm Đơn hàng từ DB lên dựa vào request.getOrderId()
		Order order = new Order(request.getOrderId(), 1200000); // Gói tập 1.200.000đ

		// 2. Bảo mật: Xử lý Voucher trực tiếp ở Backend
		if ("GYMSALE10".equals(request.getVoucherCode())) {
			double discount = order.getOriginalAmount() * 0.10; // Giảm 10%
			order.setFinalAmount(order.getOriginalAmount() - discount);
		}

		// 3. Khởi tạo phương thức thanh toán (Polymorphism)
		Payment payment;
		String paymentId = "PAY-" + UUID.randomUUID().toString().substring(0, 8);

		if ("MOMO".equalsIgnoreCase(request.getPaymentMethod())) {
			payment = new MomoPayment(paymentId, order.getOrderId(), order.getFinalAmount());
		} else if ("BANK_TRANSFER".equalsIgnoreCase(request.getPaymentMethod())) {
			payment = new BankTransferPayment(paymentId, order.getOrderId(), order.getFinalAmount());
		} else {
			throw new IllegalArgumentException("Phương thức thanh toán không hợp lệ!");
		}

		// 4. Gọi cổng thanh toán để tạo QR/Link giao dịch
		payment.processPayment();

		// 5. Lưu thông tin giao dịch PENDING vào Database qua DAO
		paymentRepository.save(payment);

		// 6. Lưu thông tin payment và order xuống Database ở đây...

		return payment;
	}

	public void processWebhook(String orderId, boolean isSuccess) {
		// Tìm kiếm giao dịch trong DB thông qua DAO
		Payment payment = paymentRepository.findByOrderId(orderId);
		if (payment != null) {
			if (isSuccess) {
				payment.setPaymentStatus("SUCCESS");
			} else {
				payment.setPaymentStatus("FAILED");
			}
			// Cập nhật lại trạng thái mới xuống Database
			paymentRepository.save(payment);
			System.out.println("[Webhook] Đã cập nhật DB cho đơn hàng " + orderId);
		} else {
			System.out.println("[Lỗi] Không tìm thấy giao dịch cho đơn hàng này trong DB!");
		}
		// Xử lý cập nhật trạng thái đơn hàng khi đối tác (Momo/Ngân hàng) gọi ngầm về
		// server
		System.out.println("[Webhook] Cập nhật trạng thái đơn " + orderId + ". Thành công: " + isSuccess);
		// Logic cập nhật trạng thái đơn hàng trong DB thành COMPLETED hoặc CANCELLED
	}
}