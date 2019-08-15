package com.diviso.graeshoppe.offer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.diviso.graeshoppe.offer.service.DeductionValueTypeService;
import com.diviso.graeshoppe.offer.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.offer.web.rest.util.HeaderUtil;
import com.diviso.graeshoppe.offer.web.rest.util.PaginationUtil;
import com.diviso.graeshoppe.offer.service.dto.DeductionValueTypeDTO;
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
 * REST controller for managing DeductionValueType.
 */
@RestController
@RequestMapping("/api")
public class DeductionValueTypeResource {

    private final Logger log = LoggerFactory.getLogger(DeductionValueTypeResource.class);

    private static final String ENTITY_NAME = "offerDeductionValueType";

    private final DeductionValueTypeService deductionValueTypeService;

    public DeductionValueTypeResource(DeductionValueTypeService deductionValueTypeService) {
        this.deductionValueTypeService = deductionValueTypeService;
    }

    /**
     * POST  /deduction-value-types : Create a new deductionValueType.
     *
     * @param deductionValueTypeDTO the deductionValueTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deductionValueTypeDTO, or with status 400 (Bad Request) if the deductionValueType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/deduction-value-types")
    @Timed
    public ResponseEntity<DeductionValueTypeDTO> createDeductionValueType(@RequestBody DeductionValueTypeDTO deductionValueTypeDTO) throws URISyntaxException {
        log.debug("REST request to save DeductionValueType : {}", deductionValueTypeDTO);
        if (deductionValueTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new deductionValueType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeductionValueTypeDTO result = deductionValueTypeService.save(deductionValueTypeDTO);
        return ResponseEntity.created(new URI("/api/deduction-value-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deduction-value-types : Updates an existing deductionValueType.
     *
     * @param deductionValueTypeDTO the deductionValueTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deductionValueTypeDTO,
     * or with status 400 (Bad Request) if the deductionValueTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the deductionValueTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/deduction-value-types")
    @Timed
    public ResponseEntity<DeductionValueTypeDTO> updateDeductionValueType(@RequestBody DeductionValueTypeDTO deductionValueTypeDTO) throws URISyntaxException {
        log.debug("REST request to update DeductionValueType : {}", deductionValueTypeDTO);
        if (deductionValueTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeductionValueTypeDTO result = deductionValueTypeService.save(deductionValueTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deductionValueTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deduction-value-types : get all the deductionValueTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of deductionValueTypes in body
     */
    @GetMapping("/deduction-value-types")
    @Timed
    public ResponseEntity<List<DeductionValueTypeDTO>> getAllDeductionValueTypes(Pageable pageable) {
        log.debug("REST request to get a page of DeductionValueTypes");
        Page<DeductionValueTypeDTO> page = deductionValueTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/deduction-value-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /deduction-value-types/:id : get the "id" deductionValueType.
     *
     * @param id the id of the deductionValueTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deductionValueTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/deduction-value-types/{id}")
    @Timed
    public ResponseEntity<DeductionValueTypeDTO> getDeductionValueType(@PathVariable Long id) {
        log.debug("REST request to get DeductionValueType : {}", id);
        Optional<DeductionValueTypeDTO> deductionValueTypeDTO = deductionValueTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deductionValueTypeDTO);
    }

    /**
     * DELETE  /deduction-value-types/:id : delete the "id" deductionValueType.
     *
     * @param id the id of the deductionValueTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/deduction-value-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeductionValueType(@PathVariable Long id) {
        log.debug("REST request to delete DeductionValueType : {}", id);
        deductionValueTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/deduction-value-types?query=:query : search for the deductionValueType corresponding
     * to the query.
     *
     * @param query the query of the deductionValueType search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/deduction-value-types")
    @Timed
    public ResponseEntity<List<DeductionValueTypeDTO>> searchDeductionValueTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DeductionValueTypes for query {}", query);
        Page<DeductionValueTypeDTO> page = deductionValueTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/deduction-value-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
