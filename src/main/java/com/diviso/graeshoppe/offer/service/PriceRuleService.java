package com.diviso.graeshoppe.offer.service;

import com.diviso.graeshoppe.offer.service.dto.PriceRuleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing PriceRule.
 */
public interface PriceRuleService {

    /**
     * Save a priceRule.
     *
     * @param priceRuleDTO the entity to save
     * @return the persisted entity
     */
    PriceRuleDTO save(PriceRuleDTO priceRuleDTO);

    /**
     * Get all the priceRules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PriceRuleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" priceRule.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PriceRuleDTO> findOne(Long id);

    /**
     * Delete the "id" priceRule.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the priceRule corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PriceRuleDTO> search(String query, Pageable pageable);
}
