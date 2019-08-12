package com.diviso.graeshoppe.offer.service.mapper;

import com.diviso.graeshoppe.offer.domain.*;
import com.diviso.graeshoppe.offer.service.dto.PriceRuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PriceRule and its DTO PriceRuleDTO.
 */
@Mapper(componentModel = "spring", uses = {TargetTypeMapper.class, DeductionValueTypeMapper.class, CustomerSelectionMapper.class, AllocationMethodMapper.class})
public interface PriceRuleMapper extends EntityMapper<PriceRuleDTO, PriceRule> {

    @Mapping(source = "targetType.id", target = "targetTypeId")
    @Mapping(source = "deductionValueType.id", target = "deductionValueTypeId")
    @Mapping(source = "customerSelection.id", target = "customerSelectionId")
    @Mapping(source = "allocationMethod.id", target = "allocationMethodId")
    PriceRuleDTO toDto(PriceRule priceRule);

    @Mapping(source = "targetTypeId", target = "targetType")
    @Mapping(source = "deductionValueTypeId", target = "deductionValueType")
    @Mapping(source = "customerSelectionId", target = "customerSelection")
    @Mapping(source = "allocationMethodId", target = "allocationMethod")
    PriceRule toEntity(PriceRuleDTO priceRuleDTO);

    default PriceRule fromId(Long id) {
        if (id == null) {
            return null;
        }
        PriceRule priceRule = new PriceRule();
        priceRule.setId(id);
        return priceRule;
    }
}
