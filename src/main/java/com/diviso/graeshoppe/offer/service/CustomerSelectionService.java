package com.diviso.graeshoppe.offer.service;

import com.diviso.graeshoppe.offer.service.dto.CustomerSelectionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CustomerSelection.
 */
public interface CustomerSelectionService {

    /**
     * Save a customerSelection.
     *
     * @param customerSelectionDTO the entity to save
     * @return the persisted entity
     */
    CustomerSelectionDTO save(CustomerSelectionDTO customerSelectionDTO);

    /**
     * Get all the customerSelections.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CustomerSelectionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" customerSelection.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CustomerSelectionDTO> findOne(Long id);

    /**
     * Delete the "id" customerSelection.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the customerSelection corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CustomerSelectionDTO> search(String query, Pageable pageable);
}
