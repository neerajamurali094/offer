package com.diviso.graeshoppe.offer.service.impl;

import com.diviso.graeshoppe.offer.service.TargetTypeService;
import com.diviso.graeshoppe.offer.domain.TargetType;
import com.diviso.graeshoppe.offer.repository.TargetTypeRepository;
import com.diviso.graeshoppe.offer.repository.search.TargetTypeSearchRepository;
import com.diviso.graeshoppe.offer.service.dto.TargetTypeDTO;
import com.diviso.graeshoppe.offer.service.mapper.TargetTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TargetType.
 */
@Service
@Transactional
public class TargetTypeServiceImpl implements TargetTypeService {

    private final Logger log = LoggerFactory.getLogger(TargetTypeServiceImpl.class);

    private final TargetTypeRepository targetTypeRepository;

    private final TargetTypeMapper targetTypeMapper;

    private final TargetTypeSearchRepository targetTypeSearchRepository;

    public TargetTypeServiceImpl(TargetTypeRepository targetTypeRepository, TargetTypeMapper targetTypeMapper, TargetTypeSearchRepository targetTypeSearchRepository) {
        this.targetTypeRepository = targetTypeRepository;
        this.targetTypeMapper = targetTypeMapper;
        this.targetTypeSearchRepository = targetTypeSearchRepository;
    }

    /**
     * Save a targetType.
     *
     * @param targetTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TargetTypeDTO save(TargetTypeDTO targetTypeDTO) {
        log.debug("Request to save TargetType : {}", targetTypeDTO);

        TargetType targetType = targetTypeMapper.toEntity(targetTypeDTO);
        targetType = targetTypeRepository.save(targetType);
        TargetTypeDTO result = targetTypeMapper.toDto(targetType);
        targetTypeSearchRepository.save(targetType);
        return result;
    }

    /**
     * Get all the targetTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TargetTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TargetTypes");
        return targetTypeRepository.findAll(pageable)
            .map(targetTypeMapper::toDto);
    }


    /**
     * Get one targetType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TargetTypeDTO> findOne(Long id) {
        log.debug("Request to get TargetType : {}", id);
        return targetTypeRepository.findById(id)
            .map(targetTypeMapper::toDto);
    }

    /**
     * Delete the targetType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TargetType : {}", id);
        targetTypeRepository.deleteById(id);
        targetTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the targetType corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TargetTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TargetTypes for query {}", query);
        return targetTypeSearchRepository.search(queryStringQuery(query), pageable)
            .map(targetTypeMapper::toDto);
    }
}
