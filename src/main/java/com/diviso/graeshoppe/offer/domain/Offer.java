package com.diviso.graeshoppe.offer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Offer entity.
 * entity to store the offer
 * @author Anjali
 */
@ApiModel(description = "Offer entity. entity to store the offer @author Anjali")
@Entity
@Table(name = "offer")
@Document(indexName = "offer")
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "promo_code")
    private String promoCode;

    @Column(name = "usage_count")
    private Long usageCount;

    @OneToOne    @JoinColumn(unique = true)
    private PriceRule priceRule;

    @OneToOne    @JoinColumn(unique = true)
    private PaymentRule paymentRule;

    @OneToOne    @JoinColumn(unique = true)
    private OrderRule orderRule;

    @OneToMany(mappedBy = "offer")
    private Set<Store> stores = new HashSet<>();
    @OneToMany(mappedBy = "offer")
    private Set<OfferTarget> offerTargets = new HashSet<>();
    @OneToMany(mappedBy = "offer")
    private Set<OfferTargetCategory> offerTargetCategories = new HashSet<>();
    @OneToMany(mappedBy = "offer")
    private Set<Country> countries = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Offer title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Offer description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Offer createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public Offer updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public Offer promoCode(String promoCode) {
        this.promoCode = promoCode;
        return this;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public Long getUsageCount() {
        return usageCount;
    }

    public Offer usageCount(Long usageCount) {
        this.usageCount = usageCount;
        return this;
    }

    public void setUsageCount(Long usageCount) {
        this.usageCount = usageCount;
    }

    public PriceRule getPriceRule() {
        return priceRule;
    }

    public Offer priceRule(PriceRule priceRule) {
        this.priceRule = priceRule;
        return this;
    }

    public void setPriceRule(PriceRule priceRule) {
        this.priceRule = priceRule;
    }

    public PaymentRule getPaymentRule() {
        return paymentRule;
    }

    public Offer paymentRule(PaymentRule paymentRule) {
        this.paymentRule = paymentRule;
        return this;
    }

    public void setPaymentRule(PaymentRule paymentRule) {
        this.paymentRule = paymentRule;
    }

    public OrderRule getOrderRule() {
        return orderRule;
    }

    public Offer orderRule(OrderRule orderRule) {
        this.orderRule = orderRule;
        return this;
    }

    public void setOrderRule(OrderRule orderRule) {
        this.orderRule = orderRule;
    }

    public Set<Store> getStores() {
        return stores;
    }

    public Offer stores(Set<Store> stores) {
        this.stores = stores;
        return this;
    }

    public Offer addStores(Store store) {
        this.stores.add(store);
        store.setOffer(this);
        return this;
    }

    public Offer removeStores(Store store) {
        this.stores.remove(store);
        store.setOffer(null);
        return this;
    }

    public void setStores(Set<Store> stores) {
        this.stores = stores;
    }

    public Set<OfferTarget> getOfferTargets() {
        return offerTargets;
    }

    public Offer offerTargets(Set<OfferTarget> offerTargets) {
        this.offerTargets = offerTargets;
        return this;
    }

    public Offer addOfferTargets(OfferTarget offerTarget) {
        this.offerTargets.add(offerTarget);
        offerTarget.setOffer(this);
        return this;
    }

    public Offer removeOfferTargets(OfferTarget offerTarget) {
        this.offerTargets.remove(offerTarget);
        offerTarget.setOffer(null);
        return this;
    }

    public void setOfferTargets(Set<OfferTarget> offerTargets) {
        this.offerTargets = offerTargets;
    }

    public Set<OfferTargetCategory> getOfferTargetCategories() {
        return offerTargetCategories;
    }

    public Offer offerTargetCategories(Set<OfferTargetCategory> offerTargetCategories) {
        this.offerTargetCategories = offerTargetCategories;
        return this;
    }

    public Offer addOfferTargetCategories(OfferTargetCategory offerTargetCategory) {
        this.offerTargetCategories.add(offerTargetCategory);
        offerTargetCategory.setOffer(this);
        return this;
    }

    public Offer removeOfferTargetCategories(OfferTargetCategory offerTargetCategory) {
        this.offerTargetCategories.remove(offerTargetCategory);
        offerTargetCategory.setOffer(null);
        return this;
    }

    public void setOfferTargetCategories(Set<OfferTargetCategory> offerTargetCategories) {
        this.offerTargetCategories = offerTargetCategories;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public Offer countries(Set<Country> countries) {
        this.countries = countries;
        return this;
    }

    public Offer addCountries(Country country) {
        this.countries.add(country);
        country.setOffer(this);
        return this;
    }

    public Offer removeCountries(Country country) {
        this.countries.remove(country);
        country.setOffer(null);
        return this;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
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
        Offer offer = (Offer) o;
        if (offer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), offer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Offer{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", promoCode='" + getPromoCode() + "'" +
            ", usageCount=" + getUsageCount() +
            "}";
    }
}
