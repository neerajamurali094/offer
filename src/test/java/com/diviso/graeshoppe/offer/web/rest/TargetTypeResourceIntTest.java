package com.diviso.graeshoppe.offer.web.rest;

import com.diviso.graeshoppe.offer.OfferApp;

import com.diviso.graeshoppe.offer.domain.TargetType;
import com.diviso.graeshoppe.offer.repository.TargetTypeRepository;
import com.diviso.graeshoppe.offer.repository.search.TargetTypeSearchRepository;
import com.diviso.graeshoppe.offer.service.TargetTypeService;
import com.diviso.graeshoppe.offer.service.dto.TargetTypeDTO;
import com.diviso.graeshoppe.offer.service.mapper.TargetTypeMapper;
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
 * Test class for the TargetTypeResource REST controller.
 *
 * @see TargetTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OfferApp.class)
public class TargetTypeResourceIntTest {

    private static final String DEFAULT_TARGET_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_TYPE = "BBBBBBBBBB";

    @Autowired
    private TargetTypeRepository targetTypeRepository;

    @Autowired
    private TargetTypeMapper targetTypeMapper;

    @Autowired
    private TargetTypeService targetTypeService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.offer.repository.search test package.
     *
     * @see com.diviso.graeshoppe.offer.repository.search.TargetTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private TargetTypeSearchRepository mockTargetTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTargetTypeMockMvc;

    private TargetType targetType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TargetTypeResource targetTypeResource = new TargetTypeResource(targetTypeService);
        this.restTargetTypeMockMvc = MockMvcBuilders.standaloneSetup(targetTypeResource)
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
    public static TargetType createEntity(EntityManager em) {
        TargetType targetType = new TargetType()
            .targetType(DEFAULT_TARGET_TYPE);
        return targetType;
    }

    @Before
    public void initTest() {
        targetType = createEntity(em);
    }

    @Test
    @Transactional
    public void createTargetType() throws Exception {
        int databaseSizeBeforeCreate = targetTypeRepository.findAll().size();

        // Create the TargetType
        TargetTypeDTO targetTypeDTO = targetTypeMapper.toDto(targetType);
        restTargetTypeMockMvc.perform(post("/api/target-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(targetTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the TargetType in the database
        List<TargetType> targetTypeList = targetTypeRepository.findAll();
        assertThat(targetTypeList).hasSize(databaseSizeBeforeCreate + 1);
        TargetType testTargetType = targetTypeList.get(targetTypeList.size() - 1);
        assertThat(testTargetType.getTargetType()).isEqualTo(DEFAULT_TARGET_TYPE);

        // Validate the TargetType in Elasticsearch
        verify(mockTargetTypeSearchRepository, times(1)).save(testTargetType);
    }

    @Test
    @Transactional
    public void createTargetTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = targetTypeRepository.findAll().size();

        // Create the TargetType with an existing ID
        targetType.setId(1L);
        TargetTypeDTO targetTypeDTO = targetTypeMapper.toDto(targetType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTargetTypeMockMvc.perform(post("/api/target-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(targetTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TargetType in the database
        List<TargetType> targetTypeList = targetTypeRepository.findAll();
        assertThat(targetTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the TargetType in Elasticsearch
        verify(mockTargetTypeSearchRepository, times(0)).save(targetType);
    }

    @Test
    @Transactional
    public void getAllTargetTypes() throws Exception {
        // Initialize the database
        targetTypeRepository.saveAndFlush(targetType);

        // Get all the targetTypeList
        restTargetTypeMockMvc.perform(get("/api/target-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(targetType.getId().intValue())))
            .andExpect(jsonPath("$.[*].targetType").value(hasItem(DEFAULT_TARGET_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getTargetType() throws Exception {
        // Initialize the database
        targetTypeRepository.saveAndFlush(targetType);

        // Get the targetType
        restTargetTypeMockMvc.perform(get("/api/target-types/{id}", targetType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(targetType.getId().intValue()))
            .andExpect(jsonPath("$.targetType").value(DEFAULT_TARGET_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTargetType() throws Exception {
        // Get the targetType
        restTargetTypeMockMvc.perform(get("/api/target-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTargetType() throws Exception {
        // Initialize the database
        targetTypeRepository.saveAndFlush(targetType);

        int databaseSizeBeforeUpdate = targetTypeRepository.findAll().size();

        // Update the targetType
        TargetType updatedTargetType = targetTypeRepository.findById(targetType.getId()).get();
        // Disconnect from session so that the updates on updatedTargetType are not directly saved in db
        em.detach(updatedTargetType);
        updatedTargetType
            .targetType(UPDATED_TARGET_TYPE);
        TargetTypeDTO targetTypeDTO = targetTypeMapper.toDto(updatedTargetType);

        restTargetTypeMockMvc.perform(put("/api/target-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(targetTypeDTO)))
            .andExpect(status().isOk());

        // Validate the TargetType in the database
        List<TargetType> targetTypeList = targetTypeRepository.findAll();
        assertThat(targetTypeList).hasSize(databaseSizeBeforeUpdate);
        TargetType testTargetType = targetTypeList.get(targetTypeList.size() - 1);
        assertThat(testTargetType.getTargetType()).isEqualTo(UPDATED_TARGET_TYPE);

        // Validate the TargetType in Elasticsearch
        verify(mockTargetTypeSearchRepository, times(1)).save(testTargetType);
    }

    @Test
    @Transactional
    public void updateNonExistingTargetType() throws Exception {
        int databaseSizeBeforeUpdate = targetTypeRepository.findAll().size();

        // Create the TargetType
        TargetTypeDTO targetTypeDTO = targetTypeMapper.toDto(targetType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTargetTypeMockMvc.perform(put("/api/target-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(targetTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TargetType in the database
        List<TargetType> targetTypeList = targetTypeRepository.findAll();
        assertThat(targetTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TargetType in Elasticsearch
        verify(mockTargetTypeSearchRepository, times(0)).save(targetType);
    }

    @Test
    @Transactional
    public void deleteTargetType() throws Exception {
        // Initialize the database
        targetTypeRepository.saveAndFlush(targetType);

        int databaseSizeBeforeDelete = targetTypeRepository.findAll().size();

        // Get the targetType
        restTargetTypeMockMvc.perform(delete("/api/target-types/{id}", targetType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TargetType> targetTypeList = targetTypeRepository.findAll();
        assertThat(targetTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TargetType in Elasticsearch
        verify(mockTargetTypeSearchRepository, times(1)).deleteById(targetType.getId());
    }

    @Test
    @Transactional
    public void searchTargetType() throws Exception {
        // Initialize the database
        targetTypeRepository.saveAndFlush(targetType);
        when(mockTargetTypeSearchRepository.search(queryStringQuery("id:" + targetType.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(targetType), PageRequest.of(0, 1), 1));
        // Search the targetType
        restTargetTypeMockMvc.perform(get("/api/_search/target-types?query=id:" + targetType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(targetType.getId().intValue())))
            .andExpect(jsonPath("$.[*].targetType").value(hasItem(DEFAULT_TARGET_TYPE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TargetType.class);
        TargetType targetType1 = new TargetType();
        targetType1.setId(1L);
        TargetType targetType2 = new TargetType();
        targetType2.setId(targetType1.getId());
        assertThat(targetType1).isEqualTo(targetType2);
        targetType2.setId(2L);
        assertThat(targetType1).isNotEqualTo(targetType2);
        targetType1.setId(null);
        assertThat(targetType1).isNotEqualTo(targetType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TargetTypeDTO.class);
        TargetTypeDTO targetTypeDTO1 = new TargetTypeDTO();
        targetTypeDTO1.setId(1L);
        TargetTypeDTO targetTypeDTO2 = new TargetTypeDTO();
        assertThat(targetTypeDTO1).isNotEqualTo(targetTypeDTO2);
        targetTypeDTO2.setId(targetTypeDTO1.getId());
        assertThat(targetTypeDTO1).isEqualTo(targetTypeDTO2);
        targetTypeDTO2.setId(2L);
        assertThat(targetTypeDTO1).isNotEqualTo(targetTypeDTO2);
        targetTypeDTO1.setId(null);
        assertThat(targetTypeDTO1).isNotEqualTo(targetTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(targetTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(targetTypeMapper.fromId(null)).isNull();
    }
}
