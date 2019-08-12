package com.diviso.graeshoppe.offer.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

import com.diviso.graeshoppe.offer.domain.DeductionValueType;

/**
 * A DTO for the PriceRule entity.
 */
public class PriceRuleDTO implements Serializable {

    private Long id;

    private Long deductionValue;

    private Long allocationLimit;

    private Boolean oncePerCustomer;

    private Long usageLimit;

    private Instant startDate;

    private Instant endDate;

    private Instant createdDate;

    private Instant updatedDate;

    private Double prerequisiteSubtotalRange;

    private Double prerequisiteQuantityRange;

    private Double prerequisiteShippingPriceRange;

    private Long targetTypeId;

    private Long deductionValueTypeId;

    private Long customerSelectionId;

    private Long allocationMethodId;
    
   /* private String deductionType;

    public String getDeductionType() {
		return deductionType;
	}

	public void setDeductionType(String deductionType) {
		this.deductionType = deductionType;
	}*/

	public Boolean getOncePerCustomer() {
		return oncePerCustomer;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeductionValue() {
        return deductionValue;
    }

    public void setDeductionValue(Long deductionValue) {
        this.deductionValue = deductionValue;
    }

    public Long getAllocationLimit() {
        return allocationLimit;
    }

    public void setAllocationLimit(Long allocationLimit) {
        this.allocationLimit = allocationLimit;
    }

    public Boolean isOncePerCustomer() {
        return oncePerCustomer;
    }

    public void setOncePerCustomer(Boolean oncePerCustomer) {
        this.oncePerCustomer = oncePerCustomer;
    }

    public Long getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(Long usageLimit) {
        this.usageLimit = usageLimit;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
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

    public Double getPrerequisiteSubtotalRange() {
        return prerequisiteSubtotalRange;
    }

    public void setPrerequisiteSubtotalRange(Double prerequisiteSubtotalRange) {
        this.prerequisiteSubtotalRange = prerequisiteSubtotalRange;
    }

    public Double getPrerequisiteQuantityRange() {
        return prerequisiteQuantityRange;
    }

    public void setPrerequisiteQuantityRange(Double prerequisiteQuantityRange) {
        this.prerequisiteQuantityRange = prerequisiteQuantityRange;
    }

    public Double getPrerequisiteShippingPriceRange() {
        return prerequisiteShippingPriceRange;
    }

    public void setPrerequisiteShippingPriceRange(Double prerequisiteShippingPriceRange) {
        this.prerequisiteShippingPriceRange = prerequisiteShippingPriceRange;
    }

    public Long getTargetTypeId() {
        return targetTypeId;
    }

    public void setTargetTypeId(Long targetTypeId) {
        this.targetTypeId = targetTypeId;
    }

    public Long getDeductionValueTypeId() {
        return deductionValueTypeId;
    }

    public void setDeductionValueTypeId(Long deductionValueTypeId) {
        this.deductionValueTypeId = deductionValueTypeId;
    }

    public Long getCustomerSelectionId() {
        return customerSelectionId;
    }

    public void setCustomerSelectionId(Long customerSelectionId) {
        this.customerSelectionId = customerSelectionId;
    }

    public Long getAllocationMethodId() {
        return allocationMethodId;
    }

    public void setAllocationMethodId(Long allocationMethodId) {
        this.allocationMethodId = allocationMethodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PriceRuleDTO priceRuleDTO = (PriceRuleDTO) o;
        if (priceRuleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), priceRuleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PriceRuleDTO{" +
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
            ", targetType=" + getTargetTypeId() +
            ", deductionValueType=" + getDeductionValueTypeId() +
            ", customerSelection=" + getCustomerSelectionId() +
            ", allocationMethod=" + getAllocationMethodId() +
            "}";
    }
}
