package com.diviso.graeshoppe.offer.service;

import com.diviso.graeshoppe.offer.domain.Offer;
import com.diviso.graeshoppe.offer.model.OfferModel;
import com.diviso.graeshoppe.offer.model.OrderModel;

/**
 * Service Interface for managing Offer commands.
 */
public interface AggregateCommandService {
	
	/**
     * Save a offer.
     *
     * @param offerModel the entity to save
     * @return the persisted entity
     */
	Offer saveOffer(OfferModel offerModel);
    
    /**
     * claim the offer.
     *
     * @param orderModel the entity to claim the offer
     * @return the orderModel entity
     */
    OrderModel claimOffer(OrderModel orderModel);
    
}
