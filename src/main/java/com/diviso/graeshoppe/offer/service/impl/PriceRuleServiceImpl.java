package com.diviso.graeshoppe.offer.service.impl;

import com.diviso.graeshoppe.offer.service.PriceRuleService;
import com.diviso.graeshoppe.offer.domain.PriceRule;
import com.diviso.graeshoppe.offer.repository.PriceRuleRepository;
import com.diviso.graeshoppe.offer.repository.search.PriceRuleSearchRepository;
import com.diviso.graeshoppe.offer.service.dto.PriceRuleDTO;
import com.diviso.graeshoppe.offer.service.mapper.PriceRuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PriceRule.
 */
@Service
@Transactional
public class PriceRuleServiceImpl implements PriceRuleService {

    private final Logger log = LoggerFactory.getLogger(PriceRuleServiceImpl.class);

    private final PriceRuleRepository priceRuleRepository;

    private final PriceRuleMapper priceRuleMapper;

    private final PriceRuleSearchRepository priceRuleSearchRepository;

    public PriceRuleServiceImpl(PriceRuleRepository priceRuleRepository, PriceRuleMapper priceRuleMapper, PriceRuleSearchRepository priceRuleSearchRepository) {
        this.priceRuleRepository = priceRuleRepository;
        this.priceRuleMapper = priceRuleMapper;
        this.priceRuleSearchRepository = priceRuleSearchRepository;
    }

    /**
     * Save a priceRule.
     *
     * @param priceRuleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PriceRuleDTO save(PriceRuleDTO priceRuleDTO) {
        log.debug("Request to save PriceRule : {}", priceRuleDTO);

        PriceRule priceRule = priceRuleMapper.toEntity(priceRuleDTO);
        priceRule = priceRuleRepository.save(priceRule);
        PriceRuleDTO result = priceRuleMapper.toDto(priceRule);
        priceRuleSearchRepository.save(priceRule);
        return result;
    }

    /**
     * Get all the priceRules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PriceRuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PriceRules");
        return priceRuleRepository.findAll(pageable)
            .map(priceRuleMapper::toDto);
    }


    /**
     * Get one priceRule by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PriceRuleDTO> findOne(Long id) {
        log.debug("Request to get PriceRule : {}", id);
        return priceRuleRepository.findById(id)
            .map(priceRuleMapper::toDto);
    }

    /**
     * Delete the priceRule by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PriceRule : {}", id);
        priceRuleRepository.deleteById(id);
        priceRuleSearchRepository.deleteById(id);
    }

    /**
     * Search for the priceRule corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PriceRuleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PriceRules for query {}", query);
        return priceRuleSearchRepository.search(queryStringQuery(query), pageable)
            .map(priceRuleMapper::toDto);
    }
}
