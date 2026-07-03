package com.gymsystem.payment.model;

import java.time.LocalDateTime;

public abstract class Payment {
	private String paymentId;
	private String orderId;
	private double amount;
	private String paymentStatus; // PENDING, SUCCESS, FAILED
	private LocalDateTime paymentTime;

	public Payment(String paymentId, String orderId, double amount) {
		this.paymentId = paymentId;
		this.orderId = orderId;
		this.amount = amount;
		this.paymentStatus = "PENDING";
		this.paymentTime = LocalDateTime.now();
	}

	public abstract void processPayment();

	// Getters & Setters
	public String getPaymentId() {
		return paymentId;
	}

	public String getOrderId() {
		return orderId;
	}

	public double getAmount() {
		return amount;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
}