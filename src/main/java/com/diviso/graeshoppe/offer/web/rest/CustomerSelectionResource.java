package com.diviso.graeshoppe.offer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.diviso.graeshoppe.offer.service.CustomerSelectionService;
import com.diviso.graeshoppe.offer.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.offer.web.rest.util.HeaderUtil;
import com.diviso.graeshoppe.offer.web.rest.util.PaginationUtil;
import com.diviso.graeshoppe.offer.service.dto.CustomerSelectionDTO;
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
 * REST controller for managing CustomerSelection.
 */
//@RestController
//@RequestMapping("/api")
public class CustomerSelectionResource {

    private final Logger log = LoggerFactory.getLogger(CustomerSelectionResource.class);

    private static final String ENTITY_NAME = "offerCustomerSelection";

    private final CustomerSelectionService customerSelectionService;

    public CustomerSelectionResource(CustomerSelectionService customerSelectionService) {
        this.customerSelectionService = customerSelectionService;
    }

    /**
     * POST  /customer-selections : Create a new customerSelection.
     *
     * @param customerSelectionDTO the customerSelectionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerSelectionDTO, or with status 400 (Bad Request) if the customerSelection has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-selections")
    @Timed
    public ResponseEntity<CustomerSelectionDTO> createCustomerSelection(@RequestBody CustomerSelectionDTO customerSelectionDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerSelection : {}", customerSelectionDTO);
        if (customerSelectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerSelection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerSelectionDTO result = customerSelectionService.save(customerSelectionDTO);
        return ResponseEntity.created(new URI("/api/customer-selections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-selections : Updates an existing customerSelection.
     *
     * @param customerSelectionDTO the customerSelectionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerSelectionDTO,
     * or with status 400 (Bad Request) if the customerSelectionDTO is not valid,
     * or with status 500 (Internal Server Error) if the customerSelectionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-selections")
    @Timed
    public ResponseEntity<CustomerSelectionDTO> updateCustomerSelection(@RequestBody CustomerSelectionDTO customerSelectionDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerSelection : {}", customerSelectionDTO);
        if (customerSelectionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerSelectionDTO result = customerSelectionService.save(customerSelectionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerSelectionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-selections : get all the customerSelections.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of customerSelections in body
     */
    @GetMapping("/customer-selections")
    @Timed
    public ResponseEntity<List<CustomerSelectionDTO>> getAllCustomerSelections(Pageable pageable) {
        log.debug("REST request to get a page of CustomerSelections");
        Page<CustomerSelectionDTO> page = customerSelectionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-selections");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /customer-selections/:id : get the "id" customerSelection.
     *
     * @param id the id of the customerSelectionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerSelectionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/customer-selections/{id}")
    @Timed
    public ResponseEntity<CustomerSelectionDTO> getCustomerSelection(@PathVariable Long id) {
        log.debug("REST request to get CustomerSelection : {}", id);
        Optional<CustomerSelectionDTO> customerSelectionDTO = customerSelectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerSelectionDTO);
    }

    /**
     * DELETE  /customer-selections/:id : delete the "id" customerSelection.
     *
     * @param id the id of the customerSelectionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-selections/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerSelection(@PathVariable Long id) {
        log.debug("REST request to delete CustomerSelection : {}", id);
        customerSelectionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/customer-selections?query=:query : search for the customerSelection corresponding
     * to the query.
     *
     * @param query the query of the customerSelection search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/customer-selections")
    @Timed
    public ResponseEntity<List<CustomerSelectionDTO>> searchCustomerSelections(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CustomerSelections for query {}", query);
        Page<CustomerSelectionDTO> page = customerSelectionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/customer-selections");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
