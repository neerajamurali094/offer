package com.diviso.graeshoppe.offer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * OfferTargetCategory entity.
 * to store the product category with offers
 * @author Anjali
 */
@ApiModel(description = "OfferTargetCategory entity. to store the product category with offers @author Anjali")
@Entity
@Table(name = "offer_target_category")
@Document(indexName = "offertargetcategory")
public class OfferTargetCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "target_category_id")
    private Long targetCategoryId;

    @ManyToOne
    @JsonIgnoreProperties("offerTargetCategories")
    private Offer offer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTargetCategoryId() {
        return targetCategoryId;
    }

    public OfferTargetCategory targetCategoryId(Long targetCategoryId) {
        this.targetCategoryId = targetCategoryId;
        return this;
    }

    public void setTargetCategoryId(Long targetCategoryId) {
        this.targetCategoryId = targetCategoryId;
    }

    public Offer getOffer() {
        return offer;
    }

    public OfferTargetCategory offer(Offer offer) {
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
        OfferTargetCategory offerTargetCategory = (OfferTargetCategory) o;
        if (offerTargetCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), offerTargetCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OfferTargetCategory{" +
            "id=" + getId() +
            ", targetCategoryId=" + getTargetCategoryId() +
            "}";
    }
}
