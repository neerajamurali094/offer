package com.diviso.graeshoppe.offer.service.impl;

import com.diviso.graeshoppe.offer.service.OfferService;
import com.diviso.graeshoppe.offer.service.OrderRuleService;
import com.diviso.graeshoppe.offer.domain.Offer;
import com.diviso.graeshoppe.offer.model.OrderModel;
import com.diviso.graeshoppe.offer.repository.OfferRepository;
import com.diviso.graeshoppe.offer.repository.search.OfferSearchRepository;
import com.diviso.graeshoppe.offer.service.dto.OfferDTO;
import com.diviso.graeshoppe.offer.service.dto.OrderRuleDTO;
import com.diviso.graeshoppe.offer.service.dto.PriceRuleDTO;
import com.diviso.graeshoppe.offer.service.mapper.OfferMapper;

import org.drools.core.SessionConfiguration;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.drools.core.marshalling.impl.ProtobufMessages.KnowledgeBase;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Offer.
 */
@Service
@Transactional
public class OfferServiceImpl implements OfferService {

    private final Logger log = LoggerFactory.getLogger(OfferServiceImpl.class);
    
    public OfferServiceImpl(OfferRepository offerRepository, OfferMapper offerMapper,
			OfferSearchRepository offerSearchRepository, OrderRuleService orderRuleServiceImpl,
			com.diviso.graeshoppe.offer.service.impl.PriceRuleServiceImpl priceRuleServiceImpl) {
		super();
		this.offerRepository = offerRepository;
		this.offerMapper = offerMapper;
		this.offerSearchRepository = offerSearchRepository;
		this.orderRuleServiceImpl = orderRuleServiceImpl;
		PriceRuleServiceImpl = priceRuleServiceImpl;
	}

    private final OfferRepository offerRepository;

	private final OfferMapper offerMapper;

    private final OfferSearchRepository offerSearchRepository;
    
    private static final String drlFile = "Offerrule.drl";
    
    private final OrderRuleService orderRuleServiceImpl;
    
    private final PriceRuleServiceImpl PriceRuleServiceImpl;
    
    /*public OfferServiceImpl(OfferRepository offerRepository, OfferMapper offerMapper, OfferSearchRepository offerSearchRepository) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
        this.offerSearchRepository = offerSearchRepository;
    }*/


	/**
     * Save a offer.
     *
     * @param offerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OfferDTO save(OfferDTO offerDTO) {
        log.debug("Request to save Offer : {}", offerDTO);

        Offer offer = offerMapper.toEntity(offerDTO);
        offer = offerRepository.save(offer);
        OfferDTO result = offerMapper.toDto(offer);
        offerSearchRepository.save(offer);
        return result;
    }

	/**
     * Get all the offers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OfferDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Offers");
        return offerRepository.findAll(pageable)
            .map(offerMapper::toDto);
    }


    /**
     * Get one offer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OfferDTO> findOne(Long id) {
        log.debug("Request to get Offer : {}", id);
        return offerRepository.findById(id)
            .map(offerMapper::toDto);
    }

    /**
     * Delete the offer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Offer : {}", id);
       offerRepository.deleteById(id);
        offerSearchRepository.deleteById(id);
    }

    /**
     * Search for the offer corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OfferDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Offers for query {}", query);
        return offerSearchRepository.search(queryStringQuery(query), pageable)
            .map(offerMapper::toDto);
    }
    
    /**
     * Get one offer by promoCode.
     *
     * @param promoCode the promoCode of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OfferDTO> findOfferByPromoCode(String promoCode) {
        log.debug("Request to get Offer by promo code : {}", promoCode);
        return offerRepository.findByPromoCode(promoCode)
            .map(offerMapper::toDto);
    }

    
	@Override
	public void claimOffer(OrderModel orderModel) {
		
		 Optional<OfferDTO> offerDto=offerRepository.findByPromoCode(orderModel.getPromoCode())
		         .map(offerMapper::toDto); 
		 
		 KieServices kieServices = KieServices.Factory.get();
		 KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
		 kieFileSystem.write(ResourceFactory.newClassPathResource(drlFile)); 
		 KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
	     kieBuilder.buildAll();   
	     KieContainer kieContainer= kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
	    
	     KieBase kBase=kieContainer.getKieBase();
	     KieSession ksession = kBase.newKieSession();
	     
	    // orderModel.setPrerequisiteOrderNumber(orderRuleDto.get().getPrerequisiteOrderNumber());
	     //orderModel.setDeductionValueTypeId(priceRuleDto.get().getDeductionValueTypeId());
	     //orderModel.setDeductionValue(priceRuleDto.get().getDeductionValue());
		 
	     ksession.insert(orderModel);
	     
	     Optional<OrderRuleDTO> orderRuleDto=orderRuleServiceImpl.findOne(offerDto.get().getOrderRuleId());
	     log.debug("****orderRuleDto****{}",orderRuleDto);
	     ksession.insert(orderRuleDto);
	     
	     Optional<PriceRuleDTO> priceRuleDto=PriceRuleServiceImpl.findOne(offerDto.get().getPriceRuleId());
	     log.debug("****priceRuleDto****{}",priceRuleDto);
	     ksession.insert(priceRuleDto);
         
	     ksession.fireAllRules();
	  
	     log.debug("****ordermodel{}",orderModel.getOrderTotal());
	    // log.debug("******discount total{}",orderModel.getDiscountTotal());
	     log.debug("******orderrule*{}",orderRuleDto.get().getPrerequisiteOrderNumber());
	     
	     ksession.dispose(); 
	     //log.debug("******discount{}",orderModel.getOrderDiscountPercentage());
	     
	      
	      
	     
	}
}

