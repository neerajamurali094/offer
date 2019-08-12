package com.diviso.graeshoppe.offer.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the OfferTarget entity.
 */
public class OfferTargetDTO implements Serializable {

    private Long id;

    private Long targetId;

    private Long offerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
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

        OfferTargetDTO offerTargetDTO = (OfferTargetDTO) o;
        if (offerTargetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), offerTargetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OfferTargetDTO{" +
            "id=" + getId() +
            ", targetId=" + getTargetId() +
            ", offer=" + getOfferId() +
            "}";
    }
}
