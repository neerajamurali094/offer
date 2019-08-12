package com.diviso.graeshoppe.offer.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AllocationMethod entity.
 */
public class AllocationMethodDTO implements Serializable {

    private Long id;

    private String allocationMethod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAllocationMethod() {
        return allocationMethod;
    }

    public void setAllocationMethod(String allocationMethod) {
        this.allocationMethod = allocationMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AllocationMethodDTO allocationMethodDTO = (AllocationMethodDTO) o;
        if (allocationMethodDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), allocationMethodDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AllocationMethodDTO{" +
            "id=" + getId() +
            ", allocationMethod='" + getAllocationMethod() + "'" +
            "}";
    }
}
