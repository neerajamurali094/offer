package com.diviso.graeshoppe.offer.service.mapper;

import com.diviso.graeshoppe.offer.domain.*;
import com.diviso.graeshoppe.offer.service.dto.OrderRuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrderRule and its DTO OrderRuleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrderRuleMapper extends EntityMapper<OrderRuleDTO, OrderRule> {



    default OrderRule fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderRule orderRule = new OrderRule();
        orderRule.setId(id);
        return orderRule;
    }
}
