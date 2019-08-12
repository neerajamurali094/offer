package com.diviso.graeshoppe.offer.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * OrderRule entity.
 * order rule of the offer
 * @author Anjali
 */
@ApiModel(description = "OrderRule entity. order rule of the offer @author Anjali")
@Entity
@Table(name = "order_rule")
@Document(indexName = "orderrule")
public class OrderRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prerequisite_order_number")
    private Long prerequisiteOrderNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrerequisiteOrderNumber() {
        return prerequisiteOrderNumber;
    }

    public OrderRule prerequisiteOrderNumber(Long prerequisiteOrderNumber) {
        this.prerequisiteOrderNumber = prerequisiteOrderNumber;
        return this;
    }

    public void setPrerequisiteOrderNumber(Long prerequisiteOrderNumber) {
        this.prerequisiteOrderNumber = prerequisiteOrderNumber;
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
        OrderRule orderRule = (OrderRule) o;
        if (orderRule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderRule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderRule{" +
            "id=" + getId() +
            ", prerequisiteOrderNumber=" + getPrerequisiteOrderNumber() +
            "}";
    }
}
