package com.kaoguan.app.web.rest;

import com.kaoguan.app.KaoguanApp;

import com.kaoguan.app.domain.StarActivity;
import com.kaoguan.app.repository.StarActivityRepository;
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
 * Test class for the StarActivityResource REST controller.
 *
 * @see StarActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KaoguanApp.class)
public class StarActivityResourceIntTest {

    private static final Integer DEFAULT_DEL_FLAG = 1;
    private static final Integer UPDATED_DEL_FLAG = 2;

    private static final ZonedDateTime DEFAULT_CREATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private StarActivityRepository starActivityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStarActivityMockMvc;

    private StarActivity starActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            StarActivityResource starActivityResource = new StarActivityResource(starActivityRepository);
        this.restStarActivityMockMvc = MockMvcBuilders.standaloneSetup(starActivityResource)
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
    public static StarActivity createEntity(EntityManager em) {
        StarActivity starActivity = new StarActivity()
                .delFlag(DEFAULT_DEL_FLAG)
                .createDateTime(DEFAULT_CREATE_DATE_TIME);
        return starActivity;
    }

    @Before
    public void initTest() {
        starActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createStarActivity() throws Exception {
        int databaseSizeBeforeCreate = starActivityRepository.findAll().size();

        // Create the StarActivity

        restStarActivityMockMvc.perform(post("/api/star-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(starActivity)))
            .andExpect(status().isCreated());

        // Validate the StarActivity in the database
        List<StarActivity> starActivityList = starActivityRepository.findAll();
        assertThat(starActivityList).hasSize(databaseSizeBeforeCreate + 1);
        StarActivity testStarActivity = starActivityList.get(starActivityList.size() - 1);
        assertThat(testStarActivity.getDelFlag()).isEqualTo(DEFAULT_DEL_FLAG);
        assertThat(testStarActivity.getCreateDateTime()).isEqualTo(DEFAULT_CREATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void createStarActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = starActivityRepository.findAll().size();

        // Create the StarActivity with an existing ID
        StarActivity existingStarActivity = new StarActivity();
        existingStarActivity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStarActivityMockMvc.perform(post("/api/star-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingStarActivity)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<StarActivity> starActivityList = starActivityRepository.findAll();
        assertThat(starActivityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStarActivities() throws Exception {
        // Initialize the database
        starActivityRepository.saveAndFlush(starActivity);

        // Get all the starActivityList
        restStarActivityMockMvc.perform(get("/api/star-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(starActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].delFlag").value(hasItem(DEFAULT_DEL_FLAG)))
            .andExpect(jsonPath("$.[*].createDateTime").value(hasItem(sameInstant(DEFAULT_CREATE_DATE_TIME))));
    }

    @Test
    @Transactional
    public void getStarActivity() throws Exception {
        // Initialize the database
        starActivityRepository.saveAndFlush(starActivity);

        // Get the starActivity
        restStarActivityMockMvc.perform(get("/api/star-activities/{id}", starActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(starActivity.getId().intValue()))
            .andExpect(jsonPath("$.delFlag").value(DEFAULT_DEL_FLAG))
            .andExpect(jsonPath("$.createDateTime").value(sameInstant(DEFAULT_CREATE_DATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingStarActivity() throws Exception {
        // Get the starActivity
        restStarActivityMockMvc.perform(get("/api/star-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStarActivity() throws Exception {
        // Initialize the database
        starActivityRepository.saveAndFlush(starActivity);
        int databaseSizeBeforeUpdate = starActivityRepository.findAll().size();

        // Update the starActivity
        StarActivity updatedStarActivity = starActivityRepository.findOne(starActivity.getId());
        updatedStarActivity
                .delFlag(UPDATED_DEL_FLAG)
                .createDateTime(UPDATED_CREATE_DATE_TIME);

        restStarActivityMockMvc.perform(put("/api/star-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStarActivity)))
            .andExpect(status().isOk());

        // Validate the StarActivity in the database
        List<StarActivity> starActivityList = starActivityRepository.findAll();
        assertThat(starActivityList).hasSize(databaseSizeBeforeUpdate);
        StarActivity testStarActivity = starActivityList.get(starActivityList.size() - 1);
        assertThat(testStarActivity.getDelFlag()).isEqualTo(UPDATED_DEL_FLAG);
        assertThat(testStarActivity.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingStarActivity() throws Exception {
        int databaseSizeBeforeUpdate = starActivityRepository.findAll().size();

        // Create the StarActivity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStarActivityMockMvc.perform(put("/api/star-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(starActivity)))
            .andExpect(status().isCreated());

        // Validate the StarActivity in the database
        List<StarActivity> starActivityList = starActivityRepository.findAll();
        assertThat(starActivityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStarActivity() throws Exception {
        // Initialize the database
        starActivityRepository.saveAndFlush(starActivity);
        int databaseSizeBeforeDelete = starActivityRepository.findAll().size();

        // Get the starActivity
        restStarActivityMockMvc.perform(delete("/api/star-activities/{id}", starActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StarActivity> starActivityList = starActivityRepository.findAll();
        assertThat(starActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StarActivity.class);
    }
}
