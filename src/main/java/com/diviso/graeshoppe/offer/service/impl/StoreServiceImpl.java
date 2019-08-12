package com.diviso.graeshoppe.offer.service.impl;

import com.diviso.graeshoppe.offer.service.StoreService;
import com.diviso.graeshoppe.offer.domain.Store;
import com.diviso.graeshoppe.offer.repository.StoreRepository;
import com.diviso.graeshoppe.offer.repository.search.StoreSearchRepository;
import com.diviso.graeshoppe.offer.service.dto.StoreDTO;
import com.diviso.graeshoppe.offer.service.mapper.StoreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Store.
 */
@Service
@Transactional
public class StoreServiceImpl implements StoreService {

    private final Logger log = LoggerFactory.getLogger(StoreServiceImpl.class);

    private final StoreRepository storeRepository;

    private final StoreMapper storeMapper;

    private final StoreSearchRepository storeSearchRepository;

    public StoreServiceImpl(StoreRepository storeRepository, StoreMapper storeMapper, StoreSearchRepository storeSearchRepository) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
        this.storeSearchRepository = storeSearchRepository;
    }

    /**
     * Save a store.
     *
     * @param storeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StoreDTO save(StoreDTO storeDTO) {
        log.debug("Request to save Store : {}", storeDTO);

        Store store = storeMapper.toEntity(storeDTO);
        store = storeRepository.save(store);
        StoreDTO result = storeMapper.toDto(store);
        storeSearchRepository.save(store);
        return result;
    }

    /**
     * Get all the stores.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StoreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Stores");
        return storeRepository.findAll(pageable)
            .map(storeMapper::toDto);
    }


    /**
     * Get one store by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StoreDTO> findOne(Long id) {
        log.debug("Request to get Store : {}", id);
        return storeRepository.findById(id)
            .map(storeMapper::toDto);
    }

    /**
     * Delete the store by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Store : {}", id);
        storeRepository.deleteById(id);
        storeSearchRepository.deleteById(id);
    }

    /**
     * Search for the store corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StoreDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Stores for query {}", query);
        return storeSearchRepository.search(queryStringQuery(query), pageable)
            .map(storeMapper::toDto);
    }
}
