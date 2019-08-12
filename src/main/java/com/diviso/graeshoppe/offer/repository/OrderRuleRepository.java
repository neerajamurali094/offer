package com.diviso.graeshoppe.offer.repository;

import com.diviso.graeshoppe.offer.domain.OrderRule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrderRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderRuleRepository extends JpaRepository<OrderRule, Long> {

}
