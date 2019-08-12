package com.diviso.graeshoppe.offer.service.impl;

import com.diviso.graeshoppe.offer.service.OfferTargetCategoryService;
import com.diviso.graeshoppe.offer.domain.OfferTargetCategory;
import com.diviso.graeshoppe.offer.repository.OfferTargetCategoryRepository;
import com.diviso.graeshoppe.offer.repository.search.OfferTargetCategorySearchRepository;
import com.diviso.graeshoppe.offer.service.dto.OfferTargetCategoryDTO;
import com.diviso.graeshoppe.offer.service.mapper.OfferTargetCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OfferTargetCategory.
 */
@Service
@Transactional
public class OfferTargetCategoryServiceImpl implements OfferTargetCategoryService {

    private final Logger log = LoggerFactory.getLogger(OfferTargetCategoryServiceImpl.class);

    private final OfferTargetCategoryRepository offerTargetCategoryRepository;

    private final OfferTargetCategoryMapper offerTargetCategoryMapper;

    private final OfferTargetCategorySearchRepository offerTargetCategorySearchRepository;

    public OfferTargetCategoryServiceImpl(OfferTargetCategoryRepository offerTargetCategoryRepository, OfferTargetCategoryMapper offerTargetCategoryMapper, OfferTargetCategorySearchRepository offerTargetCategorySearchRepository) {
        this.offerTargetCategoryRepository = offerTargetCategoryRepository;
        this.offerTargetCategoryMapper = offerTargetCategoryMapper;
        this.offerTargetCategorySearchRepository = offerTargetCategorySearchRepository;
    }

    /**
     * Save a offerTargetCategory.
     *
     * @param offerTargetCategoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OfferTargetCategoryDTO save(OfferTargetCategoryDTO offerTargetCategoryDTO) {
        log.debug("Request to save OfferTargetCategory : {}", offerTargetCategoryDTO);

        OfferTargetCategory offerTargetCategory = offerTargetCategoryMapper.toEntity(offerTargetCategoryDTO);
        offerTargetCategory = offerTargetCategoryRepository.save(offerTargetCategory);
        OfferTargetCategoryDTO result = offerTargetCategoryMapper.toDto(offerTargetCategory);
        offerTargetCategorySearchRepository.save(offerTargetCategory);
        return result;
    }

    /**
     * Get all the offerTargetCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OfferTargetCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OfferTargetCategories");
        return offerTargetCategoryRepository.findAll(pageable)
            .map(offerTargetCategoryMapper::toDto);
    }


    /**
     * Get one offerTargetCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OfferTargetCategoryDTO> findOne(Long id) {
        log.debug("Request to get OfferTargetCategory : {}", id);
        return offerTargetCategoryRepository.findById(id)
            .map(offerTargetCategoryMapper::toDto);
    }

    /**
     * Delete the offerTargetCategory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OfferTargetCategory : {}", id);
        offerTargetCategoryRepository.deleteById(id);
        offerTargetCategorySearchRepository.deleteById(id);
    }

    /**
     * Search for the offerTargetCategory corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OfferTargetCategoryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OfferTargetCategories for query {}", query);
        return offerTargetCategorySearchRepository.search(queryStringQuery(query), pageable)
            .map(offerTargetCategoryMapper::toDto);
    }
}
