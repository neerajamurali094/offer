package com.diviso.graeshoppe.offer.service.impl;

import com.diviso.graeshoppe.offer.service.AllocationMethodService;
import com.diviso.graeshoppe.offer.domain.AllocationMethod;
import com.diviso.graeshoppe.offer.repository.AllocationMethodRepository;
import com.diviso.graeshoppe.offer.repository.search.AllocationMethodSearchRepository;
import com.diviso.graeshoppe.offer.service.dto.AllocationMethodDTO;
import com.diviso.graeshoppe.offer.service.mapper.AllocationMethodMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AllocationMethod.
 */
@Service
@Transactional
public class AllocationMethodServiceImpl implements AllocationMethodService {

    private final Logger log = LoggerFactory.getLogger(AllocationMethodServiceImpl.class);

    private final AllocationMethodRepository allocationMethodRepository;

    private final AllocationMethodMapper allocationMethodMapper;

    private final AllocationMethodSearchRepository allocationMethodSearchRepository;

    public AllocationMethodServiceImpl(AllocationMethodRepository allocationMethodRepository, AllocationMethodMapper allocationMethodMapper, AllocationMethodSearchRepository allocationMethodSearchRepository) {
        this.allocationMethodRepository = allocationMethodRepository;
        this.allocationMethodMapper = allocationMethodMapper;
        this.allocationMethodSearchRepository = allocationMethodSearchRepository;
    }

    /**
     * Save a allocationMethod.
     *
     * @param allocationMethodDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AllocationMethodDTO save(AllocationMethodDTO allocationMethodDTO) {
        log.debug("Request to save AllocationMethod : {}", allocationMethodDTO);

        AllocationMethod allocationMethod = allocationMethodMapper.toEntity(allocationMethodDTO);
        allocationMethod = allocationMethodRepository.save(allocationMethod);
        AllocationMethodDTO result = allocationMethodMapper.toDto(allocationMethod);
        allocationMethodSearchRepository.save(allocationMethod);
        return result;
    }

    /**
     * Get all the allocationMethods.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AllocationMethodDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AllocationMethods");
        return allocationMethodRepository.findAll(pageable)
            .map(allocationMethodMapper::toDto);
    }


    /**
     * Get one allocationMethod by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AllocationMethodDTO> findOne(Long id) {
        log.debug("Request to get AllocationMethod : {}", id);
        return allocationMethodRepository.findById(id)
            .map(allocationMethodMapper::toDto);
    }

    /**
     * Delete the allocationMethod by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AllocationMethod : {}", id);
        allocationMethodRepository.deleteById(id);
        allocationMethodSearchRepository.deleteById(id);
    }

    /**
     * Search for the allocationMethod corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AllocationMethodDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AllocationMethods for query {}", query);
        return allocationMethodSearchRepository.search(queryStringQuery(query), pageable)
            .map(allocationMethodMapper::toDto);
    }
}
