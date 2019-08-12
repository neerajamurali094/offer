package com.diviso.graeshoppe.offer.web.rest;

import com.diviso.graeshoppe.offer.OfferApp;

import com.diviso.graeshoppe.offer.domain.OfferTargetCategory;
import com.diviso.graeshoppe.offer.repository.OfferTargetCategoryRepository;
import com.diviso.graeshoppe.offer.repository.search.OfferTargetCategorySearchRepository;
import com.diviso.graeshoppe.offer.service.OfferTargetCategoryService;
import com.diviso.graeshoppe.offer.service.dto.OfferTargetCategoryDTO;
import com.diviso.graeshoppe.offer.service.mapper.OfferTargetCategoryMapper;
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
 * Test class for the OfferTargetCategoryResource REST controller.
 *
 * @see OfferTargetCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OfferApp.class)
public class OfferTargetCategoryResourceIntTest {

    private static final Long DEFAULT_TARGET_CATEGORY_ID = 1L;
    private static final Long UPDATED_TARGET_CATEGORY_ID = 2L;

    @Autowired
    private OfferTargetCategoryRepository offerTargetCategoryRepository;

    @Autowired
    private OfferTargetCategoryMapper offerTargetCategoryMapper;

    @Autowired
    private OfferTargetCategoryService offerTargetCategoryService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.offer.repository.search test package.
     *
     * @see com.diviso.graeshoppe.offer.repository.search.OfferTargetCategorySearchRepositoryMockConfiguration
     */
    @Autowired
    private OfferTargetCategorySearchRepository mockOfferTargetCategorySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOfferTargetCategoryMockMvc;

    private OfferTargetCategory offerTargetCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OfferTargetCategoryResource offerTargetCategoryResource = new OfferTargetCategoryResource(offerTargetCategoryService);
        this.restOfferTargetCategoryMockMvc = MockMvcBuilders.standaloneSetup(offerTargetCategoryResource)
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
    public static OfferTargetCategory createEntity(EntityManager em) {
        OfferTargetCategory offerTargetCategory = new OfferTargetCategory()
            .targetCategoryId(DEFAULT_TARGET_CATEGORY_ID);
        return offerTargetCategory;
    }

    @Before
    public void initTest() {
        offerTargetCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createOfferTargetCategory() throws Exception {
        int databaseSizeBeforeCreate = offerTargetCategoryRepository.findAll().size();

        // Create the OfferTargetCategory
        OfferTargetCategoryDTO offerTargetCategoryDTO = offerTargetCategoryMapper.toDto(offerTargetCategory);
        restOfferTargetCategoryMockMvc.perform(post("/api/offer-target-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offerTargetCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the OfferTargetCategory in the database
        List<OfferTargetCategory> offerTargetCategoryList = offerTargetCategoryRepository.findAll();
        assertThat(offerTargetCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        OfferTargetCategory testOfferTargetCategory = offerTargetCategoryList.get(offerTargetCategoryList.size() - 1);
        assertThat(testOfferTargetCategory.getTargetCategoryId()).isEqualTo(DEFAULT_TARGET_CATEGORY_ID);

        // Validate the OfferTargetCategory in Elasticsearch
        verify(mockOfferTargetCategorySearchRepository, times(1)).save(testOfferTargetCategory);
    }

    @Test
    @Transactional
    public void createOfferTargetCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offerTargetCategoryRepository.findAll().size();

        // Create the OfferTargetCategory with an existing ID
        offerTargetCategory.setId(1L);
        OfferTargetCategoryDTO offerTargetCategoryDTO = offerTargetCategoryMapper.toDto(offerTargetCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfferTargetCategoryMockMvc.perform(post("/api/offer-target-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offerTargetCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OfferTargetCategory in the database
        List<OfferTargetCategory> offerTargetCategoryList = offerTargetCategoryRepository.findAll();
        assertThat(offerTargetCategoryList).hasSize(databaseSizeBeforeCreate);

        // Validate the OfferTargetCategory in Elasticsearch
        verify(mockOfferTargetCategorySearchRepository, times(0)).save(offerTargetCategory);
    }

    @Test
    @Transactional
    public void getAllOfferTargetCategories() throws Exception {
        // Initialize the database
        offerTargetCategoryRepository.saveAndFlush(offerTargetCategory);

        // Get all the offerTargetCategoryList
        restOfferTargetCategoryMockMvc.perform(get("/api/offer-target-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offerTargetCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].targetCategoryId").value(hasItem(DEFAULT_TARGET_CATEGORY_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getOfferTargetCategory() throws Exception {
        // Initialize the database
        offerTargetCategoryRepository.saveAndFlush(offerTargetCategory);

        // Get the offerTargetCategory
        restOfferTargetCategoryMockMvc.perform(get("/api/offer-target-categories/{id}", offerTargetCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(offerTargetCategory.getId().intValue()))
            .andExpect(jsonPath("$.targetCategoryId").value(DEFAULT_TARGET_CATEGORY_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOfferTargetCategory() throws Exception {
        // Get the offerTargetCategory
        restOfferTargetCategoryMockMvc.perform(get("/api/offer-target-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOfferTargetCategory() throws Exception {
        // Initialize the database
        offerTargetCategoryRepository.saveAndFlush(offerTargetCategory);

        int databaseSizeBeforeUpdate = offerTargetCategoryRepository.findAll().size();

        // Update the offerTargetCategory
        OfferTargetCategory updatedOfferTargetCategory = offerTargetCategoryRepository.findById(offerTargetCategory.getId()).get();
        // Disconnect from session so that the updates on updatedOfferTargetCategory are not directly saved in db
        em.detach(updatedOfferTargetCategory);
        updatedOfferTargetCategory
            .targetCategoryId(UPDATED_TARGET_CATEGORY_ID);
        OfferTargetCategoryDTO offerTargetCategoryDTO = offerTargetCategoryMapper.toDto(updatedOfferTargetCategory);

        restOfferTargetCategoryMockMvc.perform(put("/api/offer-target-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offerTargetCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the OfferTargetCategory in the database
        List<OfferTargetCategory> offerTargetCategoryList = offerTargetCategoryRepository.findAll();
        assertThat(offerTargetCategoryList).hasSize(databaseSizeBeforeUpdate);
        OfferTargetCategory testOfferTargetCategory = offerTargetCategoryList.get(offerTargetCategoryList.size() - 1);
        assertThat(testOfferTargetCategory.getTargetCategoryId()).isEqualTo(UPDATED_TARGET_CATEGORY_ID);

        // Validate the OfferTargetCategory in Elasticsearch
        verify(mockOfferTargetCategorySearchRepository, times(1)).save(testOfferTargetCategory);
    }

    @Test
    @Transactional
    public void updateNonExistingOfferTargetCategory() throws Exception {
        int databaseSizeBeforeUpdate = offerTargetCategoryRepository.findAll().size();

        // Create the OfferTargetCategory
        OfferTargetCategoryDTO offerTargetCategoryDTO = offerTargetCategoryMapper.toDto(offerTargetCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferTargetCategoryMockMvc.perform(put("/api/offer-target-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offerTargetCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OfferTargetCategory in the database
        List<OfferTargetCategory> offerTargetCategoryList = offerTargetCategoryRepository.findAll();
        assertThat(offerTargetCategoryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OfferTargetCategory in Elasticsearch
        verify(mockOfferTargetCategorySearchRepository, times(0)).save(offerTargetCategory);
    }

    @Test
    @Transactional
    public void deleteOfferTargetCategory() throws Exception {
        // Initialize the database
        offerTargetCategoryRepository.saveAndFlush(offerTargetCategory);

        int databaseSizeBeforeDelete = offerTargetCategoryRepository.findAll().size();

        // Get the offerTargetCategory
        restOfferTargetCategoryMockMvc.perform(delete("/api/offer-target-categories/{id}", offerTargetCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OfferTargetCategory> offerTargetCategoryList = offerTargetCategoryRepository.findAll();
        assertThat(offerTargetCategoryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OfferTargetCategory in Elasticsearch
        verify(mockOfferTargetCategorySearchRepository, times(1)).deleteById(offerTargetCategory.getId());
    }

    @Test
    @Transactional
    public void searchOfferTargetCategory() throws Exception {
        // Initialize the database
        offerTargetCategoryRepository.saveAndFlush(offerTargetCategory);
        when(mockOfferTargetCategorySearchRepository.search(queryStringQuery("id:" + offerTargetCategory.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(offerTargetCategory), PageRequest.of(0, 1), 1));
        // Search the offerTargetCategory
        restOfferTargetCategoryMockMvc.perform(get("/api/_search/offer-target-categories?query=id:" + offerTargetCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offerTargetCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].targetCategoryId").value(hasItem(DEFAULT_TARGET_CATEGORY_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfferTargetCategory.class);
        OfferTargetCategory offerTargetCategory1 = new OfferTargetCategory();
        offerTargetCategory1.setId(1L);
        OfferTargetCategory offerTargetCategory2 = new OfferTargetCategory();
        offerTargetCategory2.setId(offerTargetCategory1.getId());
        assertThat(offerTargetCategory1).isEqualTo(offerTargetCategory2);
        offerTargetCategory2.setId(2L);
        assertThat(offerTargetCategory1).isNotEqualTo(offerTargetCategory2);
        offerTargetCategory1.setId(null);
        assertThat(offerTargetCategory1).isNotEqualTo(offerTargetCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfferTargetCategoryDTO.class);
        OfferTargetCategoryDTO offerTargetCategoryDTO1 = new OfferTargetCategoryDTO();
        offerTargetCategoryDTO1.setId(1L);
        OfferTargetCategoryDTO offerTargetCategoryDTO2 = new OfferTargetCategoryDTO();
        assertThat(offerTargetCategoryDTO1).isNotEqualTo(offerTargetCategoryDTO2);
        offerTargetCategoryDTO2.setId(offerTargetCategoryDTO1.getId());
        assertThat(offerTargetCategoryDTO1).isEqualTo(offerTargetCategoryDTO2);
        offerTargetCategoryDTO2.setId(2L);
        assertThat(offerTargetCategoryDTO1).isNotEqualTo(offerTargetCategoryDTO2);
        offerTargetCategoryDTO1.setId(null);
        assertThat(offerTargetCategoryDTO1).isNotEqualTo(offerTargetCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(offerTargetCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(offerTargetCategoryMapper.fromId(null)).isNull();
    }
}
