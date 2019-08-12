package com.diviso.graeshoppe.offer.service.impl;

import com.diviso.graeshoppe.offer.service.PaymentRuleService;
import com.diviso.graeshoppe.offer.domain.PaymentRule;
import com.diviso.graeshoppe.offer.repository.PaymentRuleRepository;
import com.diviso.graeshoppe.offer.repository.search.PaymentRuleSearchRepository;
import com.diviso.graeshoppe.offer.service.dto.PaymentRuleDTO;
import com.diviso.graeshoppe.offer.service.mapper.PaymentRuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PaymentRule.
 */
@Service
@Transactional
public class PaymentRuleServiceImpl implements PaymentRuleService {

    private final Logger log = LoggerFactory.getLogger(PaymentRuleServiceImpl.class);

    private final PaymentRuleRepository paymentRuleRepository;

    private final PaymentRuleMapper paymentRuleMapper;

    private final PaymentRuleSearchRepository paymentRuleSearchRepository;

    public PaymentRuleServiceImpl(PaymentRuleRepository paymentRuleRepository, PaymentRuleMapper paymentRuleMapper, PaymentRuleSearchRepository paymentRuleSearchRepository) {
        this.paymentRuleRepository = paymentRuleRepository;
        this.paymentRuleMapper = paymentRuleMapper;
        this.paymentRuleSearchRepository = paymentRuleSearchRepository;
    }

    /**
     * Save a paymentRule.
     *
     * @param paymentRuleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PaymentRuleDTO save(PaymentRuleDTO paymentRuleDTO) {
        log.debug("Request to save PaymentRule : {}", paymentRuleDTO);

        PaymentRule paymentRule = paymentRuleMapper.toEntity(paymentRuleDTO);
        paymentRule = paymentRuleRepository.save(paymentRule);
        PaymentRuleDTO result = paymentRuleMapper.toDto(paymentRule);
        paymentRuleSearchRepository.save(paymentRule);
        return result;
    }

    /**
     * Get all the paymentRules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaymentRuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentRules");
        return paymentRuleRepository.findAll(pageable)
            .map(paymentRuleMapper::toDto);
    }


    /**
     * Get one paymentRule by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentRuleDTO> findOne(Long id) {
        log.debug("Request to get PaymentRule : {}", id);
        return paymentRuleRepository.findById(id)
            .map(paymentRuleMapper::toDto);
    }

    /**
     * Delete the paymentRule by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentRule : {}", id);
        paymentRuleRepository.deleteById(id);
        paymentRuleSearchRepository.deleteById(id);
    }

    /**
     * Search for the paymentRule corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaymentRuleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PaymentRules for query {}", query);
        return paymentRuleSearchRepository.search(queryStringQuery(query), pageable)
            .map(paymentRuleMapper::toDto);
    }
}
