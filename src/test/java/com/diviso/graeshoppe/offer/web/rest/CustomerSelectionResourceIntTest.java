package com.diviso.graeshoppe.offer.web.rest;

import com.diviso.graeshoppe.offer.OfferApp;

import com.diviso.graeshoppe.offer.domain.CustomerSelection;
import com.diviso.graeshoppe.offer.repository.CustomerSelectionRepository;
import com.diviso.graeshoppe.offer.repository.search.CustomerSelectionSearchRepository;
import com.diviso.graeshoppe.offer.service.CustomerSelectionService;
import com.diviso.graeshoppe.offer.service.dto.CustomerSelectionDTO;
import com.diviso.graeshoppe.offer.service.mapper.CustomerSelectionMapper;
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
 * Test class for the CustomerSelectionResource REST controller.
 *
 * @see CustomerSelectionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OfferApp.class)
public class CustomerSelectionResourceIntTest {

    private static final String DEFAULT_CUSTOMER_SELECTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_SELECTION_TYPE = "BBBBBBBBBB";

    @Autowired
    private CustomerSelectionRepository customerSelectionRepository;

    @Autowired
    private CustomerSelectionMapper customerSelectionMapper;

    @Autowired
    private CustomerSelectionService customerSelectionService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.offer.repository.search test package.
     *
     * @see com.diviso.graeshoppe.offer.repository.search.CustomerSelectionSearchRepositoryMockConfiguration
     */
    @Autowired
    private CustomerSelectionSearchRepository mockCustomerSelectionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerSelectionMockMvc;

    private CustomerSelection customerSelection;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerSelectionResource customerSelectionResource = new CustomerSelectionResource(customerSelectionService);
        this.restCustomerSelectionMockMvc = MockMvcBuilders.standaloneSetup(customerSelectionResource)
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
    public static CustomerSelection createEntity(EntityManager em) {
        CustomerSelection customerSelection = new CustomerSelection()
            .customerSelectionType(DEFAULT_CUSTOMER_SELECTION_TYPE);
        return customerSelection;
    }

    @Before
    public void initTest() {
        customerSelection = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerSelection() throws Exception {
        int databaseSizeBeforeCreate = customerSelectionRepository.findAll().size();

        // Create the CustomerSelection
        CustomerSelectionDTO customerSelectionDTO = customerSelectionMapper.toDto(customerSelection);
        restCustomerSelectionMockMvc.perform(post("/api/customer-selections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerSelectionDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerSelection in the database
        List<CustomerSelection> customerSelectionList = customerSelectionRepository.findAll();
        assertThat(customerSelectionList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerSelection testCustomerSelection = customerSelectionList.get(customerSelectionList.size() - 1);
        assertThat(testCustomerSelection.getCustomerSelectionType()).isEqualTo(DEFAULT_CUSTOMER_SELECTION_TYPE);

        // Validate the CustomerSelection in Elasticsearch
        verify(mockCustomerSelectionSearchRepository, times(1)).save(testCustomerSelection);
    }

    @Test
    @Transactional
    public void createCustomerSelectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerSelectionRepository.findAll().size();

        // Create the CustomerSelection with an existing ID
        customerSelection.setId(1L);
        CustomerSelectionDTO customerSelectionDTO = customerSelectionMapper.toDto(customerSelection);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerSelectionMockMvc.perform(post("/api/customer-selections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerSelectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerSelection in the database
        List<CustomerSelection> customerSelectionList = customerSelectionRepository.findAll();
        assertThat(customerSelectionList).hasSize(databaseSizeBeforeCreate);

        // Validate the CustomerSelection in Elasticsearch
        verify(mockCustomerSelectionSearchRepository, times(0)).save(customerSelection);
    }

    @Test
    @Transactional
    public void getAllCustomerSelections() throws Exception {
        // Initialize the database
        customerSelectionRepository.saveAndFlush(customerSelection);

        // Get all the customerSelectionList
        restCustomerSelectionMockMvc.perform(get("/api/customer-selections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerSelection.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerSelectionType").value(hasItem(DEFAULT_CUSTOMER_SELECTION_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getCustomerSelection() throws Exception {
        // Initialize the database
        customerSelectionRepository.saveAndFlush(customerSelection);

        // Get the customerSelection
        restCustomerSelectionMockMvc.perform(get("/api/customer-selections/{id}", customerSelection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerSelection.getId().intValue()))
            .andExpect(jsonPath("$.customerSelectionType").value(DEFAULT_CUSTOMER_SELECTION_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerSelection() throws Exception {
        // Get the customerSelection
        restCustomerSelectionMockMvc.perform(get("/api/customer-selections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerSelection() throws Exception {
        // Initialize the database
        customerSelectionRepository.saveAndFlush(customerSelection);

        int databaseSizeBeforeUpdate = customerSelectionRepository.findAll().size();

        // Update the customerSelection
        CustomerSelection updatedCustomerSelection = customerSelectionRepository.findById(customerSelection.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerSelection are not directly saved in db
        em.detach(updatedCustomerSelection);
        updatedCustomerSelection
            .customerSelectionType(UPDATED_CUSTOMER_SELECTION_TYPE);
        CustomerSelectionDTO customerSelectionDTO = customerSelectionMapper.toDto(updatedCustomerSelection);

        restCustomerSelectionMockMvc.perform(put("/api/customer-selections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerSelectionDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerSelection in the database
        List<CustomerSelection> customerSelectionList = customerSelectionRepository.findAll();
        assertThat(customerSelectionList).hasSize(databaseSizeBeforeUpdate);
        CustomerSelection testCustomerSelection = customerSelectionList.get(customerSelectionList.size() - 1);
        assertThat(testCustomerSelection.getCustomerSelectionType()).isEqualTo(UPDATED_CUSTOMER_SELECTION_TYPE);

        // Validate the CustomerSelection in Elasticsearch
        verify(mockCustomerSelectionSearchRepository, times(1)).save(testCustomerSelection);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerSelection() throws Exception {
        int databaseSizeBeforeUpdate = customerSelectionRepository.findAll().size();

        // Create the CustomerSelection
        CustomerSelectionDTO customerSelectionDTO = customerSelectionMapper.toDto(customerSelection);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerSelectionMockMvc.perform(put("/api/customer-selections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerSelectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerSelection in the database
        List<CustomerSelection> customerSelectionList = customerSelectionRepository.findAll();
        assertThat(customerSelectionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CustomerSelection in Elasticsearch
        verify(mockCustomerSelectionSearchRepository, times(0)).save(customerSelection);
    }

    @Test
    @Transactional
    public void deleteCustomerSelection() throws Exception {
        // Initialize the database
        customerSelectionRepository.saveAndFlush(customerSelection);

        int databaseSizeBeforeDelete = customerSelectionRepository.findAll().size();

        // Get the customerSelection
        restCustomerSelectionMockMvc.perform(delete("/api/customer-selections/{id}", customerSelection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerSelection> customerSelectionList = customerSelectionRepository.findAll();
        assertThat(customerSelectionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CustomerSelection in Elasticsearch
        verify(mockCustomerSelectionSearchRepository, times(1)).deleteById(customerSelection.getId());
    }

    @Test
    @Transactional
    public void searchCustomerSelection() throws Exception {
        // Initialize the database
        customerSelectionRepository.saveAndFlush(customerSelection);
        when(mockCustomerSelectionSearchRepository.search(queryStringQuery("id:" + customerSelection.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(customerSelection), PageRequest.of(0, 1), 1));
        // Search the customerSelection
        restCustomerSelectionMockMvc.perform(get("/api/_search/customer-selections?query=id:" + customerSelection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerSelection.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerSelectionType").value(hasItem(DEFAULT_CUSTOMER_SELECTION_TYPE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerSelection.class);
        CustomerSelection customerSelection1 = new CustomerSelection();
        customerSelection1.setId(1L);
        CustomerSelection customerSelection2 = new CustomerSelection();
        customerSelection2.setId(customerSelection1.getId());
        assertThat(customerSelection1).isEqualTo(customerSelection2);
        customerSelection2.setId(2L);
        assertThat(customerSelection1).isNotEqualTo(customerSelection2);
        customerSelection1.setId(null);
        assertThat(customerSelection1).isNotEqualTo(customerSelection2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerSelectionDTO.class);
        CustomerSelectionDTO customerSelectionDTO1 = new CustomerSelectionDTO();
        customerSelectionDTO1.setId(1L);
        CustomerSelectionDTO customerSelectionDTO2 = new CustomerSelectionDTO();
        assertThat(customerSelectionDTO1).isNotEqualTo(customerSelectionDTO2);
        customerSelectionDTO2.setId(customerSelectionDTO1.getId());
        assertThat(customerSelectionDTO1).isEqualTo(customerSelectionDTO2);
        customerSelectionDTO2.setId(2L);
        assertThat(customerSelectionDTO1).isNotEqualTo(customerSelectionDTO2);
        customerSelectionDTO1.setId(null);
        assertThat(customerSelectionDTO1).isNotEqualTo(customerSelectionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(customerSelectionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(customerSelectionMapper.fromId(null)).isNull();
    }
}
