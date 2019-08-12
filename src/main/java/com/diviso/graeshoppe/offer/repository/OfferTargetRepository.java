package com.diviso.graeshoppe.offer.repository;

import com.diviso.graeshoppe.offer.domain.OfferTarget;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OfferTarget entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferTargetRepository extends JpaRepository<OfferTarget, Long> {

}
