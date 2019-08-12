package com.diviso.graeshoppe.offer.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diviso.graeshoppe.offer.repository.DeductionValueTypeRepository;
import com.diviso.graeshoppe.offer.repository.OfferRepository;
import com.diviso.graeshoppe.offer.repository.search.DeductionValueTypeSearchRepository;
import com.diviso.graeshoppe.offer.repository.search.OfferSearchRepository;
import com.diviso.graeshoppe.offer.service.AggregateQueryService;
import com.diviso.graeshoppe.offer.service.dto.DeductionValueTypeDTO;
import com.diviso.graeshoppe.offer.service.dto.OfferDTO;
import com.diviso.graeshoppe.offer.service.mapper.DeductionValueTypeMapper;
import com.diviso.graeshoppe.offer.service.mapper.OfferMapper;

/**
 * Service Implementation for managing Offer queries.
 */
@Service
@Transactional
public class AggregateQueryServiceImpl implements AggregateQueryService {
	
	 private final Logger log = LoggerFactory.getLogger(AggregateQueryServiceImpl.class);
	   
	
	 public AggregateQueryServiceImpl(OfferRepository offerRepository, OfferMapper offerMapper,
			OfferSearchRepository offerSearchRepository, DeductionValueTypeRepository deductionValueTypeRepository,
			DeductionValueTypeMapper deductionValueTypeMapper,
			DeductionValueTypeSearchRepository deductionValueTypeSearchRepository) {
		super();
		this.offerRepository = offerRepository;
		this.offerMapper = offerMapper;
		this.offerSearchRepository = offerSearchRepository;
		this.deductionValueTypeRepository = deductionValueTypeRepository;
		this.deductionValueTypeMapper = deductionValueTypeMapper;
		this.deductionValueTypeSearchRepository = deductionValueTypeSearchRepository;
	}

	private final OfferRepository offerRepository;

	 private final OfferMapper offerMapper;

	 private final OfferSearchRepository offerSearchRepository;
	 
	 private final DeductionValueTypeRepository deductionValueTypeRepository;

	 private final DeductionValueTypeMapper deductionValueTypeMapper;

     private final DeductionValueTypeSearchRepository deductionValueTypeSearchRepository;

	   
	/**
     * Get one offer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
	@Override
	public Optional<OfferDTO> findOfferById(Long id) {
		log.debug("Request to get Offer : {}", id);
        return offerRepository.findById(id)
            .map(offerMapper::toDto);
	}
	
	/**
     * Get all the offers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OfferDTO> findAllOffers(Pageable pageable) {
        log.debug("Request to get all Offers");
        return offerRepository.findAll(pageable)
            .map(offerMapper::toDto);
    }
    
    /**
     * Get all the offer deductionValueTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeductionValueTypeDTO> findAllDeductionValueTypes(Pageable pageable) {
        log.debug("Request to get all DeductionValueTypes");
        return deductionValueTypeRepository.findAll(pageable)
            .map(deductionValueTypeMapper::toDto);
    }



}
