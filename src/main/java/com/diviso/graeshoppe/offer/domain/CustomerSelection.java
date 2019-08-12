package com.diviso.graeshoppe.offer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * CustomerSelection entity.
 * entity represents all users or not
 * @author Anjali
 */
@ApiModel(description = "CustomerSelection entity. entity represents all users or not @author Anjali")
@Entity
@Table(name = "customer_selection")
@Document(indexName = "customerselection")
public class CustomerSelection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_selection_type")
    private String customerSelectionType;

    @OneToMany(mappedBy = "customerSelection")
    private Set<PriceRule> priceRules = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerSelectionType() {
        return customerSelectionType;
    }

    public CustomerSelection customerSelectionType(String customerSelectionType) {
        this.customerSelectionType = customerSelectionType;
        return this;
    }

    public void setCustomerSelectionType(String customerSelectionType) {
        this.customerSelectionType = customerSelectionType;
    }

    public Set<PriceRule> getPriceRules() {
        return priceRules;
    }

    public CustomerSelection priceRules(Set<PriceRule> priceRules) {
        this.priceRules = priceRules;
        return this;
    }

    public CustomerSelection addPriceRule(PriceRule priceRule) {
        this.priceRules.add(priceRule);
        priceRule.setCustomerSelection(this);
        return this;
    }

    public CustomerSelection removePriceRule(PriceRule priceRule) {
        this.priceRules.remove(priceRule);
        priceRule.setCustomerSelection(null);
        return this;
    }

    public void setPriceRules(Set<PriceRule> priceRules) {
        this.priceRules = priceRules;
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
        CustomerSelection customerSelection = (CustomerSelection) o;
        if (customerSelection.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerSelection.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerSelection{" +
            "id=" + getId() +
            ", customerSelectionType='" + getCustomerSelectionType() + "'" +
            "}";
    }
}
