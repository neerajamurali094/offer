package com.diviso.graeshoppe.offer.service.mapper;

import com.diviso.graeshoppe.offer.domain.*;
import com.diviso.graeshoppe.offer.service.dto.PaymentRuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PaymentRule and its DTO PaymentRuleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaymentRuleMapper extends EntityMapper<PaymentRuleDTO, PaymentRule> {



    default PaymentRule fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentRule paymentRule = new PaymentRule();
        paymentRule.setId(id);
        return paymentRule;
    }
}
