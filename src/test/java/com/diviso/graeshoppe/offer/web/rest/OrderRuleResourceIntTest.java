package com.diviso.graeshoppe.offer.web.rest;

import com.diviso.graeshoppe.offer.OfferApp;

import com.diviso.graeshoppe.offer.domain.OrderRule;
import com.diviso.graeshoppe.offer.repository.OrderRuleRepository;
import com.diviso.graeshoppe.offer.repository.search.OrderRuleSearchRepository;
import com.diviso.graeshoppe.offer.service.OrderRuleService;
import com.diviso.graeshoppe.offer.service.dto.OrderRuleDTO;
import com.diviso.graeshoppe.offer.service.mapper.OrderRuleMapper;
import com.diviso.graeshoppe.offer.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.diviso.graeshoppe.offer.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrderRuleResource REST controller.
 *
 * @see OrderRuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OfferApp.class)
public class OrderRuleResourceIntTest {

    private static final Long DEFAULT_PREREQUISITE_ORDER_NUMBER = 1L;
    private static final Long UPDATED_PREREQUISITE_ORDER_NUMBER = 2L;

    @Autowired
    private OrderRuleRepository orderRuleRepository;

    @Autowired
    private OrderRuleMapper orderRuleMapper;

    @Autowired
    private OrderRuleService orderRuleService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.offer.repository.search test package.
     *
     * @see com.diviso.graeshoppe.offer.repository.search.OrderRuleSearchRepositoryMockConfiguration
     */
    @Autowired
    private OrderRuleSearchRepository mockOrderRuleSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderRuleMockMvc;

    private OrderRule orderRule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderRuleResource orderRuleResource = new OrderRuleResource(orderRuleService);
        this.restOrderRuleMockMvc = MockMvcBuilders.standaloneSetup(orderRuleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderRule createEntity(EntityManager em) {
        OrderRule orderRule = new OrderRule()
            .prerequisiteOrderNumber(DEFAULT_PREREQUISITE_ORDER_NUMBER);
        return orderRule;
    }

    @Before
    public void initTest() {
        orderRule = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderRule() throws Exception {
        int databaseSizeBeforeCreate = orderRuleRepository.findAll().size();

        // Create the OrderRule
        OrderRuleDTO orderRuleDTO = orderRuleMapper.toDto(orderRule);
        restOrderRuleMockMvc.perform(post("/api/order-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderRuleDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderRule in the database
        List<OrderRule> orderRuleList = orderRuleRepository.findAll();
        assertThat(orderRuleList).hasSize(databaseSizeBeforeCreate + 1);
        OrderRule testOrderRule = orderRuleList.get(orderRuleList.size() - 1);
        assertThat(testOrderRule.getPrerequisiteOrderNumber()).isEqualTo(DEFAULT_PREREQUISITE_ORDER_NUMBER);

        // Validate the OrderRule in Elasticsearch
        verify(mockOrderRuleSearchRepository, times(1)).save(testOrderRule);
    }

    @Test
    @Transactional
    public void createOrderRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderRuleRepository.findAll().size();

        // Create the OrderRule with an existing ID
        orderRule.setId(1L);
        OrderRuleDTO orderRuleDTO = orderRuleMapper.toDto(orderRule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderRuleMockMvc.perform(post("/api/order-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderRuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderRule in the database
        List<OrderRule> orderRuleList = orderRuleRepository.findAll();
        assertThat(orderRuleList).hasSize(databaseSizeBeforeCreate);

        // Validate the OrderRule in Elasticsearch
        verify(mockOrderRuleSearchRepository, times(0)).save(orderRule);
    }

    @Test
    @Transactional
    public void getAllOrderRules() throws Exception {
        // Initialize the database
        orderRuleRepository.saveAndFlush(orderRule);

        // Get all the orderRuleList
        restOrderRuleMockMvc.perform(get("/api/order-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].prerequisiteOrderNumber").value(hasItem(DEFAULT_PREREQUISITE_ORDER_NUMBER.intValue())));
    }
    
    @Test
    @Transactional
    public void getOrderRule() throws Exception {
        // Initialize the database
        orderRuleRepository.saveAndFlush(orderRule);

        // Get the orderRule
        restOrderRuleMockMvc.perform(get("/api/order-rules/{id}", orderRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderRule.getId().intValue()))
            .andExpect(jsonPath("$.prerequisiteOrderNumber").value(DEFAULT_PREREQUISITE_ORDER_NUMBER.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderRule() throws Exception {
        // Get the orderRule
        restOrderRuleMockMvc.perform(get("/api/order-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderRule() throws Exception {
        // Initialize the database
        orderRuleRepository.saveAndFlush(orderRule);

        int databaseSizeBeforeUpdate = orderRuleRepository.findAll().size();

        // Update the orderRule
        OrderRule updatedOrderRule = orderRuleRepository.findById(orderRule.getId()).get();
        // Disconnect from session so that the updates on updatedOrderRule are not directly saved in db
        em.detach(updatedOrderRule);
        updatedOrderRule
            .prerequisiteOrderNumber(UPDATED_PREREQUISITE_ORDER_NUMBER);
        OrderRuleDTO orderRuleDTO = orderRuleMapper.toDto(updatedOrderRule);

        restOrderRuleMockMvc.perform(put("/api/order-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderRuleDTO)))
            .andExpect(status().isOk());

        // Validate the OrderRule in the database
        List<OrderRule> orderRuleList = orderRuleRepository.findAll();
        assertThat(orderRuleList).hasSize(databaseSizeBeforeUpdate);
        OrderRule testOrderRule = orderRuleList.get(orderRuleList.size() - 1);
        assertThat(testOrderRule.getPrerequisiteOrderNumber()).isEqualTo(UPDATED_PREREQUISITE_ORDER_NUMBER);

        // Validate the OrderRule in Elasticsearch
        verify(mockOrderRuleSearchRepository, times(1)).save(testOrderRule);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderRule() throws Exception {
        int databaseSizeBeforeUpdate = orderRuleRepository.findAll().size();

        // Create the OrderRule
        OrderRuleDTO orderRuleDTO = orderRuleMapper.toDto(orderRule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderRuleMockMvc.perform(put("/api/order-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderRuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderRule in the database
        List<OrderRule> orderRuleList = orderRuleRepository.findAll();
        assertThat(orderRuleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OrderRule in Elasticsearch
        verify(mockOrderRuleSearchRepository, times(0)).save(orderRule);
    }

    @Test
    @Transactional
    public void deleteOrderRule() throws Exception {
        // Initialize the database
        orderRuleRepository.saveAndFlush(orderRule);

        int databaseSizeBeforeDelete = orderRuleRepository.findAll().size();

        // Get the orderRule
        restOrderRuleMockMvc.perform(delete("/api/order-rules/{id}", orderRule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderRule> orderRuleList = orderRuleRepository.findAll();
        assertThat(orderRuleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OrderRule in Elasticsearch
        verify(mockOrderRuleSearchRepository, times(1)).deleteById(orderRule.getId());
    }

    @Test
    @Transactional
    public void searchOrderRule() throws Exception {
        // Initialize the database
        orderRuleRepository.saveAndFlush(orderRule);
        when(mockOrderRuleSearchRepository.search(queryStringQuery("id:" + orderRule.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(orderRule), PageRequest.of(0, 1), 1));
        // Search the orderRule
        restOrderRuleMockMvc.perform(get("/api/_search/order-rules?query=id:" + orderRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].prerequisiteOrderNumber").value(hasItem(DEFAULT_PREREQUISITE_ORDER_NUMBER.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderRule.class);
        OrderRule orderRule1 = new OrderRule();
        orderRule1.setId(1L);
        OrderRule orderRule2 = new OrderRule();
        orderRule2.setId(orderRule1.getId());
        assertThat(orderRule1).isEqualTo(orderRule2);
        orderRule2.setId(2L);
        assertThat(orderRule1).isNotEqualTo(orderRule2);
        orderRule1.setId(null);
        assertThat(orderRule1).isNotEqualTo(orderRule2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderRuleDTO.class);
        OrderRuleDTO orderRuleDTO1 = new OrderRuleDTO();
        orderRuleDTO1.setId(1L);
        OrderRuleDTO orderRuleDTO2 = new OrderRuleDTO();
        assertThat(orderRuleDTO1).isNotEqualTo(orderRuleDTO2);
        orderRuleDTO2.setId(orderRuleDTO1.getId());
        assertThat(orderRuleDTO1).isEqualTo(orderRuleDTO2);
        orderRuleDTO2.setId(2L);
        assertThat(orderRuleDTO1).isNotEqualTo(orderRuleDTO2);
        orderRuleDTO1.setId(null);
        assertThat(orderRuleDTO1).isNotEqualTo(orderRuleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orderRuleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orderRuleMapper.fromId(null)).isNull();
    }
}
