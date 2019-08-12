package com.diviso.graeshoppe.offer.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * TargetType entity.
 * @author Anjali
 */
@ApiModel(description = "TargetType entity. @author Anjali")
@Entity
@Table(name = "target_type")
@Document(indexName = "targettype")
public class TargetType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * line-item or shipping line
     */
    @ApiModelProperty(value = "line-item or shipping line")
    @Column(name = "target_type")
    private String targetType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTargetType() {
        return targetType;
    }

    public TargetType targetType(String targetType) {
        this.targetType = targetType;
        return this;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
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
        TargetType targetType = (TargetType) o;
        if (targetType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), targetType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TargetType{" +
            "id=" + getId() +
            ", targetType='" + getTargetType() + "'" +
            "}";
    }
}
