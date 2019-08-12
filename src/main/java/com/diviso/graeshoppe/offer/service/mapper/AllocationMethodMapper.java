package com.diviso.graeshoppe.offer.service.mapper;

import com.diviso.graeshoppe.offer.domain.*;
import com.diviso.graeshoppe.offer.service.dto.AllocationMethodDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AllocationMethod and its DTO AllocationMethodDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AllocationMethodMapper extends EntityMapper<AllocationMethodDTO, AllocationMethod> {


    @Mapping(target = "priceRules", ignore = true)
    AllocationMethod toEntity(AllocationMethodDTO allocationMethodDTO);

    default AllocationMethod fromId(Long id) {
        if (id == null) {
            return null;
        }
        AllocationMethod allocationMethod = new AllocationMethod();
        allocationMethod.setId(id);
        return allocationMethod;
    }
}
