package com.diviso.graeshoppe.offer.repository;

import com.diviso.graeshoppe.offer.domain.PaymentRule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PaymentRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentRuleRepository extends JpaRepository<PaymentRule, Long> {

}
