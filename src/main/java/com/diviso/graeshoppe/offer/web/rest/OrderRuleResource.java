package com.diviso.graeshoppe.offer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.diviso.graeshoppe.offer.service.OrderRuleService;
import com.diviso.graeshoppe.offer.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.offer.web.rest.util.HeaderUtil;
import com.diviso.graeshoppe.offer.web.rest.util.PaginationUtil;
import com.diviso.graeshoppe.offer.service.dto.OrderRuleDTO;
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
 * REST controller for managing OrderRule.
 */
//@RestController
//@RequestMapping("/api")
public class OrderRuleResource {

    private final Logger log = LoggerFactory.getLogger(OrderRuleResource.class);

    private static final String ENTITY_NAME = "offerOrderRule";

    private final OrderRuleService orderRuleService;

    public OrderRuleResource(OrderRuleService orderRuleService) {
        this.orderRuleService = orderRuleService;
    }

    /**
     * POST  /order-rules : Create a new orderRule.
     *
     * @param orderRuleDTO the orderRuleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderRuleDTO, or with status 400 (Bad Request) if the orderRule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-rules")
    @Timed
    public ResponseEntity<OrderRuleDTO> createOrderRule(@RequestBody OrderRuleDTO orderRuleDTO) throws URISyntaxException {
        log.debug("REST request to save OrderRule : {}", orderRuleDTO);
        if (orderRuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderRuleDTO result = orderRuleService.save(orderRuleDTO);
        return ResponseEntity.created(new URI("/api/order-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-rules : Updates an existing orderRule.
     *
     * @param orderRuleDTO the orderRuleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderRuleDTO,
     * or with status 400 (Bad Request) if the orderRuleDTO is not valid,
     * or with status 500 (Internal Server Error) if the orderRuleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-rules")
    @Timed
    public ResponseEntity<OrderRuleDTO> updateOrderRule(@RequestBody OrderRuleDTO orderRuleDTO) throws URISyntaxException {
        log.debug("REST request to update OrderRule : {}", orderRuleDTO);
        if (orderRuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderRuleDTO result = orderRuleService.save(orderRuleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderRuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-rules : get all the orderRules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of orderRules in body
     */
    @GetMapping("/order-rules")
    @Timed
    public ResponseEntity<List<OrderRuleDTO>> getAllOrderRules(Pageable pageable) {
        log.debug("REST request to get a page of OrderRules");
        Page<OrderRuleDTO> page = orderRuleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/order-rules");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /order-rules/:id : get the "id" orderRule.
     *
     * @param id the id of the orderRuleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderRuleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/order-rules/{id}")
    @Timed
    public ResponseEntity<OrderRuleDTO> getOrderRule(@PathVariable Long id) {
        log.debug("REST request to get OrderRule : {}", id);
        Optional<OrderRuleDTO> orderRuleDTO = orderRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderRuleDTO);
    }

    /**
     * DELETE  /order-rules/:id : delete the "id" orderRule.
     *
     * @param id the id of the orderRuleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-rules/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderRule(@PathVariable Long id) {
        log.debug("REST request to delete OrderRule : {}", id);
        orderRuleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/order-rules?query=:query : search for the orderRule corresponding
     * to the query.
     *
     * @param query the query of the orderRule search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/order-rules")
    @Timed
    public ResponseEntity<List<OrderRuleDTO>> searchOrderRules(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OrderRules for query {}", query);
        Page<OrderRuleDTO> page = orderRuleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/order-rules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
