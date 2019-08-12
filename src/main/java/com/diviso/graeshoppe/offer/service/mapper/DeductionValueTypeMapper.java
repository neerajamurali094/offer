package com.diviso.graeshoppe.offer.service.mapper;

import com.diviso.graeshoppe.offer.domain.*;
import com.diviso.graeshoppe.offer.service.dto.DeductionValueTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DeductionValueType and its DTO DeductionValueTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DeductionValueTypeMapper extends EntityMapper<DeductionValueTypeDTO, DeductionValueType> {


    @Mapping(target = "priceRules", ignore = true)
    DeductionValueType toEntity(DeductionValueTypeDTO deductionValueTypeDTO);

    default DeductionValueType fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeductionValueType deductionValueType = new DeductionValueType();
        deductionValueType.setId(id);
        return deductionValueType;
    }
}
