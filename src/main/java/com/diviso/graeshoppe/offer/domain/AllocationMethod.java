package com.diviso.graeshoppe.offer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * AllocationMethod entity.
 * @author Anjali
 */
@ApiModel(description = "AllocationMethod entity. @author Anjali")
@Entity
@Table(name = "allocation_method")
@Document(indexName = "allocationmethod")
public class AllocationMethod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * shipping-line :each  line-item:across
     */
    @ApiModelProperty(value = "shipping-line :each  line-item:across")
    @Column(name = "allocation_method")
    private String allocationMethod;

    @OneToMany(mappedBy = "allocationMethod")
    private Set<PriceRule> priceRules = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAllocationMethod() {
        return allocationMethod;
    }

    public AllocationMethod allocationMethod(String allocationMethod) {
        this.allocationMethod = allocationMethod;
        return this;
    }

    public void setAllocationMethod(String allocationMethod) {
        this.allocationMethod = allocationMethod;
    }

    public Set<PriceRule> getPriceRules() {
        return priceRules;
    }

    public AllocationMethod priceRules(Set<PriceRule> priceRules) {
        this.priceRules = priceRules;
        return this;
    }

    public AllocationMethod addPriceRule(PriceRule priceRule) {
        this.priceRules.add(priceRule);
        priceRule.setAllocationMethod(this);
        return this;
    }

    public AllocationMethod removePriceRule(PriceRule priceRule) {
        this.priceRules.remove(priceRule);
        priceRule.setAllocationMethod(null);
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
        AllocationMethod allocationMethod = (AllocationMethod) o;
        if (allocationMethod.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), allocationMethod.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AllocationMethod{" +
            "id=" + getId() +
            ", allocationMethod='" + getAllocationMethod() + "'" +
            "}";
    }
}
