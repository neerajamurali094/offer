package com.diviso.graeshoppe.offer.service.mapper;

import com.diviso.graeshoppe.offer.domain.*;
import com.diviso.graeshoppe.offer.service.dto.OfferTargetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OfferTarget and its DTO OfferTargetDTO.
 */
@Mapper(componentModel = "spring", uses = {OfferMapper.class})
public interface OfferTargetMapper extends EntityMapper<OfferTargetDTO, OfferTarget> {

    @Mapping(source = "offer.id", target = "offerId")
    OfferTargetDTO toDto(OfferTarget offerTarget);

    @Mapping(source = "offerId", target = "offer")
    OfferTarget toEntity(OfferTargetDTO offerTargetDTO);

    default OfferTarget fromId(Long id) {
        if (id == null) {
            return null;
        }
        OfferTarget offerTarget = new OfferTarget();
        offerTarget.setId(id);
        return offerTarget;
    }
}
