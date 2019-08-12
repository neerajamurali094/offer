package com.diviso.graeshoppe.offer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * PriceRule entity.
 * represents the price rule of the offer
 * @author Anjali
 */
@ApiModel(description = "PriceRule entity. represents the price rule of the offer @author Anjali")
@Entity
@Table(name = "price_rule")
@Document(indexName = "pricerule")
public class PriceRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deduction_value")
    private Long deductionValue;

    @Column(name = "allocation_limit")
    private Long allocationLimit;

    @Column(name = "once_per_customer")
    private Boolean oncePerCustomer;

    @Column(name = "usage_limit")
    private Long usageLimit;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "prerequisite_subtotal_range")
    private Double prerequisiteSubtotalRange;

    @Column(name = "prerequisite_quantity_range")
    private Double prerequisiteQuantityRange;

    @Column(name = "prerequisite_shipping_price_range")
    private Double prerequisiteShippingPriceRange;

    @OneToOne    @JoinColumn(unique = true)
    private TargetType targetType;

    @ManyToOne
    @JsonIgnoreProperties("priceRules")
    private DeductionValueType deductionValueType;

    @ManyToOne
    @JsonIgnoreProperties("priceRules")
    private CustomerSelection customerSelection;

    @ManyToOne
    @JsonIgnoreProperties("priceRules")
    private AllocationMethod allocationMethod;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeductionValue() {
        return deductionValue;
    }

    public PriceRule deductionValue(Long deductionValue) {
        this.deductionValue = deductionValue;
        return this;
    }

    public void setDeductionValue(Long deductionValue) {
        this.deductionValue = deductionValue;
    }

    public Long getAllocationLimit() {
        return allocationLimit;
    }

    public PriceRule allocationLimit(Long allocationLimit) {
        this.allocationLimit = allocationLimit;
        return this;
    }

    public void setAllocationLimit(Long allocationLimit) {
        this.allocationLimit = allocationLimit;
    }

    public Boolean isOncePerCustomer() {
        return oncePerCustomer;
    }

    public PriceRule oncePerCustomer(Boolean oncePerCustomer) {
        this.oncePerCustomer = oncePerCustomer;
        return this;
    }

    public void setOncePerCustomer(Boolean oncePerCustomer) {
        this.oncePerCustomer = oncePerCustomer;
    }

    public Long getUsageLimit() {
        return usageLimit;
    }

    public PriceRule usageLimit(Long usageLimit) {
        this.usageLimit = usageLimit;
        return this;
    }

    public void setUsageLimit(Long usageLimit) {
        this.usageLimit = usageLimit;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public PriceRule startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public PriceRule endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public PriceRule createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public PriceRule updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Double getPrerequisiteSubtotalRange() {
        return prerequisiteSubtotalRange;
    }

    public PriceRule prerequisiteSubtotalRange(Double prerequisiteSubtotalRange) {
        this.prerequisiteSubtotalRange = prerequisiteSubtotalRange;
        return this;
    }

    public void setPrerequisiteSubtotalRange(Double prerequisiteSubtotalRange) {
        this.prerequisiteSubtotalRange = prerequisiteSubtotalRange;
    }

    public Double getPrerequisiteQuantityRange() {
        return prerequisiteQuantityRange;
    }

    public PriceRule prerequisiteQuantityRange(Double prerequisiteQuantityRange) {
        this.prerequisiteQuantityRange = prerequisiteQuantityRange;
        return this;
    }

    public void setPrerequisiteQuantityRange(Double prerequisiteQuantityRange) {
        this.prerequisiteQuantityRange = prerequisiteQuantityRange;
    }

    public Double getPrerequisiteShippingPriceRange() {
        return prerequisiteShippingPriceRange;
    }

    public PriceRule prerequisiteShippingPriceRange(Double prerequisiteShippingPriceRange) {
        this.prerequisiteShippingPriceRange = prerequisiteShippingPriceRange;
        return this;
    }

    public void setPrerequisiteShippingPriceRange(Double prerequisiteShippingPriceRange) {
        this.prerequisiteShippingPriceRange = prerequisiteShippingPriceRange;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public PriceRule targetType(TargetType targetType) {
        this.targetType = targetType;
        return this;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

    public DeductionValueType getDeductionValueType() {
        return deductionValueType;
    }

    public PriceRule deductionValueType(DeductionValueType deductionValueType) {
        this.deductionValueType = deductionValueType;
        return this;
    }

    public void setDeductionValueType(DeductionValueType deductionValueType) {
        this.deductionValueType = deductionValueType;
    }

    public CustomerSelection getCustomerSelection() {
        return customerSelection;
    }

    public PriceRule customerSelection(CustomerSelection customerSelection) {
        this.customerSelection = customerSelection;
        return this;
    }

    public void setCustomerSelection(CustomerSelection customerSelection) {
        this.customerSelection = customerSelection;
    }

    public AllocationMethod getAllocationMethod() {
        return allocationMethod;
    }

    public PriceRule allocationMethod(AllocationMethod allocationMethod) {
        this.allocationMethod = allocationMethod;
        return this;
    }

    public void setAllocationMethod(AllocationMethod allocationMethod) {
        this.allocationMethod = allocationMethod;
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
        PriceRule priceRule = (PriceRule) o;
        if (priceRule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), priceRule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PriceRule{" +
            "id=" + getId() +
            ", deductionValue=" + getDeductionValue() +
            ", allocationLimit=" + getAllocationLimit() +
            ", oncePerCustomer='" + isOncePerCustomer() + "'" +
            ", usageLimit=" + getUsageLimit() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", prerequisiteSubtotalRange=" + getPrerequisiteSubtotalRange() +
            ", prerequisiteQuantityRange=" + getPrerequisiteQuantityRange() +
            ", prerequisiteShippingPriceRange=" + getPrerequisiteShippingPriceRange() +
            "}";
    }
}
