package com.diviso.graeshoppe.offer.service;

import com.diviso.graeshoppe.offer.service.dto.OrderRuleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing OrderRule.
 */
public interface OrderRuleService {

    /**
     * Save a orderRule.
     *
     * @param orderRuleDTO the entity to save
     * @return the persisted entity
     */
    OrderRuleDTO save(OrderRuleDTO orderRuleDTO);

    /**
     * Get all the orderRules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OrderRuleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" orderRule.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OrderRuleDTO> findOne(Long id);

    /**
     * Delete the "id" orderRule.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the orderRule corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OrderRuleDTO> search(String query, Pageable pageable);
}
