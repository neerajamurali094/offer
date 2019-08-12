package com.diviso.graeshoppe.offer.model;

import java.time.Instant;

public class OrderModel {
	
	private String promoCode;

	private Long orderNumber;
	
	private Instant claimedDate;

	private Long orderTotal;
	
	private Long orderDiscountAmount;
	
	private Long orderDiscountTotal;
	
	public Long getOrderDiscountAmount() {
		return orderDiscountAmount;
	}

	public void setOrderDiscountAmount(Long orderDiscountAmount) {
		this.orderDiscountAmount = orderDiscountAmount;
	}
	
	public Instant getClaimedDate() {
		return claimedDate;
	}

	public void setClaimedDate(Instant claimedDate) {
		this.claimedDate = claimedDate;
	}

	public Long getOrderDiscountTotal() {
		return orderDiscountTotal;
	}

	public void setOrderDiscountTotal(Long orderDiscountTotal) {
		this.orderDiscountTotal = orderDiscountTotal;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Long getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(Long orderTotal) {
		this.orderTotal = orderTotal;
	}
	
}
