package com.diviso.graeshoppe.offer.web.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.diviso.graeshoppe.offer.service.AggregateQueryService;
import com.diviso.graeshoppe.offer.service.dto.DeductionValueTypeDTO;
import com.diviso.graeshoppe.offer.service.dto.OfferDTO;
import com.diviso.graeshoppe.offer.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Offer query service.
 */
@RestController
@RequestMapping("/api")
public class AggregateQueryResource {

	 private final Logger log = LoggerFactory.getLogger(OfferResource.class);

	 private static final String ENTITY_NAME = "offerAggregateQuery";
	 
	 private final AggregateQueryService  aggregateQueryService;

	public AggregateQueryResource(AggregateQueryService aggregateQueryService) {
		super();
		this.aggregateQueryService = aggregateQueryService;
	}

	 /**
     * GET  /query/offers/get-offer-by-id/:id : get the "id" offer.
     *
     * @param id the id of the offerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the offerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/query/offers/get-offer-by-id/{id}")
    @Timed
    public ResponseEntity<OfferDTO> getOfferById(@PathVariable Long id) {
        log.debug("REST request to get Offer : {}", id);
        Optional<OfferDTO> offerDTO = aggregateQueryService.findOfferById(id);
        return ResponseUtil.wrapOrNotFound(offerDTO);
    }
    
    /**
     * GET  /offers : get all the offers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of offers in body
     */
    @GetMapping("/query/offers/get-all-offers")
    @Timed
    public ResponseEntity<List<OfferDTO>> getAllOffers(Pageable pageable) {
        log.debug("REST request to get a page of Offers");
        Page<OfferDTO> page = aggregateQueryService.findAllOffers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/query/offers/get-all-offers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    /**
     * GET  /offers : get all the offers by storeId.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of offers in body
     */
    @GetMapping("/query/offers/get-all-offers-by-storeId/{storeId}")
    @Timed
    public ResponseEntity<List<OfferDTO>> getAllOffersByStoreId(Pageable pageable,@PathVariable Long storeId) {
        log.debug("REST request to get a page of Offers by storeId");
        Page<OfferDTO> page = aggregateQueryService.findAllOffersByStoreId(pageable,storeId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/query/offers/get-all-offers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    
    
    /**
     * GET  /deduction-value-types : get all the deductionValueTypes of offers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of deductionValueTypes in body
     */
    @GetMapping("/query/offers/get-all-deduction-value-types")
    @Timed
    public ResponseEntity<List<DeductionValueTypeDTO>> getAllDeductionValueTypes(Pageable pageable) {
        log.debug("REST request to get a page of DeductionValueTypes");
        Page<DeductionValueTypeDTO> page = aggregateQueryService.findAllDeductionValueTypes(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/query/offers/get-all-deduction-value-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    
    
}
