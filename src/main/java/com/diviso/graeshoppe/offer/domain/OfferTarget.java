package com.diviso.graeshoppe.offer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * OfferTarget entity.
 * to store the products with offers
 * @author Anjali
 */
@ApiModel(description = "OfferTarget entity. to store the products with offers @author Anjali")
@Entity
@Table(name = "offer_target")
@Document(indexName = "offertarget")
public class OfferTarget implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "target_id")
    private Long targetId;

    @ManyToOne
    @JsonIgnoreProperties("offerTargets")
    private Offer offer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTargetId() {
        return targetId;
    }

    public OfferTarget targetId(Long targetId) {
        this.targetId = targetId;
        return this;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Offer getOffer() {
        return offer;
    }

    public OfferTarget offer(Offer offer) {
        this.offer = offer;
        return this;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
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
        OfferTarget offerTarget = (OfferTarget) o;
        if (offerTarget.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), offerTarget.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OfferTarget{" +
            "id=" + getId() +
            ", targetId=" + getTargetId() +
            "}";
    }
}
