package com.diviso.graeshoppe.offer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.diviso.graeshoppe.offer.service.OfferTargetService;
import com.diviso.graeshoppe.offer.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.offer.web.rest.util.HeaderUtil;
import com.diviso.graeshoppe.offer.web.rest.util.PaginationUtil;
import com.diviso.graeshoppe.offer.service.dto.OfferTargetDTO;
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
 * REST controller for managing OfferTarget.
 */
//@RestController
//@RequestMapping("/api")
public class OfferTargetResource {

    private final Logger log = LoggerFactory.getLogger(OfferTargetResource.class);

    private static final String ENTITY_NAME = "offerOfferTarget";

    private final OfferTargetService offerTargetService;

    public OfferTargetResource(OfferTargetService offerTargetService) {
        this.offerTargetService = offerTargetService;
    }

    /**
     * POST  /offer-targets : Create a new offerTarget.
     *
     * @param offerTargetDTO the offerTargetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new offerTargetDTO, or with status 400 (Bad Request) if the offerTarget has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/offer-targets")
    @Timed
    public ResponseEntity<OfferTargetDTO> createOfferTarget(@RequestBody OfferTargetDTO offerTargetDTO) throws URISyntaxException {
        log.debug("REST request to save OfferTarget : {}", offerTargetDTO);
        if (offerTargetDTO.getId() != null) {
            throw new BadRequestAlertException("A new offerTarget cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfferTargetDTO result = offerTargetService.save(offerTargetDTO);
        return ResponseEntity.created(new URI("/api/offer-targets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /offer-targets : Updates an existing offerTarget.
     *
     * @param offerTargetDTO the offerTargetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated offerTargetDTO,
     * or with status 400 (Bad Request) if the offerTargetDTO is not valid,
     * or with status 500 (Internal Server Error) if the offerTargetDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/offer-targets")
    @Timed
    public ResponseEntity<OfferTargetDTO> updateOfferTarget(@RequestBody OfferTargetDTO offerTargetDTO) throws URISyntaxException {
        log.debug("REST request to update OfferTarget : {}", offerTargetDTO);
        if (offerTargetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OfferTargetDTO result = offerTargetService.save(offerTargetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, offerTargetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /offer-targets : get all the offerTargets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of offerTargets in body
     */
    @GetMapping("/offer-targets")
    @Timed
    public ResponseEntity<List<OfferTargetDTO>> getAllOfferTargets(Pageable pageable) {
        log.debug("REST request to get a page of OfferTargets");
        Page<OfferTargetDTO> page = offerTargetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/offer-targets");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /offer-targets/:id : get the "id" offerTarget.
     *
     * @param id the id of the offerTargetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the offerTargetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/offer-targets/{id}")
    @Timed
    public ResponseEntity<OfferTargetDTO> getOfferTarget(@PathVariable Long id) {
        log.debug("REST request to get OfferTarget : {}", id);
        Optional<OfferTargetDTO> offerTargetDTO = offerTargetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(offerTargetDTO);
    }

    /**
     * DELETE  /offer-targets/:id : delete the "id" offerTarget.
     *
     * @param id the id of the offerTargetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/offer-targets/{id}")
    @Timed
    public ResponseEntity<Void> deleteOfferTarget(@PathVariable Long id) {
        log.debug("REST request to delete OfferTarget : {}", id);
        offerTargetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/offer-targets?query=:query : search for the offerTarget corresponding
     * to the query.
     *
     * @param query the query of the offerTarget search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/offer-targets")
    @Timed
    public ResponseEntity<List<OfferTargetDTO>> searchOfferTargets(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OfferTargets for query {}", query);
        Page<OfferTargetDTO> page = offerTargetService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/offer-targets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
