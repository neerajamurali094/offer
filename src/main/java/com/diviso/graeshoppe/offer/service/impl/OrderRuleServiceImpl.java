package com.diviso.graeshoppe.offer.service.impl;

import com.diviso.graeshoppe.offer.service.OrderRuleService;
import com.diviso.graeshoppe.offer.domain.OrderRule;
import com.diviso.graeshoppe.offer.repository.OrderRuleRepository;
import com.diviso.graeshoppe.offer.repository.search.OrderRuleSearchRepository;
import com.diviso.graeshoppe.offer.service.dto.OrderRuleDTO;
import com.diviso.graeshoppe.offer.service.mapper.OrderRuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OrderRule.
 */
@Service
@Transactional
public class OrderRuleServiceImpl implements OrderRuleService {

    private final Logger log = LoggerFactory.getLogger(OrderRuleServiceImpl.class);

    private final OrderRuleRepository orderRuleRepository;

    private final OrderRuleMapper orderRuleMapper;

    private final OrderRuleSearchRepository orderRuleSearchRepository;

    public OrderRuleServiceImpl(OrderRuleRepository orderRuleRepository, OrderRuleMapper orderRuleMapper, OrderRuleSearchRepository orderRuleSearchRepository) {
        this.orderRuleRepository = orderRuleRepository;
        this.orderRuleMapper = orderRuleMapper;
        this.orderRuleSearchRepository = orderRuleSearchRepository;
    }

    /**
     * Save a orderRule.
     *
     * @param orderRuleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrderRuleDTO save(OrderRuleDTO orderRuleDTO) {
        log.debug("Request to save OrderRule : {}", orderRuleDTO);

        OrderRule orderRule = orderRuleMapper.toEntity(orderRuleDTO);
        orderRule = orderRuleRepository.save(orderRule);
        OrderRuleDTO result = orderRuleMapper.toDto(orderRule);
        orderRuleSearchRepository.save(orderRule);
        return result;
    }

    /**
     * Get all the orderRules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrderRuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrderRules");
        return orderRuleRepository.findAll(pageable)
            .map(orderRuleMapper::toDto);
    }


    /**
     * Get one orderRule by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderRuleDTO> findOne(Long id) {
        log.debug("Request to get OrderRule : {}", id);
        return orderRuleRepository.findById(id)
            .map(orderRuleMapper::toDto);
    }

    /**
     * Delete the orderRule by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderRule : {}", id);
        orderRuleRepository.deleteById(id);
        orderRuleSearchRepository.deleteById(id);
    }

    /**
     * Search for the orderRule corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrderRuleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OrderRules for query {}", query);
        return orderRuleSearchRepository.search(queryStringQuery(query), pageable)
            .map(orderRuleMapper::toDto);
    }
}
