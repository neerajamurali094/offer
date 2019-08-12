package com.diviso.graeshoppe.offer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.diviso.graeshoppe.offer.service.TargetTypeService;
import com.diviso.graeshoppe.offer.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.offer.web.rest.util.HeaderUtil;
import com.diviso.graeshoppe.offer.web.rest.util.PaginationUtil;
import com.diviso.graeshoppe.offer.service.dto.TargetTypeDTO;
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
 * REST controller for managing TargetType.
 */
//@RestController
//@RequestMapping("/api")
public class TargetTypeResource {

    private final Logger log = LoggerFactory.getLogger(TargetTypeResource.class);

    private static final String ENTITY_NAME = "offerTargetType";

    private final TargetTypeService targetTypeService;

    public TargetTypeResource(TargetTypeService targetTypeService) {
        this.targetTypeService = targetTypeService;
    }

    /**
     * POST  /target-types : Create a new targetType.
     *
     * @param targetTypeDTO the targetTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new targetTypeDTO, or with status 400 (Bad Request) if the targetType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/target-types")
    @Timed
    public ResponseEntity<TargetTypeDTO> createTargetType(@RequestBody TargetTypeDTO targetTypeDTO) throws URISyntaxException {
        log.debug("REST request to save TargetType : {}", targetTypeDTO);
        if (targetTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new targetType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TargetTypeDTO result = targetTypeService.save(targetTypeDTO);
        return ResponseEntity.created(new URI("/api/target-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /target-types : Updates an existing targetType.
     *
     * @param targetTypeDTO the targetTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated targetTypeDTO,
     * or with status 400 (Bad Request) if the targetTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the targetTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/target-types")
    @Timed
    public ResponseEntity<TargetTypeDTO> updateTargetType(@RequestBody TargetTypeDTO targetTypeDTO) throws URISyntaxException {
        log.debug("REST request to update TargetType : {}", targetTypeDTO);
        if (targetTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TargetTypeDTO result = targetTypeService.save(targetTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, targetTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /target-types : get all the targetTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of targetTypes in body
     */
    @GetMapping("/target-types")
    @Timed
    public ResponseEntity<List<TargetTypeDTO>> getAllTargetTypes(Pageable pageable) {
        log.debug("REST request to get a page of TargetTypes");
        Page<TargetTypeDTO> page = targetTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/target-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /target-types/:id : get the "id" targetType.
     *
     * @param id the id of the targetTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the targetTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/target-types/{id}")
    @Timed
    public ResponseEntity<TargetTypeDTO> getTargetType(@PathVariable Long id) {
        log.debug("REST request to get TargetType : {}", id);
        Optional<TargetTypeDTO> targetTypeDTO = targetTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(targetTypeDTO);
    }

    /**
     * DELETE  /target-types/:id : delete the "id" targetType.
     *
     * @param id the id of the targetTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/target-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteTargetType(@PathVariable Long id) {
        log.debug("REST request to delete TargetType : {}", id);
        targetTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/target-types?query=:query : search for the targetType corresponding
     * to the query.
     *
     * @param query the query of the targetType search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/target-types")
    @Timed
    public ResponseEntity<List<TargetTypeDTO>> searchTargetTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TargetTypes for query {}", query);
        Page<TargetTypeDTO> page = targetTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/target-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
