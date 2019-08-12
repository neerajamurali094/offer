package com.diviso.graeshoppe.offer.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TargetType entity.
 */
public class TargetTypeDTO implements Serializable {

    private Long id;

    private String targetType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TargetTypeDTO targetTypeDTO = (TargetTypeDTO) o;
        if (targetTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), targetTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TargetTypeDTO{" +
            "id=" + getId() +
            ", targetType='" + getTargetType() + "'" +
            "}";
    }
}
