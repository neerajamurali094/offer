package com.diviso.graeshoppe.offer.service;

import com.diviso.graeshoppe.offer.service.dto.OfferTargetCategoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing OfferTargetCategory.
 */
public interface OfferTargetCategoryService {

    /**
     * Save a offerTargetCategory.
     *
     * @param offerTargetCategoryDTO the entity to save
     * @return the persisted entity
     */
    OfferTargetCategoryDTO save(OfferTargetCategoryDTO offerTargetCategoryDTO);

    /**
     * Get all the offerTargetCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OfferTargetCategoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" offerTargetCategory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OfferTargetCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" offerTargetCategory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the offerTargetCategory corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OfferTargetCategoryDTO> search(String query, Pageable pageable);
}
