package com.diviso.graeshoppe.offer.service.mapper;

import com.diviso.graeshoppe.offer.domain.*;
import com.diviso.graeshoppe.offer.service.dto.OfferDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Offer and its DTO OfferDTO.
 */
@Mapper(componentModel = "spring", uses = {PriceRuleMapper.class, PaymentRuleMapper.class, OrderRuleMapper.class})
public interface OfferMapper extends EntityMapper<OfferDTO, Offer> {

    @Mapping(source = "priceRule.id", target = "priceRuleId")
    @Mapping(source = "paymentRule.id", target = "paymentRuleId")
    @Mapping(source = "orderRule.id", target = "orderRuleId")
    OfferDTO toDto(Offer offer);

    @Mapping(source = "priceRuleId", target = "priceRule")
    @Mapping(source = "paymentRuleId", target = "paymentRule")
    @Mapping(source = "orderRuleId", target = "orderRule")
    @Mapping(target = "stores", ignore = true)
    @Mapping(target = "offerTargets", ignore = true)
    @Mapping(target = "offerTargetCategories", ignore = true)
    @Mapping(target = "countries", ignore = true)
    Offer toEntity(OfferDTO offerDTO);

    default Offer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Offer offer = new Offer();
        offer.setId(id);
        return offer;
    }
}
