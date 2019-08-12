package com.diviso.graeshoppe.offer.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PaymentRule entity.
 */
public class PaymentRuleDTO implements Serializable {

    private Long id;

    private String paymentMode;

    private String authorizedProvider;

    private Long cashBackType;

    private Long cashBackValue;

    private Long numberOfTransactionLimit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getAuthorizedProvider() {
        return authorizedProvider;
    }

    public void setAuthorizedProvider(String authorizedProvider) {
        this.authorizedProvider = authorizedProvider;
    }

    public Long getCashBackType() {
        return cashBackType;
    }

    public void setCashBackType(Long cashBackType) {
        this.cashBackType = cashBackType;
    }

    public Long getCashBackValue() {
        return cashBackValue;
    }

    public void setCashBackValue(Long cashBackValue) {
        this.cashBackValue = cashBackValue;
    }

    public Long getNumberOfTransactionLimit() {
        return numberOfTransactionLimit;
    }

    public void setNumberOfTransactionLimit(Long numberOfTransactionLimit) {
        this.numberOfTransactionLimit = numberOfTransactionLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaymentRuleDTO paymentRuleDTO = (PaymentRuleDTO) o;
        if (paymentRuleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentRuleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentRuleDTO{" +
            "id=" + getId() +
            ", paymentMode='" + getPaymentMode() + "'" +
            ", authorizedProvider='" + getAuthorizedProvider() + "'" +
            ", cashBackType=" + getCashBackType() +
            ", cashBackValue=" + getCashBackValue() +
            ", numberOfTransactionLimit=" + getNumberOfTransactionLimit() +
            "}";
    }
}
