package com.diviso.graeshoppe.offer.model;

import java.time.Instant;

public class OrderModel {
	
	private String promoCode;

	private Long orderNumber;
	
	private Instant claimedDate;

	private Double orderTotal;
	
	private Double orderDiscountAmount;
	
	private Double orderDiscountTotal;
	
	public Double getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(Double orderTotal) {
		this.orderTotal = orderTotal;
	}

	public Double getOrderDiscountAmount() {
		return orderDiscountAmount;
	}

	public void setOrderDiscountAmount(Double orderDiscountAmount) {
		this.orderDiscountAmount = orderDiscountAmount;
	}

	public Double getOrderDiscountTotal() {
		return orderDiscountTotal;
	}

	public void setOrderDiscountTotal(Double orderDiscountTotal) {
		this.orderDiscountTotal = orderDiscountTotal;
	}

	public Instant getClaimedDate() {
		return claimedDate;
	}

	public void setClaimedDate(Instant claimedDate) {
		this.claimedDate = claimedDate;
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

}
