package com.diviso.graeshoppe.offer.repository;

import com.diviso.graeshoppe.offer.domain.PriceRule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PriceRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PriceRuleRepository extends JpaRepository<PriceRule, Long> {

}
