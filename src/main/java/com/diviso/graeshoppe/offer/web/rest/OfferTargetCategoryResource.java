package com.diviso.graeshoppe.offer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.diviso.graeshoppe.offer.service.OfferTargetCategoryService;
import com.diviso.graeshoppe.offer.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.offer.web.rest.util.HeaderUtil;
import com.diviso.graeshoppe.offer.web.rest.util.PaginationUtil;
import com.diviso.graeshoppe.offer.service.dto.OfferTargetCategoryDTO;
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
 * REST controller for managing OfferTargetCategory.
 */
//@RestController
//@RequestMapping("/api")
public class OfferTargetCategoryResource {

    private final Logger log = LoggerFactory.getLogger(OfferTargetCategoryResource.class);

    private static final String ENTITY_NAME = "offerOfferTargetCategory";

    private final OfferTargetCategoryService offerTargetCategoryService;

    public OfferTargetCategoryResource(OfferTargetCategoryService offerTargetCategoryService) {
        this.offerTargetCategoryService = offerTargetCategoryService;
    }

    /**
     * POST  /offer-target-categories : Create a new offerTargetCategory.
     *
     * @param offerTargetCategoryDTO the offerTargetCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new offerTargetCategoryDTO, or with status 400 (Bad Request) if the offerTargetCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/offer-target-categories")
    @Timed
    public ResponseEntity<OfferTargetCategoryDTO> createOfferTargetCategory(@RequestBody OfferTargetCategoryDTO offerTargetCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save OfferTargetCategory : {}", offerTargetCategoryDTO);
        if (offerTargetCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new offerTargetCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfferTargetCategoryDTO result = offerTargetCategoryService.save(offerTargetCategoryDTO);
        return ResponseEntity.created(new URI("/api/offer-target-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /offer-target-categories : Updates an existing offerTargetCategory.
     *
     * @param offerTargetCategoryDTO the offerTargetCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated offerTargetCategoryDTO,
     * or with status 400 (Bad Request) if the offerTargetCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the offerTargetCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/offer-target-categories")
    @Timed
    public ResponseEntity<OfferTargetCategoryDTO> updateOfferTargetCategory(@RequestBody OfferTargetCategoryDTO offerTargetCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update OfferTargetCategory : {}", offerTargetCategoryDTO);
        if (offerTargetCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OfferTargetCategoryDTO result = offerTargetCategoryService.save(offerTargetCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, offerTargetCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /offer-target-categories : get all the offerTargetCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of offerTargetCategories in body
     */
    @GetMapping("/offer-target-categories")
    @Timed
    public ResponseEntity<List<OfferTargetCategoryDTO>> getAllOfferTargetCategories(Pageable pageable) {
        log.debug("REST request to get a page of OfferTargetCategories");
        Page<OfferTargetCategoryDTO> page = offerTargetCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/offer-target-categories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /offer-target-categories/:id : get the "id" offerTargetCategory.
     *
     * @param id the id of the offerTargetCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the offerTargetCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/offer-target-categories/{id}")
    @Timed
    public ResponseEntity<OfferTargetCategoryDTO> getOfferTargetCategory(@PathVariable Long id) {
        log.debug("REST request to get OfferTargetCategory : {}", id);
        Optional<OfferTargetCategoryDTO> offerTargetCategoryDTO = offerTargetCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(offerTargetCategoryDTO);
    }

    /**
     * DELETE  /offer-target-categories/:id : delete the "id" offerTargetCategory.
     *
     * @param id the id of the offerTargetCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/offer-target-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteOfferTargetCategory(@PathVariable Long id) {
        log.debug("REST request to delete OfferTargetCategory : {}", id);
        offerTargetCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/offer-target-categories?query=:query : search for the offerTargetCategory corresponding
     * to the query.
     *
     * @param query the query of the offerTargetCategory search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/offer-target-categories")
    @Timed
    public ResponseEntity<List<OfferTargetCategoryDTO>> searchOfferTargetCategories(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OfferTargetCategories for query {}", query);
        Page<OfferTargetCategoryDTO> page = offerTargetCategoryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/offer-target-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
