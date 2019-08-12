package com.diviso.graeshoppe.offer.service.mapper;

import com.diviso.graeshoppe.offer.domain.*;
import com.diviso.graeshoppe.offer.service.dto.OfferTargetCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OfferTargetCategory and its DTO OfferTargetCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {OfferMapper.class})
public interface OfferTargetCategoryMapper extends EntityMapper<OfferTargetCategoryDTO, OfferTargetCategory> {

    @Mapping(source = "offer.id", target = "offerId")
    OfferTargetCategoryDTO toDto(OfferTargetCategory offerTargetCategory);

    @Mapping(source = "offerId", target = "offer")
    OfferTargetCategory toEntity(OfferTargetCategoryDTO offerTargetCategoryDTO);

    default OfferTargetCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        OfferTargetCategory offerTargetCategory = new OfferTargetCategory();
        offerTargetCategory.setId(id);
        return offerTargetCategory;
    }
}
