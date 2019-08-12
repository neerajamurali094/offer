package com.diviso.graeshoppe.offer.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Offer entity.
 */
public class OfferDTO implements Serializable {

    private Long id;

    private String title;

    private String description;

    private Instant createdDate;

    private Instant updatedDate;

    private String promoCode;

    private Long usageCount;

    private Long priceRuleId;

    private Long paymentRuleId;

    private Long orderRuleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public Long getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Long usageCount) {
        this.usageCount = usageCount;
    }

    public Long getPriceRuleId() {
        return priceRuleId;
    }

    public void setPriceRuleId(Long priceRuleId) {
        this.priceRuleId = priceRuleId;
    }

    public Long getPaymentRuleId() {
        return paymentRuleId;
    }

    public void setPaymentRuleId(Long paymentRuleId) {
        this.paymentRuleId = paymentRuleId;
    }

    public Long getOrderRuleId() {
        return orderRuleId;
    }

    public void setOrderRuleId(Long orderRuleId) {
        this.orderRuleId = orderRuleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OfferDTO offerDTO = (OfferDTO) o;
        if (offerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), offerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OfferDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", promoCode='" + getPromoCode() + "'" +
            ", usageCount=" + getUsageCount() +
            ", priceRule=" + getPriceRuleId() +
            ", paymentRule=" + getPaymentRuleId() +
            ", orderRule=" + getOrderRuleId() +
            "}";
    }
}
