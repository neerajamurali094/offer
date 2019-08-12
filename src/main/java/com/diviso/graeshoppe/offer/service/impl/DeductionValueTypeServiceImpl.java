package com.diviso.graeshoppe.offer.service.impl;

import com.diviso.graeshoppe.offer.service.DeductionValueTypeService;
import com.diviso.graeshoppe.offer.domain.DeductionValueType;
import com.diviso.graeshoppe.offer.repository.DeductionValueTypeRepository;
import com.diviso.graeshoppe.offer.repository.search.DeductionValueTypeSearchRepository;
import com.diviso.graeshoppe.offer.service.dto.DeductionValueTypeDTO;
import com.diviso.graeshoppe.offer.service.mapper.DeductionValueTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DeductionValueType.
 */
@Service
@Transactional
public class DeductionValueTypeServiceImpl implements DeductionValueTypeService {

    private final Logger log = LoggerFactory.getLogger(DeductionValueTypeServiceImpl.class);

    private final DeductionValueTypeRepository deductionValueTypeRepository;

    private final DeductionValueTypeMapper deductionValueTypeMapper;

    private final DeductionValueTypeSearchRepository deductionValueTypeSearchRepository;

    public DeductionValueTypeServiceImpl(DeductionValueTypeRepository deductionValueTypeRepository, DeductionValueTypeMapper deductionValueTypeMapper, DeductionValueTypeSearchRepository deductionValueTypeSearchRepository) {
        this.deductionValueTypeRepository = deductionValueTypeRepository;
        this.deductionValueTypeMapper = deductionValueTypeMapper;
        this.deductionValueTypeSearchRepository = deductionValueTypeSearchRepository;
    }

    /**
     * Save a deductionValueType.
     *
     * @param deductionValueTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DeductionValueTypeDTO save(DeductionValueTypeDTO deductionValueTypeDTO) {
        log.debug("Request to save DeductionValueType : {}", deductionValueTypeDTO);

        DeductionValueType deductionValueType = deductionValueTypeMapper.toEntity(deductionValueTypeDTO);
        deductionValueType = deductionValueTypeRepository.save(deductionValueType);
        DeductionValueTypeDTO result = deductionValueTypeMapper.toDto(deductionValueType);
        deductionValueTypeSearchRepository.save(deductionValueType);
        return result;
    }

    /**
     * Get all the deductionValueTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeductionValueTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DeductionValueTypes");
        return deductionValueTypeRepository.findAll(pageable)
            .map(deductionValueTypeMapper::toDto);
    }


    /**
     * Get one deductionValueType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DeductionValueTypeDTO> findOne(Long id) {
        log.debug("Request to get DeductionValueType : {}", id);
        return deductionValueTypeRepository.findById(id)
            .map(deductionValueTypeMapper::toDto);
    }

    /**
     * Delete the deductionValueType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeductionValueType : {}", id);
        deductionValueTypeRepository.deleteById(id);
        deductionValueTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the deductionValueType corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeductionValueTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DeductionValueTypes for query {}", query);
        return deductionValueTypeSearchRepository.search(queryStringQuery(query), pageable)
            .map(deductionValueTypeMapper::toDto);
    }
}
