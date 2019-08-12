package com.diviso.graeshoppe.offer.web.rest;

import com.diviso.graeshoppe.offer.OfferApp;

import com.diviso.graeshoppe.offer.domain.OfferTarget;
import com.diviso.graeshoppe.offer.repository.OfferTargetRepository;
import com.diviso.graeshoppe.offer.repository.search.OfferTargetSearchRepository;
import com.diviso.graeshoppe.offer.service.OfferTargetService;
import com.diviso.graeshoppe.offer.service.dto.OfferTargetDTO;
import com.diviso.graeshoppe.offer.service.mapper.OfferTargetMapper;
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
 * Test class for the OfferTargetResource REST controller.
 *
 * @see OfferTargetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OfferApp.class)
public class OfferTargetResourceIntTest {

    private static final Long DEFAULT_TARGET_ID = 1L;
    private static final Long UPDATED_TARGET_ID = 2L;

    @Autowired
    private OfferTargetRepository offerTargetRepository;

    @Autowired
    private OfferTargetMapper offerTargetMapper;

    @Autowired
    private OfferTargetService offerTargetService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.offer.repository.search test package.
     *
     * @see com.diviso.graeshoppe.offer.repository.search.OfferTargetSearchRepositoryMockConfiguration
     */
    @Autowired
    private OfferTargetSearchRepository mockOfferTargetSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOfferTargetMockMvc;

    private OfferTarget offerTarget;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OfferTargetResource offerTargetResource = new OfferTargetResource(offerTargetService);
        this.restOfferTargetMockMvc = MockMvcBuilders.standaloneSetup(offerTargetResource)
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
    public static OfferTarget createEntity(EntityManager em) {
        OfferTarget offerTarget = new OfferTarget()
            .targetId(DEFAULT_TARGET_ID);
        return offerTarget;
    }

    @Before
    public void initTest() {
        offerTarget = createEntity(em);
    }

    @Test
    @Transactional
    public void createOfferTarget() throws Exception {
        int databaseSizeBeforeCreate = offerTargetRepository.findAll().size();

        // Create the OfferTarget
        OfferTargetDTO offerTargetDTO = offerTargetMapper.toDto(offerTarget);
        restOfferTargetMockMvc.perform(post("/api/offer-targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offerTargetDTO)))
            .andExpect(status().isCreated());

        // Validate the OfferTarget in the database
        List<OfferTarget> offerTargetList = offerTargetRepository.findAll();
        assertThat(offerTargetList).hasSize(databaseSizeBeforeCreate + 1);
        OfferTarget testOfferTarget = offerTargetList.get(offerTargetList.size() - 1);
        assertThat(testOfferTarget.getTargetId()).isEqualTo(DEFAULT_TARGET_ID);

        // Validate the OfferTarget in Elasticsearch
        verify(mockOfferTargetSearchRepository, times(1)).save(testOfferTarget);
    }

    @Test
    @Transactional
    public void createOfferTargetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offerTargetRepository.findAll().size();

        // Create the OfferTarget with an existing ID
        offerTarget.setId(1L);
        OfferTargetDTO offerTargetDTO = offerTargetMapper.toDto(offerTarget);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfferTargetMockMvc.perform(post("/api/offer-targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offerTargetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OfferTarget in the database
        List<OfferTarget> offerTargetList = offerTargetRepository.findAll();
        assertThat(offerTargetList).hasSize(databaseSizeBeforeCreate);

        // Validate the OfferTarget in Elasticsearch
        verify(mockOfferTargetSearchRepository, times(0)).save(offerTarget);
    }

    @Test
    @Transactional
    public void getAllOfferTargets() throws Exception {
        // Initialize the database
        offerTargetRepository.saveAndFlush(offerTarget);

        // Get all the offerTargetList
        restOfferTargetMockMvc.perform(get("/api/offer-targets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offerTarget.getId().intValue())))
            .andExpect(jsonPath("$.[*].targetId").value(hasItem(DEFAULT_TARGET_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getOfferTarget() throws Exception {
        // Initialize the database
        offerTargetRepository.saveAndFlush(offerTarget);

        // Get the offerTarget
        restOfferTargetMockMvc.perform(get("/api/offer-targets/{id}", offerTarget.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(offerTarget.getId().intValue()))
            .andExpect(jsonPath("$.targetId").value(DEFAULT_TARGET_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOfferTarget() throws Exception {
        // Get the offerTarget
        restOfferTargetMockMvc.perform(get("/api/offer-targets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOfferTarget() throws Exception {
        // Initialize the database
        offerTargetRepository.saveAndFlush(offerTarget);

        int databaseSizeBeforeUpdate = offerTargetRepository.findAll().size();

        // Update the offerTarget
        OfferTarget updatedOfferTarget = offerTargetRepository.findById(offerTarget.getId()).get();
        // Disconnect from session so that the updates on updatedOfferTarget are not directly saved in db
        em.detach(updatedOfferTarget);
        updatedOfferTarget
            .targetId(UPDATED_TARGET_ID);
        OfferTargetDTO offerTargetDTO = offerTargetMapper.toDto(updatedOfferTarget);

        restOfferTargetMockMvc.perform(put("/api/offer-targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offerTargetDTO)))
            .andExpect(status().isOk());

        // Validate the OfferTarget in the database
        List<OfferTarget> offerTargetList = offerTargetRepository.findAll();
        assertThat(offerTargetList).hasSize(databaseSizeBeforeUpdate);
        OfferTarget testOfferTarget = offerTargetList.get(offerTargetList.size() - 1);
        assertThat(testOfferTarget.getTargetId()).isEqualTo(UPDATED_TARGET_ID);

        // Validate the OfferTarget in Elasticsearch
        verify(mockOfferTargetSearchRepository, times(1)).save(testOfferTarget);
    }

    @Test
    @Transactional
    public void updateNonExistingOfferTarget() throws Exception {
        int databaseSizeBeforeUpdate = offerTargetRepository.findAll().size();

        // Create the OfferTarget
        OfferTargetDTO offerTargetDTO = offerTargetMapper.toDto(offerTarget);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferTargetMockMvc.perform(put("/api/offer-targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offerTargetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OfferTarget in the database
        List<OfferTarget> offerTargetList = offerTargetRepository.findAll();
        assertThat(offerTargetList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OfferTarget in Elasticsearch
        verify(mockOfferTargetSearchRepository, times(0)).save(offerTarget);
    }

    @Test
    @Transactional
    public void deleteOfferTarget() throws Exception {
        // Initialize the database
        offerTargetRepository.saveAndFlush(offerTarget);

        int databaseSizeBeforeDelete = offerTargetRepository.findAll().size();

        // Get the offerTarget
        restOfferTargetMockMvc.perform(delete("/api/offer-targets/{id}", offerTarget.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OfferTarget> offerTargetList = offerTargetRepository.findAll();
        assertThat(offerTargetList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OfferTarget in Elasticsearch
        verify(mockOfferTargetSearchRepository, times(1)).deleteById(offerTarget.getId());
    }

    @Test
    @Transactional
    public void searchOfferTarget() throws Exception {
        // Initialize the database
        offerTargetRepository.saveAndFlush(offerTarget);
        when(mockOfferTargetSearchRepository.search(queryStringQuery("id:" + offerTarget.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(offerTarget), PageRequest.of(0, 1), 1));
        // Search the offerTarget
        restOfferTargetMockMvc.perform(get("/api/_search/offer-targets?query=id:" + offerTarget.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offerTarget.getId().intValue())))
            .andExpect(jsonPath("$.[*].targetId").value(hasItem(DEFAULT_TARGET_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfferTarget.class);
        OfferTarget offerTarget1 = new OfferTarget();
        offerTarget1.setId(1L);
        OfferTarget offerTarget2 = new OfferTarget();
        offerTarget2.setId(offerTarget1.getId());
        assertThat(offerTarget1).isEqualTo(offerTarget2);
        offerTarget2.setId(2L);
        assertThat(offerTarget1).isNotEqualTo(offerTarget2);
        offerTarget1.setId(null);
        assertThat(offerTarget1).isNotEqualTo(offerTarget2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfferTargetDTO.class);
        OfferTargetDTO offerTargetDTO1 = new OfferTargetDTO();
        offerTargetDTO1.setId(1L);
        OfferTargetDTO offerTargetDTO2 = new OfferTargetDTO();
        assertThat(offerTargetDTO1).isNotEqualTo(offerTargetDTO2);
        offerTargetDTO2.setId(offerTargetDTO1.getId());
        assertThat(offerTargetDTO1).isEqualTo(offerTargetDTO2);
        offerTargetDTO2.setId(2L);
        assertThat(offerTargetDTO1).isNotEqualTo(offerTargetDTO2);
        offerTargetDTO1.setId(null);
        assertThat(offerTargetDTO1).isNotEqualTo(offerTargetDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(offerTargetMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(offerTargetMapper.fromId(null)).isNull();
    }
}
