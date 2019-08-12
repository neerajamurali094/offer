package com.diviso.graeshoppe.offer.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the OfferTargetCategory entity.
 */
public class OfferTargetCategoryDTO implements Serializable {

    private Long id;

    private Long targetCategoryId;

    private Long offerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTargetCategoryId() {
        return targetCategoryId;
    }

    public void setTargetCategoryId(Long targetCategoryId) {
        this.targetCategoryId = targetCategoryId;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OfferTargetCategoryDTO offerTargetCategoryDTO = (OfferTargetCategoryDTO) o;
        if (offerTargetCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), offerTargetCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OfferTargetCategoryDTO{" +
            "id=" + getId() +
            ", targetCategoryId=" + getTargetCategoryId() +
            ", offer=" + getOfferId() +
            "}";
    }
}
