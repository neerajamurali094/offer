package com.diviso.graeshoppe.offer.web.rest;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.diviso.graeshoppe.offer.model.OrderModel;
import com.diviso.graeshoppe.offer.service.AggregateCommandService;
import com.diviso.graeshoppe.offer.domain.Offer;
//import com.diviso.graeshoppe.offer.service.dto.OfferDTO;
import com.diviso.graeshoppe.offer.model.OfferModel;
import com.diviso.graeshoppe.offer.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.offer.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Offer command service.
 */
@RestController
@RequestMapping("/api")
public class AggregateCommandResource {
	 private final Logger log = LoggerFactory.getLogger(AggregateCommandResource.class);
	 
	 private static final String ENTITY_NAME = "offerAggregateCommand";
	 
	 private final AggregateCommandService aggregateCommandService;
	 
	 public AggregateCommandResource(AggregateCommandService aggregateCommandService) {
		super();
		this.aggregateCommandService = aggregateCommandService;
	}

	  
	 /**
	     * POST  /command/offers/create-offer : Create a new offer.
	     *
	     * @param offerDTO the offerDTO to create
	     * @return the ResponseEntity with status 201 (Created) and with body the new offerDTO, or with status 400 (Bad Request) if the offer has already an ID
	     * @throws URISyntaxException if the Location URI syntax is incorrect
	     */
	    @PostMapping("/command/offers/create-offer")
	    @Timed
	    public ResponseEntity<OfferModel> createOffer(@RequestBody OfferModel offerModel) throws URISyntaxException {
	        log.debug("REST request to save Offer : {}", offerModel);
	       
	        OfferModel offer = aggregateCommandService.saveOffer(offerModel);
	        return ResponseEntity.created(new URI("/api/command/offers/create-offer/" + offer.getId()))
	            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, offer.getId().toString()))
	            .body(offer);
	    }
	    
	    /**
	     * POST  /offers/claimOffer/ : get the "promoCode" and claim the offer.
	     *
	     * @param orderModel the orderModel containing requested offer details
	     * @return the ResponseEntity with status 200 (OK) and with body the OrderModel, or with status 404 (Not Found)
	     * @throws URISyntaxException 
	     */
	    @PostMapping("/command/offers/claim-offer")
	    @Timed
	    public ResponseEntity<OrderModel> claimOffer(@RequestBody OrderModel orderModel) throws URISyntaxException {
	        log.debug("REST request to claim Offer : {}", orderModel.getPromoCode());
	        OrderModel offerClaimedModel=aggregateCommandService.claimOffer(orderModel);
	        
	        return ResponseEntity.created(new URI("/api/command/offers/claim-offer/" + offerClaimedModel.getOrderDiscountTotal()))
	                .body(offerClaimedModel);
	        
	    }
	 
}
