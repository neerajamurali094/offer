package com.diviso.graeshoppe.offer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.diviso.graeshoppe.offer.service.PaymentRuleService;
import com.diviso.graeshoppe.offer.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.offer.web.rest.util.HeaderUtil;
import com.diviso.graeshoppe.offer.web.rest.util.PaginationUtil;
import com.diviso.graeshoppe.offer.service.dto.PaymentRuleDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PaymentRule.
 */
//@RestController
//@RequestMapping("/api")
public class PaymentRuleResource {

    private final Logger log = LoggerFactory.getLogger(PaymentRuleResource.class);

    private static final String ENTITY_NAME = "offerPaymentRule";

    private final PaymentRuleService paymentRuleService;

    public PaymentRuleResource(PaymentRuleService paymentRuleService) {
        this.paymentRuleService = paymentRuleService;
    }

    /**
     * POST  /payment-rules : Create a new paymentRule.
     *
     * @param paymentRuleDTO the paymentRuleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paymentRuleDTO, or with status 400 (Bad Request) if the paymentRule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payment-rules")
    @Timed
    public ResponseEntity<PaymentRuleDTO> createPaymentRule(@RequestBody PaymentRuleDTO paymentRuleDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentRule : {}", paymentRuleDTO);
        if (paymentRuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentRuleDTO result = paymentRuleService.save(paymentRuleDTO);
        return ResponseEntity.created(new URI("/api/payment-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payment-rules : Updates an existing paymentRule.
     *
     * @param paymentRuleDTO the paymentRuleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paymentRuleDTO,
     * or with status 400 (Bad Request) if the paymentRuleDTO is not valid,
     * or with status 500 (Internal Server Error) if the paymentRuleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payment-rules")
    @Timed
    public ResponseEntity<PaymentRuleDTO> updatePaymentRule(@RequestBody PaymentRuleDTO paymentRuleDTO) throws URISyntaxException {
        log.debug("REST request to update PaymentRule : {}", paymentRuleDTO);
        if (paymentRuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentRuleDTO result = paymentRuleService.save(paymentRuleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paymentRuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payment-rules : get all the paymentRules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of paymentRules in body
     */
    @GetMapping("/payment-rules")
    @Timed
    public ResponseEntity<List<PaymentRuleDTO>> getAllPaymentRules(Pageable pageable) {
        log.debug("REST request to get a page of PaymentRules");
        Page<PaymentRuleDTO> page = paymentRuleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/payment-rules");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /payment-rules/:id : get the "id" paymentRule.
     *
     * @param id the id of the paymentRuleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paymentRuleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/payment-rules/{id}")
    @Timed
    public ResponseEntity<PaymentRuleDTO> getPaymentRule(@PathVariable Long id) {
        log.debug("REST request to get PaymentRule : {}", id);
        Optional<PaymentRuleDTO> paymentRuleDTO = paymentRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentRuleDTO);
    }

    /**
     * DELETE  /payment-rules/:id : delete the "id" paymentRule.
     *
     * @param id the id of the paymentRuleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payment-rules/{id}")
    @Timed
    public ResponseEntity<Void> deletePaymentRule(@PathVariable Long id) {
        log.debug("REST request to delete PaymentRule : {}", id);
        paymentRuleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/payment-rules?query=:query : search for the paymentRule corresponding
     * to the query.
     *
     * @param query the query of the paymentRule search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/payment-rules")
    @Timed
    public ResponseEntity<List<PaymentRuleDTO>> searchPaymentRules(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PaymentRules for query {}", query);
        Page<PaymentRuleDTO> page = paymentRuleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/payment-rules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
