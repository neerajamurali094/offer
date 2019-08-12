package com.diviso.graeshoppe.offer.web.rest;

import com.diviso.graeshoppe.offer.OfferApp;

import com.diviso.graeshoppe.offer.domain.DeductionValueType;
import com.diviso.graeshoppe.offer.repository.DeductionValueTypeRepository;
import com.diviso.graeshoppe.offer.repository.search.DeductionValueTypeSearchRepository;
import com.diviso.graeshoppe.offer.service.DeductionValueTypeService;
import com.diviso.graeshoppe.offer.service.dto.DeductionValueTypeDTO;
import com.diviso.graeshoppe.offer.service.mapper.DeductionValueTypeMapper;
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
 * Test class for the DeductionValueTypeResource REST controller.
 *
 * @see DeductionValueTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OfferApp.class)
public class DeductionValueTypeResourceIntTest {

    private static final String DEFAULT_DEDUCTION_VALUE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DEDUCTION_VALUE_TYPE = "BBBBBBBBBB";

    @Autowired
    private DeductionValueTypeRepository deductionValueTypeRepository;

    @Autowired
    private DeductionValueTypeMapper deductionValueTypeMapper;

    @Autowired
    private DeductionValueTypeService deductionValueTypeService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.offer.repository.search test package.
     *
     * @see com.diviso.graeshoppe.offer.repository.search.DeductionValueTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private DeductionValueTypeSearchRepository mockDeductionValueTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeductionValueTypeMockMvc;

    private DeductionValueType deductionValueType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeductionValueTypeResource deductionValueTypeResource = new DeductionValueTypeResource(deductionValueTypeService);
        this.restDeductionValueTypeMockMvc = MockMvcBuilders.standaloneSetup(deductionValueTypeResource)
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
    public static DeductionValueType createEntity(EntityManager em) {
        DeductionValueType deductionValueType = new DeductionValueType()
            .deductionValueType(DEFAULT_DEDUCTION_VALUE_TYPE);
        return deductionValueType;
    }

    @Before
    public void initTest() {
        deductionValueType = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeductionValueType() throws Exception {
        int databaseSizeBeforeCreate = deductionValueTypeRepository.findAll().size();

        // Create the DeductionValueType
        DeductionValueTypeDTO deductionValueTypeDTO = deductionValueTypeMapper.toDto(deductionValueType);
        restDeductionValueTypeMockMvc.perform(post("/api/deduction-value-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deductionValueTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the DeductionValueType in the database
        List<DeductionValueType> deductionValueTypeList = deductionValueTypeRepository.findAll();
        assertThat(deductionValueTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DeductionValueType testDeductionValueType = deductionValueTypeList.get(deductionValueTypeList.size() - 1);
        assertThat(testDeductionValueType.getDeductionValueType()).isEqualTo(DEFAULT_DEDUCTION_VALUE_TYPE);

        // Validate the DeductionValueType in Elasticsearch
        verify(mockDeductionValueTypeSearchRepository, times(1)).save(testDeductionValueType);
    }

    @Test
    @Transactional
    public void createDeductionValueTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deductionValueTypeRepository.findAll().size();

        // Create the DeductionValueType with an existing ID
        deductionValueType.setId(1L);
        DeductionValueTypeDTO deductionValueTypeDTO = deductionValueTypeMapper.toDto(deductionValueType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeductionValueTypeMockMvc.perform(post("/api/deduction-value-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deductionValueTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeductionValueType in the database
        List<DeductionValueType> deductionValueTypeList = deductionValueTypeRepository.findAll();
        assertThat(deductionValueTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the DeductionValueType in Elasticsearch
        verify(mockDeductionValueTypeSearchRepository, times(0)).save(deductionValueType);
    }

    @Test
    @Transactional
    public void getAllDeductionValueTypes() throws Exception {
        // Initialize the database
        deductionValueTypeRepository.saveAndFlush(deductionValueType);

        // Get all the deductionValueTypeList
        restDeductionValueTypeMockMvc.perform(get("/api/deduction-value-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deductionValueType.getId().intValue())))
            .andExpect(jsonPath("$.[*].deductionValueType").value(hasItem(DEFAULT_DEDUCTION_VALUE_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getDeductionValueType() throws Exception {
        // Initialize the database
        deductionValueTypeRepository.saveAndFlush(deductionValueType);

        // Get the deductionValueType
        restDeductionValueTypeMockMvc.perform(get("/api/deduction-value-types/{id}", deductionValueType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deductionValueType.getId().intValue()))
            .andExpect(jsonPath("$.deductionValueType").value(DEFAULT_DEDUCTION_VALUE_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeductionValueType() throws Exception {
        // Get the deductionValueType
        restDeductionValueTypeMockMvc.perform(get("/api/deduction-value-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeductionValueType() throws Exception {
        // Initialize the database
        deductionValueTypeRepository.saveAndFlush(deductionValueType);

        int databaseSizeBeforeUpdate = deductionValueTypeRepository.findAll().size();

        // Update the deductionValueType
        DeductionValueType updatedDeductionValueType = deductionValueTypeRepository.findById(deductionValueType.getId()).get();
        // Disconnect from session so that the updates on updatedDeductionValueType are not directly saved in db
        em.detach(updatedDeductionValueType);
        updatedDeductionValueType
            .deductionValueType(UPDATED_DEDUCTION_VALUE_TYPE);
        DeductionValueTypeDTO deductionValueTypeDTO = deductionValueTypeMapper.toDto(updatedDeductionValueType);

        restDeductionValueTypeMockMvc.perform(put("/api/deduction-value-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deductionValueTypeDTO)))
            .andExpect(status().isOk());

        // Validate the DeductionValueType in the database
        List<DeductionValueType> deductionValueTypeList = deductionValueTypeRepository.findAll();
        assertThat(deductionValueTypeList).hasSize(databaseSizeBeforeUpdate);
        DeductionValueType testDeductionValueType = deductionValueTypeList.get(deductionValueTypeList.size() - 1);
        assertThat(testDeductionValueType.getDeductionValueType()).isEqualTo(UPDATED_DEDUCTION_VALUE_TYPE);

        // Validate the DeductionValueType in Elasticsearch
        verify(mockDeductionValueTypeSearchRepository, times(1)).save(testDeductionValueType);
    }

    @Test
    @Transactional
    public void updateNonExistingDeductionValueType() throws Exception {
        int databaseSizeBeforeUpdate = deductionValueTypeRepository.findAll().size();

        // Create the DeductionValueType
        DeductionValueTypeDTO deductionValueTypeDTO = deductionValueTypeMapper.toDto(deductionValueType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeductionValueTypeMockMvc.perform(put("/api/deduction-value-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deductionValueTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeductionValueType in the database
        List<DeductionValueType> deductionValueTypeList = deductionValueTypeRepository.findAll();
        assertThat(deductionValueTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DeductionValueType in Elasticsearch
        verify(mockDeductionValueTypeSearchRepository, times(0)).save(deductionValueType);
    }

    @Test
    @Transactional
    public void deleteDeductionValueType() throws Exception {
        // Initialize the database
        deductionValueTypeRepository.saveAndFlush(deductionValueType);

        int databaseSizeBeforeDelete = deductionValueTypeRepository.findAll().size();

        // Get the deductionValueType
        restDeductionValueTypeMockMvc.perform(delete("/api/deduction-value-types/{id}", deductionValueType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DeductionValueType> deductionValueTypeList = deductionValueTypeRepository.findAll();
        assertThat(deductionValueTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DeductionValueType in Elasticsearch
        verify(mockDeductionValueTypeSearchRepository, times(1)).deleteById(deductionValueType.getId());
    }

    @Test
    @Transactional
    public void searchDeductionValueType() throws Exception {
        // Initialize the database
        deductionValueTypeRepository.saveAndFlush(deductionValueType);
        when(mockDeductionValueTypeSearchRepository.search(queryStringQuery("id:" + deductionValueType.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(deductionValueType), PageRequest.of(0, 1), 1));
        // Search the deductionValueType
        restDeductionValueTypeMockMvc.perform(get("/api/_search/deduction-value-types?query=id:" + deductionValueType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deductionValueType.getId().intValue())))
            .andExpect(jsonPath("$.[*].deductionValueType").value(hasItem(DEFAULT_DEDUCTION_VALUE_TYPE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeductionValueType.class);
        DeductionValueType deductionValueType1 = new DeductionValueType();
        deductionValueType1.setId(1L);
        DeductionValueType deductionValueType2 = new DeductionValueType();
        deductionValueType2.setId(deductionValueType1.getId());
        assertThat(deductionValueType1).isEqualTo(deductionValueType2);
        deductionValueType2.setId(2L);
        assertThat(deductionValueType1).isNotEqualTo(deductionValueType2);
        deductionValueType1.setId(null);
        assertThat(deductionValueType1).isNotEqualTo(deductionValueType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeductionValueTypeDTO.class);
        DeductionValueTypeDTO deductionValueTypeDTO1 = new DeductionValueTypeDTO();
        deductionValueTypeDTO1.setId(1L);
        DeductionValueTypeDTO deductionValueTypeDTO2 = new DeductionValueTypeDTO();
        assertThat(deductionValueTypeDTO1).isNotEqualTo(deductionValueTypeDTO2);
        deductionValueTypeDTO2.setId(deductionValueTypeDTO1.getId());
        assertThat(deductionValueTypeDTO1).isEqualTo(deductionValueTypeDTO2);
        deductionValueTypeDTO2.setId(2L);
        assertThat(deductionValueTypeDTO1).isNotEqualTo(deductionValueTypeDTO2);
        deductionValueTypeDTO1.setId(null);
        assertThat(deductionValueTypeDTO1).isNotEqualTo(deductionValueTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(deductionValueTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(deductionValueTypeMapper.fromId(null)).isNull();
    }
}
