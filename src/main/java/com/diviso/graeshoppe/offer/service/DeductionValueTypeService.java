package com.diviso.graeshoppe.offer.service;

import com.diviso.graeshoppe.offer.service.dto.DeductionValueTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing DeductionValueType.
 */
public interface DeductionValueTypeService {

    /**
     * Save a deductionValueType.
     *
     * @param deductionValueTypeDTO the entity to save
     * @return the persisted entity
     */
    DeductionValueTypeDTO save(DeductionValueTypeDTO deductionValueTypeDTO);

    /**
     * Get all the deductionValueTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DeductionValueTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" deductionValueType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DeductionValueTypeDTO> findOne(Long id);

    /**
     * Delete the "id" deductionValueType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the deductionValueType corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DeductionValueTypeDTO> search(String query, Pageable pageable);
}
