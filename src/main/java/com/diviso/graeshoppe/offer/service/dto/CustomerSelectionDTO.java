package com.diviso.graeshoppe.offer.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CustomerSelection entity.
 */
public class CustomerSelectionDTO implements Serializable {

    private Long id;

    private String customerSelectionType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerSelectionType() {
        return customerSelectionType;
    }

    public void setCustomerSelectionType(String customerSelectionType) {
        this.customerSelectionType = customerSelectionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomerSelectionDTO customerSelectionDTO = (CustomerSelectionDTO) o;
        if (customerSelectionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerSelectionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerSelectionDTO{" +
            "id=" + getId() +
            ", customerSelectionType='" + getCustomerSelectionType() + "'" +
            "}";
    }
}
