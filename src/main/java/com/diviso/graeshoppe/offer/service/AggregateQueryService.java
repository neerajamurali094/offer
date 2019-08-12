package com.diviso.graeshoppe.offer.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.diviso.graeshoppe.offer.service.dto.DeductionValueTypeDTO;
import com.diviso.graeshoppe.offer.service.dto.OfferDTO;

/**
 * Service Interface for managing Offer queries.
 */
public interface AggregateQueryService {

	/**
     * Get the "id" offer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OfferDTO> findOfferById(Long id);
    
    /**
     * Get all the offers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OfferDTO> findAllOffers(Pageable pageable);
    
    /**
     * Get all the offer deductionValueTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DeductionValueTypeDTO> findAllDeductionValueTypes(Pageable pageable);

}
