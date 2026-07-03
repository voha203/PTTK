package com.gymsystem.payment.model;

public class Order {
	private String orderId;
	private double originalAmount;
	private double finalAmount;
	private String orderStatus; // PENDING, COMPLETED, CANCELLED

	public Order(String orderId, double originalAmount) {
		this.orderId = orderId;
		this.originalAmount = originalAmount;
		this.finalAmount = originalAmount;
		this.orderStatus = "PENDING";
	}

	// Getters & Setters
	public String getOrderId() {
		return orderId;
	}

	public double getOriginalAmount() {
		return originalAmount;
	}

	public double getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(double finalAmount) {
		this.finalAmount = finalAmount;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
}
