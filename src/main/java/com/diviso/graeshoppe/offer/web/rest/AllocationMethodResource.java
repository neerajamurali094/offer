package com.diviso.graeshoppe.offer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.diviso.graeshoppe.offer.service.AllocationMethodService;
import com.diviso.graeshoppe.offer.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.offer.web.rest.util.HeaderUtil;
import com.diviso.graeshoppe.offer.web.rest.util.PaginationUtil;
import com.diviso.graeshoppe.offer.service.dto.AllocationMethodDTO;
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
 * REST controller for managing AllocationMethod.
 */
//@RestController
//@RequestMapping("/api")
public class AllocationMethodResource {

    private final Logger log = LoggerFactory.getLogger(AllocationMethodResource.class);

    private static final String ENTITY_NAME = "offerAllocationMethod";

    private final AllocationMethodService allocationMethodService;

    public AllocationMethodResource(AllocationMethodService allocationMethodService) {
        this.allocationMethodService = allocationMethodService;
    }

    /**
     * POST  /allocation-methods : Create a new allocationMethod.
     *
     * @param allocationMethodDTO the allocationMethodDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new allocationMethodDTO, or with status 400 (Bad Request) if the allocationMethod has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/allocation-methods")
    @Timed
    public ResponseEntity<AllocationMethodDTO> createAllocationMethod(@RequestBody AllocationMethodDTO allocationMethodDTO) throws URISyntaxException {
        log.debug("REST request to save AllocationMethod : {}", allocationMethodDTO);
        if (allocationMethodDTO.getId() != null) {
            throw new BadRequestAlertException("A new allocationMethod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AllocationMethodDTO result = allocationMethodService.save(allocationMethodDTO);
        return ResponseEntity.created(new URI("/api/allocation-methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /allocation-methods : Updates an existing allocationMethod.
     *
     * @param allocationMethodDTO the allocationMethodDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated allocationMethodDTO,
     * or with status 400 (Bad Request) if the allocationMethodDTO is not valid,
     * or with status 500 (Internal Server Error) if the allocationMethodDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/allocation-methods")
    @Timed
    public ResponseEntity<AllocationMethodDTO> updateAllocationMethod(@RequestBody AllocationMethodDTO allocationMethodDTO) throws URISyntaxException {
        log.debug("REST request to update AllocationMethod : {}", allocationMethodDTO);
        if (allocationMethodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AllocationMethodDTO result = allocationMethodService.save(allocationMethodDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, allocationMethodDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /allocation-methods : get all the allocationMethods.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of allocationMethods in body
     */
    @GetMapping("/allocation-methods")
    @Timed
    public ResponseEntity<List<AllocationMethodDTO>> getAllAllocationMethods(Pageable pageable) {
        log.debug("REST request to get a page of AllocationMethods");
        Page<AllocationMethodDTO> page = allocationMethodService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/allocation-methods");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /allocation-methods/:id : get the "id" allocationMethod.
     *
     * @param id the id of the allocationMethodDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the allocationMethodDTO, or with status 404 (Not Found)
     */
    @GetMapping("/allocation-methods/{id}")
    @Timed
    public ResponseEntity<AllocationMethodDTO> getAllocationMethod(@PathVariable Long id) {
        log.debug("REST request to get AllocationMethod : {}", id);
        Optional<AllocationMethodDTO> allocationMethodDTO = allocationMethodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(allocationMethodDTO);
    }

    /**
     * DELETE  /allocation-methods/:id : delete the "id" allocationMethod.
     *
     * @param id the id of the allocationMethodDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/allocation-methods/{id}")
    @Timed
    public ResponseEntity<Void> deleteAllocationMethod(@PathVariable Long id) {
        log.debug("REST request to delete AllocationMethod : {}", id);
        allocationMethodService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/allocation-methods?query=:query : search for the allocationMethod corresponding
     * to the query.
     *
     * @param query the query of the allocationMethod search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/allocation-methods")
    @Timed
    public ResponseEntity<List<AllocationMethodDTO>> searchAllocationMethods(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AllocationMethods for query {}", query);
        Page<AllocationMethodDTO> page = allocationMethodService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/allocation-methods");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
