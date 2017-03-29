package com.kaoguan.app.web.rest;

import com.kaoguan.app.KaoguanApp;

import com.kaoguan.app.domain.JoinActivity;
import com.kaoguan.app.repository.JoinActivityRepository;
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
 * Test class for the JoinActivityResource REST controller.
 *
 * @see JoinActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KaoguanApp.class)
public class JoinActivityResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_DEL_FLAG = 1;
    private static final Integer UPDATED_DEL_FLAG = 2;

    @Autowired
    private JoinActivityRepository joinActivityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJoinActivityMockMvc;

    private JoinActivity joinActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            JoinActivityResource joinActivityResource = new JoinActivityResource(joinActivityRepository);
        this.restJoinActivityMockMvc = MockMvcBuilders.standaloneSetup(joinActivityResource)
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
    public static JoinActivity createEntity(EntityManager em) {
        JoinActivity joinActivity = new JoinActivity()
                .createDateTime(DEFAULT_CREATE_DATE_TIME)
                .delFlag(DEFAULT_DEL_FLAG);
        return joinActivity;
    }

    @Before
    public void initTest() {
        joinActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createJoinActivity() throws Exception {
        int databaseSizeBeforeCreate = joinActivityRepository.findAll().size();

        // Create the JoinActivity

        restJoinActivityMockMvc.perform(post("/api/join-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(joinActivity)))
            .andExpect(status().isCreated());

        // Validate the JoinActivity in the database
        List<JoinActivity> joinActivityList = joinActivityRepository.findAll();
        assertThat(joinActivityList).hasSize(databaseSizeBeforeCreate + 1);
        JoinActivity testJoinActivity = joinActivityList.get(joinActivityList.size() - 1);
        assertThat(testJoinActivity.getCreateDateTime()).isEqualTo(DEFAULT_CREATE_DATE_TIME);
        assertThat(testJoinActivity.getDelFlag()).isEqualTo(DEFAULT_DEL_FLAG);
    }

    @Test
    @Transactional
    public void createJoinActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = joinActivityRepository.findAll().size();

        // Create the JoinActivity with an existing ID
        JoinActivity existingJoinActivity = new JoinActivity();
        existingJoinActivity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJoinActivityMockMvc.perform(post("/api/join-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingJoinActivity)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<JoinActivity> joinActivityList = joinActivityRepository.findAll();
        assertThat(joinActivityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllJoinActivities() throws Exception {
        // Initialize the database
        joinActivityRepository.saveAndFlush(joinActivity);

        // Get all the joinActivityList
        restJoinActivityMockMvc.perform(get("/api/join-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(joinActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].createDateTime").value(hasItem(sameInstant(DEFAULT_CREATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].delFlag").value(hasItem(DEFAULT_DEL_FLAG)));
    }

    @Test
    @Transactional
    public void getJoinActivity() throws Exception {
        // Initialize the database
        joinActivityRepository.saveAndFlush(joinActivity);

        // Get the joinActivity
        restJoinActivityMockMvc.perform(get("/api/join-activities/{id}", joinActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(joinActivity.getId().intValue()))
            .andExpect(jsonPath("$.createDateTime").value(sameInstant(DEFAULT_CREATE_DATE_TIME)))
            .andExpect(jsonPath("$.delFlag").value(DEFAULT_DEL_FLAG));
    }

    @Test
    @Transactional
    public void getNonExistingJoinActivity() throws Exception {
        // Get the joinActivity
        restJoinActivityMockMvc.perform(get("/api/join-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJoinActivity() throws Exception {
        // Initialize the database
        joinActivityRepository.saveAndFlush(joinActivity);
        int databaseSizeBeforeUpdate = joinActivityRepository.findAll().size();

        // Update the joinActivity
        JoinActivity updatedJoinActivity = joinActivityRepository.findOne(joinActivity.getId());
        updatedJoinActivity
                .createDateTime(UPDATED_CREATE_DATE_TIME)
                .delFlag(UPDATED_DEL_FLAG);

        restJoinActivityMockMvc.perform(put("/api/join-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJoinActivity)))
            .andExpect(status().isOk());

        // Validate the JoinActivity in the database
        List<JoinActivity> joinActivityList = joinActivityRepository.findAll();
        assertThat(joinActivityList).hasSize(databaseSizeBeforeUpdate);
        JoinActivity testJoinActivity = joinActivityList.get(joinActivityList.size() - 1);
        assertThat(testJoinActivity.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
        assertThat(testJoinActivity.getDelFlag()).isEqualTo(UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    public void updateNonExistingJoinActivity() throws Exception {
        int databaseSizeBeforeUpdate = joinActivityRepository.findAll().size();

        // Create the JoinActivity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJoinActivityMockMvc.perform(put("/api/join-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(joinActivity)))
            .andExpect(status().isCreated());

        // Validate the JoinActivity in the database
        List<JoinActivity> joinActivityList = joinActivityRepository.findAll();
        assertThat(joinActivityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJoinActivity() throws Exception {
        // Initialize the database
        joinActivityRepository.saveAndFlush(joinActivity);
        int databaseSizeBeforeDelete = joinActivityRepository.findAll().size();

        // Get the joinActivity
        restJoinActivityMockMvc.perform(delete("/api/join-activities/{id}", joinActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JoinActivity> joinActivityList = joinActivityRepository.findAll();
        assertThat(joinActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JoinActivity.class);
    }
}
