package com.kaoguan.app.web.rest;

import com.kaoguan.app.KaoguanApp;

import com.kaoguan.app.domain.PreferActivity;
import com.kaoguan.app.repository.PreferActivityRepository;
import com.kaoguan.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.kaoguan.app.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PreferActivityResource REST controller.
 *
 * @see PreferActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KaoguanApp.class)
public class PreferActivityResourceIntTest {

    private static final Integer DEFAULT_DEL_FLAG = 1;
    private static final Integer UPDATED_DEL_FLAG = 2;

    private static final ZonedDateTime DEFAULT_CREATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PreferActivityRepository preferActivityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPreferActivityMockMvc;

    private PreferActivity preferActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            PreferActivityResource preferActivityResource = new PreferActivityResource(preferActivityRepository);
        this.restPreferActivityMockMvc = MockMvcBuilders.standaloneSetup(preferActivityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreferActivity createEntity(EntityManager em) {
        PreferActivity preferActivity = new PreferActivity()
                .delFlag(DEFAULT_DEL_FLAG)
                .createDateTime(DEFAULT_CREATE_DATE_TIME);
        return preferActivity;
    }

    @Before
    public void initTest() {
        preferActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createPreferActivity() throws Exception {
        int databaseSizeBeforeCreate = preferActivityRepository.findAll().size();

        // Create the PreferActivity

        restPreferActivityMockMvc.perform(post("/api/prefer-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preferActivity)))
            .andExpect(status().isCreated());

        // Validate the PreferActivity in the database
        List<PreferActivity> preferActivityList = preferActivityRepository.findAll();
        assertThat(preferActivityList).hasSize(databaseSizeBeforeCreate + 1);
        PreferActivity testPreferActivity = preferActivityList.get(preferActivityList.size() - 1);
        assertThat(testPreferActivity.getDelFlag()).isEqualTo(DEFAULT_DEL_FLAG);
        assertThat(testPreferActivity.getCreateDateTime()).isEqualTo(DEFAULT_CREATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void createPreferActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = preferActivityRepository.findAll().size();

        // Create the PreferActivity with an existing ID
        PreferActivity existingPreferActivity = new PreferActivity();
        existingPreferActivity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreferActivityMockMvc.perform(post("/api/prefer-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPreferActivity)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PreferActivity> preferActivityList = preferActivityRepository.findAll();
        assertThat(preferActivityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPreferActivities() throws Exception {
        // Initialize the database
        preferActivityRepository.saveAndFlush(preferActivity);

        // Get all the preferActivityList
        restPreferActivityMockMvc.perform(get("/api/prefer-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preferActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].delFlag").value(hasItem(DEFAULT_DEL_FLAG)))
            .andExpect(jsonPath("$.[*].createDateTime").value(hasItem(sameInstant(DEFAULT_CREATE_DATE_TIME))));
    }

    @Test
    @Transactional
    public void getPreferActivity() throws Exception {
        // Initialize the database
        preferActivityRepository.saveAndFlush(preferActivity);

        // Get the preferActivity
        restPreferActivityMockMvc.perform(get("/api/prefer-activities/{id}", preferActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(preferActivity.getId().intValue()))
            .andExpect(jsonPath("$.delFlag").value(DEFAULT_DEL_FLAG))
            .andExpect(jsonPath("$.createDateTime").value(sameInstant(DEFAULT_CREATE_DATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingPreferActivity() throws Exception {
        // Get the preferActivity
        restPreferActivityMockMvc.perform(get("/api/prefer-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePreferActivity() throws Exception {
        // Initialize the database
        preferActivityRepository.saveAndFlush(preferActivity);
        int databaseSizeBeforeUpdate = preferActivityRepository.findAll().size();

        // Update the preferActivity
        PreferActivity updatedPreferActivity = preferActivityRepository.findOne(preferActivity.getId());
        updatedPreferActivity
                .delFlag(UPDATED_DEL_FLAG)
                .createDateTime(UPDATED_CREATE_DATE_TIME);

        restPreferActivityMockMvc.perform(put("/api/prefer-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPreferActivity)))
            .andExpect(status().isOk());

        // Validate the PreferActivity in the database
        List<PreferActivity> preferActivityList = preferActivityRepository.findAll();
        assertThat(preferActivityList).hasSize(databaseSizeBeforeUpdate);
        PreferActivity testPreferActivity = preferActivityList.get(preferActivityList.size() - 1);
        assertThat(testPreferActivity.getDelFlag()).isEqualTo(UPDATED_DEL_FLAG);
        assertThat(testPreferActivity.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingPreferActivity() throws Exception {
        int databaseSizeBeforeUpdate = preferActivityRepository.findAll().size();

        // Create the PreferActivity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPreferActivityMockMvc.perform(put("/api/prefer-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preferActivity)))
            .andExpect(status().isCreated());

        // Validate the PreferActivity in the database
        List<PreferActivity> preferActivityList = preferActivityRepository.findAll();
        assertThat(preferActivityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePreferActivity() throws Exception {
        // Initialize the database
        preferActivityRepository.saveAndFlush(preferActivity);
        int databaseSizeBeforeDelete = preferActivityRepository.findAll().size();

        // Get the preferActivity
        restPreferActivityMockMvc.perform(delete("/api/prefer-activities/{id}", preferActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PreferActivity> preferActivityList = preferActivityRepository.findAll();
        assertThat(preferActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreferActivity.class);
    }
}
