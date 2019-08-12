package com.diviso.graeshoppe.offer.web.rest;

import com.diviso.graeshoppe.offer.OfferApp;

import com.diviso.graeshoppe.offer.domain.AllocationMethod;
import com.diviso.graeshoppe.offer.repository.AllocationMethodRepository;
import com.diviso.graeshoppe.offer.repository.search.AllocationMethodSearchRepository;
import com.diviso.graeshoppe.offer.service.AllocationMethodService;
import com.diviso.graeshoppe.offer.service.dto.AllocationMethodDTO;
import com.diviso.graeshoppe.offer.service.mapper.AllocationMethodMapper;
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
 * Test class for the AllocationMethodResource REST controller.
 *
 * @see AllocationMethodResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OfferApp.class)
public class AllocationMethodResourceIntTest {

    private static final String DEFAULT_ALLOCATION_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_ALLOCATION_METHOD = "BBBBBBBBBB";

    @Autowired
    private AllocationMethodRepository allocationMethodRepository;

    @Autowired
    private AllocationMethodMapper allocationMethodMapper;

    @Autowired
    private AllocationMethodService allocationMethodService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.offer.repository.search test package.
     *
     * @see com.diviso.graeshoppe.offer.repository.search.AllocationMethodSearchRepositoryMockConfiguration
     */
    @Autowired
    private AllocationMethodSearchRepository mockAllocationMethodSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAllocationMethodMockMvc;

    private AllocationMethod allocationMethod;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AllocationMethodResource allocationMethodResource = new AllocationMethodResource(allocationMethodService);
        this.restAllocationMethodMockMvc = MockMvcBuilders.standaloneSetup(allocationMethodResource)
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
    public static AllocationMethod createEntity(EntityManager em) {
        AllocationMethod allocationMethod = new AllocationMethod()
            .allocationMethod(DEFAULT_ALLOCATION_METHOD);
        return allocationMethod;
    }

    @Before
    public void initTest() {
        allocationMethod = createEntity(em);
    }

    @Test
    @Transactional
    public void createAllocationMethod() throws Exception {
        int databaseSizeBeforeCreate = allocationMethodRepository.findAll().size();

        // Create the AllocationMethod
        AllocationMethodDTO allocationMethodDTO = allocationMethodMapper.toDto(allocationMethod);
        restAllocationMethodMockMvc.perform(post("/api/allocation-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allocationMethodDTO)))
            .andExpect(status().isCreated());

        // Validate the AllocationMethod in the database
        List<AllocationMethod> allocationMethodList = allocationMethodRepository.findAll();
        assertThat(allocationMethodList).hasSize(databaseSizeBeforeCreate + 1);
        AllocationMethod testAllocationMethod = allocationMethodList.get(allocationMethodList.size() - 1);
        assertThat(testAllocationMethod.getAllocationMethod()).isEqualTo(DEFAULT_ALLOCATION_METHOD);

        // Validate the AllocationMethod in Elasticsearch
        verify(mockAllocationMethodSearchRepository, times(1)).save(testAllocationMethod);
    }

    @Test
    @Transactional
    public void createAllocationMethodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = allocationMethodRepository.findAll().size();

        // Create the AllocationMethod with an existing ID
        allocationMethod.setId(1L);
        AllocationMethodDTO allocationMethodDTO = allocationMethodMapper.toDto(allocationMethod);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAllocationMethodMockMvc.perform(post("/api/allocation-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allocationMethodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AllocationMethod in the database
        List<AllocationMethod> allocationMethodList = allocationMethodRepository.findAll();
        assertThat(allocationMethodList).hasSize(databaseSizeBeforeCreate);

        // Validate the AllocationMethod in Elasticsearch
        verify(mockAllocationMethodSearchRepository, times(0)).save(allocationMethod);
    }

    @Test
    @Transactional
    public void getAllAllocationMethods() throws Exception {
        // Initialize the database
        allocationMethodRepository.saveAndFlush(allocationMethod);

        // Get all the allocationMethodList
        restAllocationMethodMockMvc.perform(get("/api/allocation-methods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allocationMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].allocationMethod").value(hasItem(DEFAULT_ALLOCATION_METHOD.toString())));
    }
    
    @Test
    @Transactional
    public void getAllocationMethod() throws Exception {
        // Initialize the database
        allocationMethodRepository.saveAndFlush(allocationMethod);

        // Get the allocationMethod
        restAllocationMethodMockMvc.perform(get("/api/allocation-methods/{id}", allocationMethod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(allocationMethod.getId().intValue()))
            .andExpect(jsonPath("$.allocationMethod").value(DEFAULT_ALLOCATION_METHOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAllocationMethod() throws Exception {
        // Get the allocationMethod
        restAllocationMethodMockMvc.perform(get("/api/allocation-methods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAllocationMethod() throws Exception {
        // Initialize the database
        allocationMethodRepository.saveAndFlush(allocationMethod);

        int databaseSizeBeforeUpdate = allocationMethodRepository.findAll().size();

        // Update the allocationMethod
        AllocationMethod updatedAllocationMethod = allocationMethodRepository.findById(allocationMethod.getId()).get();
        // Disconnect from session so that the updates on updatedAllocationMethod are not directly saved in db
        em.detach(updatedAllocationMethod);
        updatedAllocationMethod
            .allocationMethod(UPDATED_ALLOCATION_METHOD);
        AllocationMethodDTO allocationMethodDTO = allocationMethodMapper.toDto(updatedAllocationMethod);

        restAllocationMethodMockMvc.perform(put("/api/allocation-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allocationMethodDTO)))
            .andExpect(status().isOk());

        // Validate the AllocationMethod in the database
        List<AllocationMethod> allocationMethodList = allocationMethodRepository.findAll();
        assertThat(allocationMethodList).hasSize(databaseSizeBeforeUpdate);
        AllocationMethod testAllocationMethod = allocationMethodList.get(allocationMethodList.size() - 1);
        assertThat(testAllocationMethod.getAllocationMethod()).isEqualTo(UPDATED_ALLOCATION_METHOD);

        // Validate the AllocationMethod in Elasticsearch
        verify(mockAllocationMethodSearchRepository, times(1)).save(testAllocationMethod);
    }

    @Test
    @Transactional
    public void updateNonExistingAllocationMethod() throws Exception {
        int databaseSizeBeforeUpdate = allocationMethodRepository.findAll().size();

        // Create the AllocationMethod
        AllocationMethodDTO allocationMethodDTO = allocationMethodMapper.toDto(allocationMethod);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAllocationMethodMockMvc.perform(put("/api/allocation-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allocationMethodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AllocationMethod in the database
        List<AllocationMethod> allocationMethodList = allocationMethodRepository.findAll();
        assertThat(allocationMethodList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AllocationMethod in Elasticsearch
        verify(mockAllocationMethodSearchRepository, times(0)).save(allocationMethod);
    }

    @Test
    @Transactional
    public void deleteAllocationMethod() throws Exception {
        // Initialize the database
        allocationMethodRepository.saveAndFlush(allocationMethod);

        int databaseSizeBeforeDelete = allocationMethodRepository.findAll().size();

        // Get the allocationMethod
        restAllocationMethodMockMvc.perform(delete("/api/allocation-methods/{id}", allocationMethod.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AllocationMethod> allocationMethodList = allocationMethodRepository.findAll();
        assertThat(allocationMethodList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AllocationMethod in Elasticsearch
        verify(mockAllocationMethodSearchRepository, times(1)).deleteById(allocationMethod.getId());
    }

    @Test
    @Transactional
    public void searchAllocationMethod() throws Exception {
        // Initialize the database
        allocationMethodRepository.saveAndFlush(allocationMethod);
        when(mockAllocationMethodSearchRepository.search(queryStringQuery("id:" + allocationMethod.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(allocationMethod), PageRequest.of(0, 1), 1));
        // Search the allocationMethod
        restAllocationMethodMockMvc.perform(get("/api/_search/allocation-methods?query=id:" + allocationMethod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allocationMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].allocationMethod").value(hasItem(DEFAULT_ALLOCATION_METHOD)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AllocationMethod.class);
        AllocationMethod allocationMethod1 = new AllocationMethod();
        allocationMethod1.setId(1L);
        AllocationMethod allocationMethod2 = new AllocationMethod();
        allocationMethod2.setId(allocationMethod1.getId());
        assertThat(allocationMethod1).isEqualTo(allocationMethod2);
        allocationMethod2.setId(2L);
        assertThat(allocationMethod1).isNotEqualTo(allocationMethod2);
        allocationMethod1.setId(null);
        assertThat(allocationMethod1).isNotEqualTo(allocationMethod2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AllocationMethodDTO.class);
        AllocationMethodDTO allocationMethodDTO1 = new AllocationMethodDTO();
        allocationMethodDTO1.setId(1L);
        AllocationMethodDTO allocationMethodDTO2 = new AllocationMethodDTO();
        assertThat(allocationMethodDTO1).isNotEqualTo(allocationMethodDTO2);
        allocationMethodDTO2.setId(allocationMethodDTO1.getId());
        assertThat(allocationMethodDTO1).isEqualTo(allocationMethodDTO2);
        allocationMethodDTO2.setId(2L);
        assertThat(allocationMethodDTO1).isNotEqualTo(allocationMethodDTO2);
        allocationMethodDTO1.setId(null);
        assertThat(allocationMethodDTO1).isNotEqualTo(allocationMethodDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(allocationMethodMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(allocationMethodMapper.fromId(null)).isNull();
    }
}
