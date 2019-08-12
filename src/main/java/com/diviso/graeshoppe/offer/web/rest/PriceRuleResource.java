package com.diviso.graeshoppe.offer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.diviso.graeshoppe.offer.service.PriceRuleService;
import com.diviso.graeshoppe.offer.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.offer.web.rest.util.HeaderUtil;
import com.diviso.graeshoppe.offer.web.rest.util.PaginationUtil;
import com.diviso.graeshoppe.offer.service.dto.PriceRuleDTO;
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
 * REST controller for managing PriceRule.
 */
//@RestController
//@RequestMapping("/api")
public class PriceRuleResource {

    private final Logger log = LoggerFactory.getLogger(PriceRuleResource.class);

    private static final String ENTITY_NAME = "offerPriceRule";

    private final PriceRuleService priceRuleService;

    public PriceRuleResource(PriceRuleService priceRuleService) {
        this.priceRuleService = priceRuleService;
    }

    /**
     * POST  /price-rules : Create a new priceRule.
     *
     * @param priceRuleDTO the priceRuleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new priceRuleDTO, or with status 400 (Bad Request) if the priceRule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/price-rules")
    @Timed
    public ResponseEntity<PriceRuleDTO> createPriceRule(@RequestBody PriceRuleDTO priceRuleDTO) throws URISyntaxException {
        log.debug("REST request to save PriceRule : {}", priceRuleDTO);
        if (priceRuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new priceRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PriceRuleDTO result = priceRuleService.save(priceRuleDTO);
        return ResponseEntity.created(new URI("/api/price-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /price-rules : Updates an existing priceRule.
     *
     * @param priceRuleDTO the priceRuleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated priceRuleDTO,
     * or with status 400 (Bad Request) if the priceRuleDTO is not valid,
     * or with status 500 (Internal Server Error) if the priceRuleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/price-rules")
    @Timed
    public ResponseEntity<PriceRuleDTO> updatePriceRule(@RequestBody PriceRuleDTO priceRuleDTO) throws URISyntaxException {
        log.debug("REST request to update PriceRule : {}", priceRuleDTO);
        if (priceRuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PriceRuleDTO result = priceRuleService.save(priceRuleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, priceRuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /price-rules : get all the priceRules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of priceRules in body
     */
    @GetMapping("/price-rules")
    @Timed
    public ResponseEntity<List<PriceRuleDTO>> getAllPriceRules(Pageable pageable) {
        log.debug("REST request to get a page of PriceRules");
        Page<PriceRuleDTO> page = priceRuleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/price-rules");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /price-rules/:id : get the "id" priceRule.
     *
     * @param id the id of the priceRuleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the priceRuleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/price-rules/{id}")
    @Timed
    public ResponseEntity<PriceRuleDTO> getPriceRule(@PathVariable Long id) {
        log.debug("REST request to get PriceRule : {}", id);
        Optional<PriceRuleDTO> priceRuleDTO = priceRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(priceRuleDTO);
    }

    /**
     * DELETE  /price-rules/:id : delete the "id" priceRule.
     *
     * @param id the id of the priceRuleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/price-rules/{id}")
    @Timed
    public ResponseEntity<Void> deletePriceRule(@PathVariable Long id) {
        log.debug("REST request to delete PriceRule : {}", id);
        priceRuleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/price-rules?query=:query : search for the priceRule corresponding
     * to the query.
     *
     * @param query the query of the priceRule search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/price-rules")
    @Timed
    public ResponseEntity<List<PriceRuleDTO>> searchPriceRules(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PriceRules for query {}", query);
        Page<PriceRuleDTO> page = priceRuleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/price-rules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
