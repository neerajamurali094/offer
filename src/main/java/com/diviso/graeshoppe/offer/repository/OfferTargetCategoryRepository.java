package com.diviso.graeshoppe.offer.repository;

import com.diviso.graeshoppe.offer.domain.OfferTargetCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OfferTargetCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferTargetCategoryRepository extends JpaRepository<OfferTargetCategory, Long> {

}
