package com.diviso.graeshoppe.offer.service.impl;

import com.diviso.graeshoppe.offer.service.OfferTargetService;
import com.diviso.graeshoppe.offer.domain.OfferTarget;
import com.diviso.graeshoppe.offer.repository.OfferTargetRepository;
import com.diviso.graeshoppe.offer.repository.search.OfferTargetSearchRepository;
import com.diviso.graeshoppe.offer.service.dto.OfferTargetDTO;
import com.diviso.graeshoppe.offer.service.mapper.OfferTargetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OfferTarget.
 */
@Service
@Transactional
public class OfferTargetServiceImpl implements OfferTargetService {

    private final Logger log = LoggerFactory.getLogger(OfferTargetServiceImpl.class);

    private final OfferTargetRepository offerTargetRepository;

    private final OfferTargetMapper offerTargetMapper;

    private final OfferTargetSearchRepository offerTargetSearchRepository;

    public OfferTargetServiceImpl(OfferTargetRepository offerTargetRepository, OfferTargetMapper offerTargetMapper, OfferTargetSearchRepository offerTargetSearchRepository) {
        this.offerTargetRepository = offerTargetRepository;
        this.offerTargetMapper = offerTargetMapper;
        this.offerTargetSearchRepository = offerTargetSearchRepository;
    }

    /**
     * Save a offerTarget.
     *
     * @param offerTargetDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OfferTargetDTO save(OfferTargetDTO offerTargetDTO) {
        log.debug("Request to save OfferTarget : {}", offerTargetDTO);

        OfferTarget offerTarget = offerTargetMapper.toEntity(offerTargetDTO);
        offerTarget = offerTargetRepository.save(offerTarget);
        OfferTargetDTO result = offerTargetMapper.toDto(offerTarget);
        offerTargetSearchRepository.save(offerTarget);
        return result;
    }

    /**
     * Get all the offerTargets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OfferTargetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OfferTargets");
        return offerTargetRepository.findAll(pageable)
            .map(offerTargetMapper::toDto);
    }


    /**
     * Get one offerTarget by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OfferTargetDTO> findOne(Long id) {
        log.debug("Request to get OfferTarget : {}", id);
        return offerTargetRepository.findById(id)
            .map(offerTargetMapper::toDto);
    }

    /**
     * Delete the offerTarget by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OfferTarget : {}", id);
        offerTargetRepository.deleteById(id);
        offerTargetSearchRepository.deleteById(id);
    }

    /**
     * Search for the offerTarget corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OfferTargetDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OfferTargets for query {}", query);
        return offerTargetSearchRepository.search(queryStringQuery(query), pageable)
            .map(offerTargetMapper::toDto);
    }
}
