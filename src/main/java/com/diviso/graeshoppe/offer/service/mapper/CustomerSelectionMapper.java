package com.diviso.graeshoppe.offer.service.mapper;

import com.diviso.graeshoppe.offer.domain.*;
import com.diviso.graeshoppe.offer.service.dto.CustomerSelectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CustomerSelection and its DTO CustomerSelectionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomerSelectionMapper extends EntityMapper<CustomerSelectionDTO, CustomerSelection> {


    @Mapping(target = "priceRules", ignore = true)
    CustomerSelection toEntity(CustomerSelectionDTO customerSelectionDTO);

    default CustomerSelection fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerSelection customerSelection = new CustomerSelection();
        customerSelection.setId(id);
        return customerSelection;
    }
}
