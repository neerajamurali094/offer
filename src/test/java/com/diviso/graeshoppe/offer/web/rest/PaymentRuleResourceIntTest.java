package com.diviso.graeshoppe.offer.web.rest;

import com.diviso.graeshoppe.offer.OfferApp;

import com.diviso.graeshoppe.offer.domain.PaymentRule;
import com.diviso.graeshoppe.offer.repository.PaymentRuleRepository;
import com.diviso.graeshoppe.offer.repository.search.PaymentRuleSearchRepository;
import com.diviso.graeshoppe.offer.service.PaymentRuleService;
import com.diviso.graeshoppe.offer.service.dto.PaymentRuleDTO;
import com.diviso.graeshoppe.offer.service.mapper.PaymentRuleMapper;
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
 * Test class for the PaymentRuleResource REST controller.
 *
 * @see PaymentRuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OfferApp.class)
public class PaymentRuleResourceIntTest {

    private static final String DEFAULT_PAYMENT_MODE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_MODE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHORIZED_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_AUTHORIZED_PROVIDER = "BBBBBBBBBB";

    private static final Long DEFAULT_CASH_BACK_TYPE = 1L;
    private static final Long UPDATED_CASH_BACK_TYPE = 2L;

    private static final Long DEFAULT_CASH_BACK_VALUE = 1L;
    private static final Long UPDATED_CASH_BACK_VALUE = 2L;

    private static final Long DEFAULT_NUMBER_OF_TRANSACTION_LIMIT = 1L;
    private static final Long UPDATED_NUMBER_OF_TRANSACTION_LIMIT = 2L;

    @Autowired
    private PaymentRuleRepository paymentRuleRepository;

    @Autowired
    private PaymentRuleMapper paymentRuleMapper;

    @Autowired
    private PaymentRuleService paymentRuleService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.offer.repository.search test package.
     *
     * @see com.diviso.graeshoppe.offer.repository.search.PaymentRuleSearchRepositoryMockConfiguration
     */
    @Autowired
    private PaymentRuleSearchRepository mockPaymentRuleSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPaymentRuleMockMvc;

    private PaymentRule paymentRule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentRuleResource paymentRuleResource = new PaymentRuleResource(paymentRuleService);
        this.restPaymentRuleMockMvc = MockMvcBuilders.standaloneSetup(paymentRuleResource)
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
    public static PaymentRule createEntity(EntityManager em) {
        PaymentRule paymentRule = new PaymentRule()
            .paymentMode(DEFAULT_PAYMENT_MODE)
            .authorizedProvider(DEFAULT_AUTHORIZED_PROVIDER)
            .cashBackType(DEFAULT_CASH_BACK_TYPE)
            .cashBackValue(DEFAULT_CASH_BACK_VALUE)
            .numberOfTransactionLimit(DEFAULT_NUMBER_OF_TRANSACTION_LIMIT);
        return paymentRule;
    }

    @Before
    public void initTest() {
        paymentRule = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentRule() throws Exception {
        int databaseSizeBeforeCreate = paymentRuleRepository.findAll().size();

        // Create the PaymentRule
        PaymentRuleDTO paymentRuleDTO = paymentRuleMapper.toDto(paymentRule);
        restPaymentRuleMockMvc.perform(post("/api/payment-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentRuleDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentRule in the database
        List<PaymentRule> paymentRuleList = paymentRuleRepository.findAll();
        assertThat(paymentRuleList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentRule testPaymentRule = paymentRuleList.get(paymentRuleList.size() - 1);
        assertThat(testPaymentRule.getPaymentMode()).isEqualTo(DEFAULT_PAYMENT_MODE);
        assertThat(testPaymentRule.getAuthorizedProvider()).isEqualTo(DEFAULT_AUTHORIZED_PROVIDER);
        assertThat(testPaymentRule.getCashBackType()).isEqualTo(DEFAULT_CASH_BACK_TYPE);
        assertThat(testPaymentRule.getCashBackValue()).isEqualTo(DEFAULT_CASH_BACK_VALUE);
        assertThat(testPaymentRule.getNumberOfTransactionLimit()).isEqualTo(DEFAULT_NUMBER_OF_TRANSACTION_LIMIT);

        // Validate the PaymentRule in Elasticsearch
        verify(mockPaymentRuleSearchRepository, times(1)).save(testPaymentRule);
    }

    @Test
    @Transactional
    public void createPaymentRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentRuleRepository.findAll().size();

        // Create the PaymentRule with an existing ID
        paymentRule.setId(1L);
        PaymentRuleDTO paymentRuleDTO = paymentRuleMapper.toDto(paymentRule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentRuleMockMvc.perform(post("/api/payment-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentRuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentRule in the database
        List<PaymentRule> paymentRuleList = paymentRuleRepository.findAll();
        assertThat(paymentRuleList).hasSize(databaseSizeBeforeCreate);

        // Validate the PaymentRule in Elasticsearch
        verify(mockPaymentRuleSearchRepository, times(0)).save(paymentRule);
    }

    @Test
    @Transactional
    public void getAllPaymentRules() throws Exception {
        // Initialize the database
        paymentRuleRepository.saveAndFlush(paymentRule);

        // Get all the paymentRuleList
        restPaymentRuleMockMvc.perform(get("/api/payment-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentMode").value(hasItem(DEFAULT_PAYMENT_MODE.toString())))
            .andExpect(jsonPath("$.[*].authorizedProvider").value(hasItem(DEFAULT_AUTHORIZED_PROVIDER.toString())))
            .andExpect(jsonPath("$.[*].cashBackType").value(hasItem(DEFAULT_CASH_BACK_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].cashBackValue").value(hasItem(DEFAULT_CASH_BACK_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].numberOfTransactionLimit").value(hasItem(DEFAULT_NUMBER_OF_TRANSACTION_LIMIT.intValue())));
    }
    
    @Test
    @Transactional
    public void getPaymentRule() throws Exception {
        // Initialize the database
        paymentRuleRepository.saveAndFlush(paymentRule);

        // Get the paymentRule
        restPaymentRuleMockMvc.perform(get("/api/payment-rules/{id}", paymentRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentRule.getId().intValue()))
            .andExpect(jsonPath("$.paymentMode").value(DEFAULT_PAYMENT_MODE.toString()))
            .andExpect(jsonPath("$.authorizedProvider").value(DEFAULT_AUTHORIZED_PROVIDER.toString()))
            .andExpect(jsonPath("$.cashBackType").value(DEFAULT_CASH_BACK_TYPE.intValue()))
            .andExpect(jsonPath("$.cashBackValue").value(DEFAULT_CASH_BACK_VALUE.intValue()))
            .andExpect(jsonPath("$.numberOfTransactionLimit").value(DEFAULT_NUMBER_OF_TRANSACTION_LIMIT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentRule() throws Exception {
        // Get the paymentRule
        restPaymentRuleMockMvc.perform(get("/api/payment-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentRule() throws Exception {
        // Initialize the database
        paymentRuleRepository.saveAndFlush(paymentRule);

        int databaseSizeBeforeUpdate = paymentRuleRepository.findAll().size();

        // Update the paymentRule
        PaymentRule updatedPaymentRule = paymentRuleRepository.findById(paymentRule.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentRule are not directly saved in db
        em.detach(updatedPaymentRule);
        updatedPaymentRule
            .paymentMode(UPDATED_PAYMENT_MODE)
            .authorizedProvider(UPDATED_AUTHORIZED_PROVIDER)
            .cashBackType(UPDATED_CASH_BACK_TYPE)
            .cashBackValue(UPDATED_CASH_BACK_VALUE)
            .numberOfTransactionLimit(UPDATED_NUMBER_OF_TRANSACTION_LIMIT);
        PaymentRuleDTO paymentRuleDTO = paymentRuleMapper.toDto(updatedPaymentRule);

        restPaymentRuleMockMvc.perform(put("/api/payment-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentRuleDTO)))
            .andExpect(status().isOk());

        // Validate the PaymentRule in the database
        List<PaymentRule> paymentRuleList = paymentRuleRepository.findAll();
        assertThat(paymentRuleList).hasSize(databaseSizeBeforeUpdate);
        PaymentRule testPaymentRule = paymentRuleList.get(paymentRuleList.size() - 1);
        assertThat(testPaymentRule.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
        assertThat(testPaymentRule.getAuthorizedProvider()).isEqualTo(UPDATED_AUTHORIZED_PROVIDER);
        assertThat(testPaymentRule.getCashBackType()).isEqualTo(UPDATED_CASH_BACK_TYPE);
        assertThat(testPaymentRule.getCashBackValue()).isEqualTo(UPDATED_CASH_BACK_VALUE);
        assertThat(testPaymentRule.getNumberOfTransactionLimit()).isEqualTo(UPDATED_NUMBER_OF_TRANSACTION_LIMIT);

        // Validate the PaymentRule in Elasticsearch
        verify(mockPaymentRuleSearchRepository, times(1)).save(testPaymentRule);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentRule() throws Exception {
        int databaseSizeBeforeUpdate = paymentRuleRepository.findAll().size();

        // Create the PaymentRule
        PaymentRuleDTO paymentRuleDTO = paymentRuleMapper.toDto(paymentRule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentRuleMockMvc.perform(put("/api/payment-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentRuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentRule in the database
        List<PaymentRule> paymentRuleList = paymentRuleRepository.findAll();
        assertThat(paymentRuleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PaymentRule in Elasticsearch
        verify(mockPaymentRuleSearchRepository, times(0)).save(paymentRule);
    }

    @Test
    @Transactional
    public void deletePaymentRule() throws Exception {
        // Initialize the database
        paymentRuleRepository.saveAndFlush(paymentRule);

        int databaseSizeBeforeDelete = paymentRuleRepository.findAll().size();

        // Get the paymentRule
        restPaymentRuleMockMvc.perform(delete("/api/payment-rules/{id}", paymentRule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PaymentRule> paymentRuleList = paymentRuleRepository.findAll();
        assertThat(paymentRuleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PaymentRule in Elasticsearch
        verify(mockPaymentRuleSearchRepository, times(1)).deleteById(paymentRule.getId());
    }

    @Test
    @Transactional
    public void searchPaymentRule() throws Exception {
        // Initialize the database
        paymentRuleRepository.saveAndFlush(paymentRule);
        when(mockPaymentRuleSearchRepository.search(queryStringQuery("id:" + paymentRule.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paymentRule), PageRequest.of(0, 1), 1));
        // Search the paymentRule
        restPaymentRuleMockMvc.perform(get("/api/_search/payment-rules?query=id:" + paymentRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentMode").value(hasItem(DEFAULT_PAYMENT_MODE)))
            .andExpect(jsonPath("$.[*].authorizedProvider").value(hasItem(DEFAULT_AUTHORIZED_PROVIDER)))
            .andExpect(jsonPath("$.[*].cashBackType").value(hasItem(DEFAULT_CASH_BACK_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].cashBackValue").value(hasItem(DEFAULT_CASH_BACK_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].numberOfTransactionLimit").value(hasItem(DEFAULT_NUMBER_OF_TRANSACTION_LIMIT.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentRule.class);
        PaymentRule paymentRule1 = new PaymentRule();
        paymentRule1.setId(1L);
        PaymentRule paymentRule2 = new PaymentRule();
        paymentRule2.setId(paymentRule1.getId());
        assertThat(paymentRule1).isEqualTo(paymentRule2);
        paymentRule2.setId(2L);
        assertThat(paymentRule1).isNotEqualTo(paymentRule2);
        paymentRule1.setId(null);
        assertThat(paymentRule1).isNotEqualTo(paymentRule2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentRuleDTO.class);
        PaymentRuleDTO paymentRuleDTO1 = new PaymentRuleDTO();
        paymentRuleDTO1.setId(1L);
        PaymentRuleDTO paymentRuleDTO2 = new PaymentRuleDTO();
        assertThat(paymentRuleDTO1).isNotEqualTo(paymentRuleDTO2);
        paymentRuleDTO2.setId(paymentRuleDTO1.getId());
        assertThat(paymentRuleDTO1).isEqualTo(paymentRuleDTO2);
        paymentRuleDTO2.setId(2L);
        assertThat(paymentRuleDTO1).isNotEqualTo(paymentRuleDTO2);
        paymentRuleDTO1.setId(null);
        assertThat(paymentRuleDTO1).isNotEqualTo(paymentRuleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paymentRuleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paymentRuleMapper.fromId(null)).isNull();
    }
}
