package com.diviso.graeshoppe.offer.service;

import com.diviso.graeshoppe.offer.service.dto.OfferTargetDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing OfferTarget.
 */
public interface OfferTargetService {

    /**
     * Save a offerTarget.
     *
     * @param offerTargetDTO the entity to save
     * @return the persisted entity
     */
    OfferTargetDTO save(OfferTargetDTO offerTargetDTO);

    /**
     * Get all the offerTargets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OfferTargetDTO> findAll(Pageable pageable);


    /**
     * Get the "id" offerTarget.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OfferTargetDTO> findOne(Long id);

    /**
     * Delete the "id" offerTarget.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the offerTarget corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OfferTargetDTO> search(String query, Pageable pageable);
}
