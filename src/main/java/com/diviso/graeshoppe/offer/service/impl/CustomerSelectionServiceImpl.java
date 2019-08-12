package com.diviso.graeshoppe.offer.service.impl;

import com.diviso.graeshoppe.offer.service.CustomerSelectionService;
import com.diviso.graeshoppe.offer.domain.CustomerSelection;
import com.diviso.graeshoppe.offer.repository.CustomerSelectionRepository;
import com.diviso.graeshoppe.offer.repository.search.CustomerSelectionSearchRepository;
import com.diviso.graeshoppe.offer.service.dto.CustomerSelectionDTO;
import com.diviso.graeshoppe.offer.service.mapper.CustomerSelectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CustomerSelection.
 */
@Service
@Transactional
public class CustomerSelectionServiceImpl implements CustomerSelectionService {

    private final Logger log = LoggerFactory.getLogger(CustomerSelectionServiceImpl.class);

    private final CustomerSelectionRepository customerSelectionRepository;

    private final CustomerSelectionMapper customerSelectionMapper;

    private final CustomerSelectionSearchRepository customerSelectionSearchRepository;

    public CustomerSelectionServiceImpl(CustomerSelectionRepository customerSelectionRepository, CustomerSelectionMapper customerSelectionMapper, CustomerSelectionSearchRepository customerSelectionSearchRepository) {
        this.customerSelectionRepository = customerSelectionRepository;
        this.customerSelectionMapper = customerSelectionMapper;
        this.customerSelectionSearchRepository = customerSelectionSearchRepository;
    }

    /**
     * Save a customerSelection.
     *
     * @param customerSelectionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomerSelectionDTO save(CustomerSelectionDTO customerSelectionDTO) {
        log.debug("Request to save CustomerSelection : {}", customerSelectionDTO);

        CustomerSelection customerSelection = customerSelectionMapper.toEntity(customerSelectionDTO);
        customerSelection = customerSelectionRepository.save(customerSelection);
        CustomerSelectionDTO result = customerSelectionMapper.toDto(customerSelection);
        customerSelectionSearchRepository.save(customerSelection);
        return result;
    }

    /**
     * Get all the customerSelections.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerSelectionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerSelections");
        return customerSelectionRepository.findAll(pageable)
            .map(customerSelectionMapper::toDto);
    }


    /**
     * Get one customerSelection by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerSelectionDTO> findOne(Long id) {
        log.debug("Request to get CustomerSelection : {}", id);
        return customerSelectionRepository.findById(id)
            .map(customerSelectionMapper::toDto);
    }

    /**
     * Delete the customerSelection by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerSelection : {}", id);
        customerSelectionRepository.deleteById(id);
        customerSelectionSearchRepository.deleteById(id);
    }

    /**
     * Search for the customerSelection corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerSelectionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CustomerSelections for query {}", query);
        return customerSelectionSearchRepository.search(queryStringQuery(query), pageable)
            .map(customerSelectionMapper::toDto);
    }
}
