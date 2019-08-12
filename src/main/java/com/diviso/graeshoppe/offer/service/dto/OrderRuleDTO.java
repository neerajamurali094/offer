package com.diviso.graeshoppe.offer.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the OrderRule entity.
 */
public class OrderRuleDTO implements Serializable {

    private Long id;

    private Long prerequisiteOrderNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrerequisiteOrderNumber() {
        return prerequisiteOrderNumber;
    }

    public void setPrerequisiteOrderNumber(Long prerequisiteOrderNumber) {
        this.prerequisiteOrderNumber = prerequisiteOrderNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderRuleDTO orderRuleDTO = (OrderRuleDTO) o;
        if (orderRuleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderRuleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderRuleDTO{" +
            "id=" + getId() +
            ", prerequisiteOrderNumber=" + getPrerequisiteOrderNumber() +
            "}";
    }
}
