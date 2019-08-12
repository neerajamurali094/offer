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
 * DeductionValueType entity.
 * represents amount or percentage type to deduct
 * @author Anjali
 */
@ApiModel(description = "DeductionValueType entity. represents amount or percentage type to deduct @author Anjali")
@Entity
@Table(name = "deduction_value_type")
@Document(indexName = "deductionvaluetype")
public class DeductionValueType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deduction_value_type")
    private String deductionValueType;

    @OneToMany(mappedBy = "deductionValueType")
    private Set<PriceRule> priceRules = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeductionValueType() {
        return deductionValueType;
    }

    public DeductionValueType deductionValueType(String deductionValueType) {
        this.deductionValueType = deductionValueType;
        return this;
    }

    public void setDeductionValueType(String deductionValueType) {
        this.deductionValueType = deductionValueType;
    }

    public Set<PriceRule> getPriceRules() {
        return priceRules;
    }

    public DeductionValueType priceRules(Set<PriceRule> priceRules) {
        this.priceRules = priceRules;
        return this;
    }

    public DeductionValueType addPriceRule(PriceRule priceRule) {
        this.priceRules.add(priceRule);
        priceRule.setDeductionValueType(this);
        return this;
    }

    public DeductionValueType removePriceRule(PriceRule priceRule) {
        this.priceRules.remove(priceRule);
        priceRule.setDeductionValueType(null);
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
        DeductionValueType deductionValueType = (DeductionValueType) o;
        if (deductionValueType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deductionValueType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeductionValueType{" +
            "id=" + getId() +
            ", deductionValueType='" + getDeductionValueType() + "'" +
            "}";
    }
}
