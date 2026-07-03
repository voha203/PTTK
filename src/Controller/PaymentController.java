package com.gymsystem.payment.controller;

import com.gymsystem.payment.dto.CheckoutRequest;
import com.gymsystem.payment.model.Payment;
import com.gymsystem.payment.service.PaymentService;

// Giả lập Annotation của Spring Boot: @RestController @RequestMapping("/api/v1/payments")
public class PaymentController {

	private final PaymentService paymentService = new PaymentService();

	// Giả lập Endpoint: @PostMapping("/checkout")
	public String createPayment(CheckoutRequest request) {
		try {
			Payment payment = paymentService.executeCheckout(request);
			return "{ \"status\": \"SUCCESS\", \"message\": \"Khởi tạo thanh toán thành công\", \"paymentId\": \""
					+ payment.getPaymentId() + "\", \"amount\": " + payment.getAmount() + " }";
		} catch (Exception e) {
			return "{ \"status\": \"ERROR\", \"message\": \"" + e.getMessage() + "\" }";
		}
	}

	// Giả lập Endpoint nhận Webhook/IPN: @PostMapping("/webhook")
	public String receivePaymentWebhook(String orderId, boolean status) {
		paymentService.processWebhook(orderId, status);
		return "{ \"status\": \"ACKNOWLEDGED\" }"; // Phản hồi cho Momo/Bank biết Server đã nhận được tin
	}
}