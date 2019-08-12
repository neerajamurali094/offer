package com.diviso.graeshoppe.offer.service;

import com.diviso.graeshoppe.offer.service.dto.TargetTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TargetType.
 */
public interface TargetTypeService {

    /**
     * Save a targetType.
     *
     * @param targetTypeDTO the entity to save
     * @return the persisted entity
     */
    TargetTypeDTO save(TargetTypeDTO targetTypeDTO);

    /**
     * Get all the targetTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TargetTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" targetType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TargetTypeDTO> findOne(Long id);

    /**
     * Delete the "id" targetType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the targetType corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TargetTypeDTO> search(String query, Pageable pageable);
}
