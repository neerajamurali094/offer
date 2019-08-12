package com.diviso.graeshoppe.offer.web.rest;

import com.diviso.graeshoppe.offer.OfferApp;

import com.diviso.graeshoppe.offer.domain.PriceRule;
import com.diviso.graeshoppe.offer.repository.PriceRuleRepository;
import com.diviso.graeshoppe.offer.repository.search.PriceRuleSearchRepository;
import com.diviso.graeshoppe.offer.service.PriceRuleService;
import com.diviso.graeshoppe.offer.service.dto.PriceRuleDTO;
import com.diviso.graeshoppe.offer.service.mapper.PriceRuleMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Test class for the PriceRuleResource REST controller.
 *
 * @see PriceRuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OfferApp.class)
public class PriceRuleResourceIntTest {

    private static final Long DEFAULT_DEDUCTION_VALUE = 1L;
    private static final Long UPDATED_DEDUCTION_VALUE = 2L;

    private static final Long DEFAULT_ALLOCATION_LIMIT = 1L;
    private static final Long UPDATED_ALLOCATION_LIMIT = 2L;

    private static final Boolean DEFAULT_ONCE_PER_CUSTOMER = false;
    private static final Boolean UPDATED_ONCE_PER_CUSTOMER = true;

    private static final Long DEFAULT_USAGE_LIMIT = 1L;
    private static final Long UPDATED_USAGE_LIMIT = 2L;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_PREREQUISITE_SUBTOTAL_RANGE = 1D;
    private static final Double UPDATED_PREREQUISITE_SUBTOTAL_RANGE = 2D;

    private static final Double DEFAULT_PREREQUISITE_QUANTITY_RANGE = 1D;
    private static final Double UPDATED_PREREQUISITE_QUANTITY_RANGE = 2D;

    private static final Double DEFAULT_PREREQUISITE_SHIPPING_PRICE_RANGE = 1D;
    private static final Double UPDATED_PREREQUISITE_SHIPPING_PRICE_RANGE = 2D;

    @Autowired
    private PriceRuleRepository priceRuleRepository;

    @Autowired
    private PriceRuleMapper priceRuleMapper;

    @Autowired
    private PriceRuleService priceRuleService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.offer.repository.search test package.
     *
     * @see com.diviso.graeshoppe.offer.repository.search.PriceRuleSearchRepositoryMockConfiguration
     */
    @Autowired
    private PriceRuleSearchRepository mockPriceRuleSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPriceRuleMockMvc;

    private PriceRule priceRule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PriceRuleResource priceRuleResource = new PriceRuleResource(priceRuleService);
        this.restPriceRuleMockMvc = MockMvcBuilders.standaloneSetup(priceRuleResource)
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
    public static PriceRule createEntity(EntityManager em) {
        PriceRule priceRule = new PriceRule()
            .deductionValue(DEFAULT_DEDUCTION_VALUE)
            .allocationLimit(DEFAULT_ALLOCATION_LIMIT)
            .oncePerCustomer(DEFAULT_ONCE_PER_CUSTOMER)
            .usageLimit(DEFAULT_USAGE_LIMIT)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .prerequisiteSubtotalRange(DEFAULT_PREREQUISITE_SUBTOTAL_RANGE)
            .prerequisiteQuantityRange(DEFAULT_PREREQUISITE_QUANTITY_RANGE)
            .prerequisiteShippingPriceRange(DEFAULT_PREREQUISITE_SHIPPING_PRICE_RANGE);
        return priceRule;
    }

    @Before
    public void initTest() {
        priceRule = createEntity(em);
    }

    @Test
    @Transactional
    public void createPriceRule() throws Exception {
        int databaseSizeBeforeCreate = priceRuleRepository.findAll().size();

        // Create the PriceRule
        PriceRuleDTO priceRuleDTO = priceRuleMapper.toDto(priceRule);
        restPriceRuleMockMvc.perform(post("/api/price-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceRuleDTO)))
            .andExpect(status().isCreated());

        // Validate the PriceRule in the database
        List<PriceRule> priceRuleList = priceRuleRepository.findAll();
        assertThat(priceRuleList).hasSize(databaseSizeBeforeCreate + 1);
        PriceRule testPriceRule = priceRuleList.get(priceRuleList.size() - 1);
        assertThat(testPriceRule.getDeductionValue()).isEqualTo(DEFAULT_DEDUCTION_VALUE);
        assertThat(testPriceRule.getAllocationLimit()).isEqualTo(DEFAULT_ALLOCATION_LIMIT);
        assertThat(testPriceRule.isOncePerCustomer()).isEqualTo(DEFAULT_ONCE_PER_CUSTOMER);
        assertThat(testPriceRule.getUsageLimit()).isEqualTo(DEFAULT_USAGE_LIMIT);
        assertThat(testPriceRule.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPriceRule.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPriceRule.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPriceRule.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testPriceRule.getPrerequisiteSubtotalRange()).isEqualTo(DEFAULT_PREREQUISITE_SUBTOTAL_RANGE);
        assertThat(testPriceRule.getPrerequisiteQuantityRange()).isEqualTo(DEFAULT_PREREQUISITE_QUANTITY_RANGE);
        assertThat(testPriceRule.getPrerequisiteShippingPriceRange()).isEqualTo(DEFAULT_PREREQUISITE_SHIPPING_PRICE_RANGE);

        // Validate the PriceRule in Elasticsearch
        verify(mockPriceRuleSearchRepository, times(1)).save(testPriceRule);
    }

    @Test
    @Transactional
    public void createPriceRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = priceRuleRepository.findAll().size();

        // Create the PriceRule with an existing ID
        priceRule.setId(1L);
        PriceRuleDTO priceRuleDTO = priceRuleMapper.toDto(priceRule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriceRuleMockMvc.perform(post("/api/price-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceRuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PriceRule in the database
        List<PriceRule> priceRuleList = priceRuleRepository.findAll();
        assertThat(priceRuleList).hasSize(databaseSizeBeforeCreate);

        // Validate the PriceRule in Elasticsearch
        verify(mockPriceRuleSearchRepository, times(0)).save(priceRule);
    }

    @Test
    @Transactional
    public void getAllPriceRules() throws Exception {
        // Initialize the database
        priceRuleRepository.saveAndFlush(priceRule);

        // Get all the priceRuleList
        restPriceRuleMockMvc.perform(get("/api/price-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priceRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].deductionValue").value(hasItem(DEFAULT_DEDUCTION_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].allocationLimit").value(hasItem(DEFAULT_ALLOCATION_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].oncePerCustomer").value(hasItem(DEFAULT_ONCE_PER_CUSTOMER.booleanValue())))
            .andExpect(jsonPath("$.[*].usageLimit").value(hasItem(DEFAULT_USAGE_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].prerequisiteSubtotalRange").value(hasItem(DEFAULT_PREREQUISITE_SUBTOTAL_RANGE.doubleValue())))
            .andExpect(jsonPath("$.[*].prerequisiteQuantityRange").value(hasItem(DEFAULT_PREREQUISITE_QUANTITY_RANGE.doubleValue())))
            .andExpect(jsonPath("$.[*].prerequisiteShippingPriceRange").value(hasItem(DEFAULT_PREREQUISITE_SHIPPING_PRICE_RANGE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getPriceRule() throws Exception {
        // Initialize the database
        priceRuleRepository.saveAndFlush(priceRule);

        // Get the priceRule
        restPriceRuleMockMvc.perform(get("/api/price-rules/{id}", priceRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(priceRule.getId().intValue()))
            .andExpect(jsonPath("$.deductionValue").value(DEFAULT_DEDUCTION_VALUE.intValue()))
            .andExpect(jsonPath("$.allocationLimit").value(DEFAULT_ALLOCATION_LIMIT.intValue()))
            .andExpect(jsonPath("$.oncePerCustomer").value(DEFAULT_ONCE_PER_CUSTOMER.booleanValue()))
            .andExpect(jsonPath("$.usageLimit").value(DEFAULT_USAGE_LIMIT.intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.prerequisiteSubtotalRange").value(DEFAULT_PREREQUISITE_SUBTOTAL_RANGE.doubleValue()))
            .andExpect(jsonPath("$.prerequisiteQuantityRange").value(DEFAULT_PREREQUISITE_QUANTITY_RANGE.doubleValue()))
            .andExpect(jsonPath("$.prerequisiteShippingPriceRange").value(DEFAULT_PREREQUISITE_SHIPPING_PRICE_RANGE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPriceRule() throws Exception {
        // Get the priceRule
        restPriceRuleMockMvc.perform(get("/api/price-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePriceRule() throws Exception {
        // Initialize the database
        priceRuleRepository.saveAndFlush(priceRule);

        int databaseSizeBeforeUpdate = priceRuleRepository.findAll().size();

        // Update the priceRule
        PriceRule updatedPriceRule = priceRuleRepository.findById(priceRule.getId()).get();
        // Disconnect from session so that the updates on updatedPriceRule are not directly saved in db
        em.detach(updatedPriceRule);
        updatedPriceRule
            .deductionValue(UPDATED_DEDUCTION_VALUE)
            .allocationLimit(UPDATED_ALLOCATION_LIMIT)
            .oncePerCustomer(UPDATED_ONCE_PER_CUSTOMER)
            .usageLimit(UPDATED_USAGE_LIMIT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .prerequisiteSubtotalRange(UPDATED_PREREQUISITE_SUBTOTAL_RANGE)
            .prerequisiteQuantityRange(UPDATED_PREREQUISITE_QUANTITY_RANGE)
            .prerequisiteShippingPriceRange(UPDATED_PREREQUISITE_SHIPPING_PRICE_RANGE);
        PriceRuleDTO priceRuleDTO = priceRuleMapper.toDto(updatedPriceRule);

        restPriceRuleMockMvc.perform(put("/api/price-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceRuleDTO)))
            .andExpect(status().isOk());

        // Validate the PriceRule in the database
        List<PriceRule> priceRuleList = priceRuleRepository.findAll();
        assertThat(priceRuleList).hasSize(databaseSizeBeforeUpdate);
        PriceRule testPriceRule = priceRuleList.get(priceRuleList.size() - 1);
        assertThat(testPriceRule.getDeductionValue()).isEqualTo(UPDATED_DEDUCTION_VALUE);
        assertThat(testPriceRule.getAllocationLimit()).isEqualTo(UPDATED_ALLOCATION_LIMIT);
        assertThat(testPriceRule.isOncePerCustomer()).isEqualTo(UPDATED_ONCE_PER_CUSTOMER);
        assertThat(testPriceRule.getUsageLimit()).isEqualTo(UPDATED_USAGE_LIMIT);
        assertThat(testPriceRule.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPriceRule.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPriceRule.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPriceRule.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testPriceRule.getPrerequisiteSubtotalRange()).isEqualTo(UPDATED_PREREQUISITE_SUBTOTAL_RANGE);
        assertThat(testPriceRule.getPrerequisiteQuantityRange()).isEqualTo(UPDATED_PREREQUISITE_QUANTITY_RANGE);
        assertThat(testPriceRule.getPrerequisiteShippingPriceRange()).isEqualTo(UPDATED_PREREQUISITE_SHIPPING_PRICE_RANGE);

        // Validate the PriceRule in Elasticsearch
        verify(mockPriceRuleSearchRepository, times(1)).save(testPriceRule);
    }

    @Test
    @Transactional
    public void updateNonExistingPriceRule() throws Exception {
        int databaseSizeBeforeUpdate = priceRuleRepository.findAll().size();

        // Create the PriceRule
        PriceRuleDTO priceRuleDTO = priceRuleMapper.toDto(priceRule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPriceRuleMockMvc.perform(put("/api/price-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceRuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PriceRule in the database
        List<PriceRule> priceRuleList = priceRuleRepository.findAll();
        assertThat(priceRuleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PriceRule in Elasticsearch
        verify(mockPriceRuleSearchRepository, times(0)).save(priceRule);
    }

    @Test
    @Transactional
    public void deletePriceRule() throws Exception {
        // Initialize the database
        priceRuleRepository.saveAndFlush(priceRule);

        int databaseSizeBeforeDelete = priceRuleRepository.findAll().size();

        // Get the priceRule
        restPriceRuleMockMvc.perform(delete("/api/price-rules/{id}", priceRule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PriceRule> priceRuleList = priceRuleRepository.findAll();
        assertThat(priceRuleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PriceRule in Elasticsearch
        verify(mockPriceRuleSearchRepository, times(1)).deleteById(priceRule.getId());
    }

    @Test
    @Transactional
    public void searchPriceRule() throws Exception {
        // Initialize the database
        priceRuleRepository.saveAndFlush(priceRule);
        when(mockPriceRuleSearchRepository.search(queryStringQuery("id:" + priceRule.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(priceRule), PageRequest.of(0, 1), 1));
        // Search the priceRule
        restPriceRuleMockMvc.perform(get("/api/_search/price-rules?query=id:" + priceRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priceRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].deductionValue").value(hasItem(DEFAULT_DEDUCTION_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].allocationLimit").value(hasItem(DEFAULT_ALLOCATION_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].oncePerCustomer").value(hasItem(DEFAULT_ONCE_PER_CUSTOMER.booleanValue())))
            .andExpect(jsonPath("$.[*].usageLimit").value(hasItem(DEFAULT_USAGE_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].prerequisiteSubtotalRange").value(hasItem(DEFAULT_PREREQUISITE_SUBTOTAL_RANGE.doubleValue())))
            .andExpect(jsonPath("$.[*].prerequisiteQuantityRange").value(hasItem(DEFAULT_PREREQUISITE_QUANTITY_RANGE.doubleValue())))
            .andExpect(jsonPath("$.[*].prerequisiteShippingPriceRange").value(hasItem(DEFAULT_PREREQUISITE_SHIPPING_PRICE_RANGE.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceRule.class);
        PriceRule priceRule1 = new PriceRule();
        priceRule1.setId(1L);
        PriceRule priceRule2 = new PriceRule();
        priceRule2.setId(priceRule1.getId());
        assertThat(priceRule1).isEqualTo(priceRule2);
        priceRule2.setId(2L);
        assertThat(priceRule1).isNotEqualTo(priceRule2);
        priceRule1.setId(null);
        assertThat(priceRule1).isNotEqualTo(priceRule2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceRuleDTO.class);
        PriceRuleDTO priceRuleDTO1 = new PriceRuleDTO();
        priceRuleDTO1.setId(1L);
        PriceRuleDTO priceRuleDTO2 = new PriceRuleDTO();
        assertThat(priceRuleDTO1).isNotEqualTo(priceRuleDTO2);
        priceRuleDTO2.setId(priceRuleDTO1.getId());
        assertThat(priceRuleDTO1).isEqualTo(priceRuleDTO2);
        priceRuleDTO2.setId(2L);
        assertThat(priceRuleDTO1).isNotEqualTo(priceRuleDTO2);
        priceRuleDTO1.setId(null);
        assertThat(priceRuleDTO1).isNotEqualTo(priceRuleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(priceRuleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(priceRuleMapper.fromId(null)).isNull();
    }
}
