package com.diviso.graeshoppe.offer.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * PaymentRule entity.
 * payment rules of the offer
 * @author Anjali
 */
@ApiModel(description = "PaymentRule entity. payment rules of the offer @author Anjali")
@Entity
@Table(name = "payment_rule")
@Document(indexName = "paymentrule")
public class PaymentRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_mode")
    private String paymentMode;

    @Column(name = "authorized_provider")
    private String authorizedProvider;

    @Column(name = "cash_back_type")
    private Long cashBackType;

    @Column(name = "cash_back_value")
    private Long cashBackValue;

    @Column(name = "number_of_transaction_limit")
    private Long numberOfTransactionLimit;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public PaymentRule paymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
        return this;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getAuthorizedProvider() {
        return authorizedProvider;
    }

    public PaymentRule authorizedProvider(String authorizedProvider) {
        this.authorizedProvider = authorizedProvider;
        return this;
    }

    public void setAuthorizedProvider(String authorizedProvider) {
        this.authorizedProvider = authorizedProvider;
    }

    public Long getCashBackType() {
        return cashBackType;
    }

    public PaymentRule cashBackType(Long cashBackType) {
        this.cashBackType = cashBackType;
        return this;
    }

    public void setCashBackType(Long cashBackType) {
        this.cashBackType = cashBackType;
    }

    public Long getCashBackValue() {
        return cashBackValue;
    }

    public PaymentRule cashBackValue(Long cashBackValue) {
        this.cashBackValue = cashBackValue;
        return this;
    }

    public void setCashBackValue(Long cashBackValue) {
        this.cashBackValue = cashBackValue;
    }

    public Long getNumberOfTransactionLimit() {
        return numberOfTransactionLimit;
    }

    public PaymentRule numberOfTransactionLimit(Long numberOfTransactionLimit) {
        this.numberOfTransactionLimit = numberOfTransactionLimit;
        return this;
    }

    public void setNumberOfTransactionLimit(Long numberOfTransactionLimit) {
        this.numberOfTransactionLimit = numberOfTransactionLimit;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentRule paymentRule = (PaymentRule) o;
        if (paymentRule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentRule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentRule{" +
            "id=" + getId() +
            ", paymentMode='" + getPaymentMode() + "'" +
            ", authorizedProvider='" + getAuthorizedProvider() + "'" +
            ", cashBackType=" + getCashBackType() +
            ", cashBackValue=" + getCashBackValue() +
            ", numberOfTransactionLimit=" + getNumberOfTransactionLimit() +
            "}";
    }
}
