package com.diviso.graeshoppe.offer.service;

import com.diviso.graeshoppe.offer.service.dto.AllocationMethodDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AllocationMethod.
 */
public interface AllocationMethodService {

    /**
     * Save a allocationMethod.
     *
     * @param allocationMethodDTO the entity to save
     * @return the persisted entity
     */
    AllocationMethodDTO save(AllocationMethodDTO allocationMethodDTO);

    /**
     * Get all the allocationMethods.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AllocationMethodDTO> findAll(Pageable pageable);


    /**
     * Get the "id" allocationMethod.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AllocationMethodDTO> findOne(Long id);

    /**
     * Delete the "id" allocationMethod.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the allocationMethod corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AllocationMethodDTO> search(String query, Pageable pageable);
}
