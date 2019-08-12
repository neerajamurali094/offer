package com.diviso.graeshoppe.offer.service;

import com.diviso.graeshoppe.offer.service.dto.PaymentRuleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing PaymentRule.
 */
public interface PaymentRuleService {

    /**
     * Save a paymentRule.
     *
     * @param paymentRuleDTO the entity to save
     * @return the persisted entity
     */
    PaymentRuleDTO save(PaymentRuleDTO paymentRuleDTO);

    /**
     * Get all the paymentRules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PaymentRuleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" paymentRule.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PaymentRuleDTO> findOne(Long id);

    /**
     * Delete the "id" paymentRule.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the paymentRule corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PaymentRuleDTO> search(String query, Pageable pageable);
}
