package com.diviso.graeshoppe.offer.repository;

import com.diviso.graeshoppe.offer.domain.Offer;
import com.diviso.graeshoppe.offer.service.dto.OfferDTO;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Offer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
	
	public Optional<Offer> findByPromoCode(String promoCode);

}
