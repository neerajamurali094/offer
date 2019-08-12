package com.diviso.graeshoppe.offer.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DeductionValueType entity.
 */
public class DeductionValueTypeDTO implements Serializable {

    private Long id;

    private String deductionValueType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeductionValueType() {
        return deductionValueType;
    }

    public void setDeductionValueType(String deductionValueType) {
        this.deductionValueType = deductionValueType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeductionValueTypeDTO deductionValueTypeDTO = (DeductionValueTypeDTO) o;
        if (deductionValueTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deductionValueTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeductionValueTypeDTO{" +
            "id=" + getId() +
            ", deductionValueType='" + getDeductionValueType() + "'" +
            "}";
    }
}
