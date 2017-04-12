package com.kaoguan.app.web.rest;

import com.kaoguan.app.KaoguanApp;

import com.kaoguan.app.domain.Activity;
import com.kaoguan.app.repository.ActivityRepository;
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

import com.kaoguan.app.domain.enumeration.ActivityType;
/**
 * Test class for the ActivityResource REST controller.
 *
 * @see ActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KaoguanApp.class)
public class ActivityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINAZTION = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAZTION = "BBBBBBBBBB";

    private static final String DEFAULT_AGE_RANGER = "AAAAAAAAAA";
    private static final String UPDATED_AGE_RANGER = "BBBBBBBBBB";

    private static final String DEFAULT_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_PRICE = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION_CITY = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_STAR_COUNT = 1;
    private static final Integer UPDATED_STAR_COUNT = 2;

    private static final Integer DEFAULT_JOIN_PERSON_COUNT = 1;
    private static final Integer UPDATED_JOIN_PERSON_COUNT = 2;

    private static final Integer DEFAULT_COMMENT_COUNT = 1;
    private static final Integer UPDATED_COMMENT_COUNT = 2;

    private static final String DEFAULT_IMAGE_1 = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_2 = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_3 = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_4 = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_4 = "BBBBBBBBBB";

    private static final ActivityType DEFAULT_ACTIVITY_TYPE = ActivityType.Product;
    private static final ActivityType UPDATED_ACTIVITY_TYPE = ActivityType.Test;

    private static final Integer DEFAULT_DEL_FLAG = 1;
    private static final Integer UPDATED_DEL_FLAG = 2;

    private static final ZonedDateTime DEFAULT_DATETIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActivityMockMvc;

    private Activity activity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ActivityResource activityResource = new ActivityResource(activityRepository);
        this.restActivityMockMvc = MockMvcBuilders.standaloneSetup(activityResource)
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
    public static Activity createEntity(EntityManager em) {
        Activity activity = new Activity()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .originaztion(DEFAULT_ORIGINAZTION)
                .ageRanger(DEFAULT_AGE_RANGER)
                .price(DEFAULT_PRICE)
                .remark(DEFAULT_REMARK)
                .locationCity(DEFAULT_LOCATION_CITY)
                .address(DEFAULT_ADDRESS)
                .starCount(DEFAULT_STAR_COUNT)
                .joinPersonCount(DEFAULT_JOIN_PERSON_COUNT)
                .commentCount(DEFAULT_COMMENT_COUNT)
                .image1(DEFAULT_IMAGE_1)
                .image2(DEFAULT_IMAGE_2)
                .image3(DEFAULT_IMAGE_3)
                .image4(DEFAULT_IMAGE_4)
                .activityType(DEFAULT_ACTIVITY_TYPE)
                .delFlag(DEFAULT_DEL_FLAG)
                .datetime(DEFAULT_DATETIME)
                .createTime(DEFAULT_CREATE_TIME)
                .updateTime(DEFAULT_UPDATE_TIME);
        return activity;
    }

    @Before
    public void initTest() {
        activity = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivity() throws Exception {
        int databaseSizeBeforeCreate = activityRepository.findAll().size();

        // Create the Activity

        restActivityMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isCreated());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate + 1);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testActivity.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testActivity.getOriginaztion()).isEqualTo(DEFAULT_ORIGINAZTION);
        assertThat(testActivity.getAgeRanger()).isEqualTo(DEFAULT_AGE_RANGER);
        assertThat(testActivity.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testActivity.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testActivity.getLocationCity()).isEqualTo(DEFAULT_LOCATION_CITY);
        assertThat(testActivity.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testActivity.getStarCount()).isEqualTo(DEFAULT_STAR_COUNT);
        assertThat(testActivity.getJoinPersonCount()).isEqualTo(DEFAULT_JOIN_PERSON_COUNT);
        assertThat(testActivity.getCommentCount()).isEqualTo(DEFAULT_COMMENT_COUNT);
        assertThat(testActivity.getImage1()).isEqualTo(DEFAULT_IMAGE_1);
        assertThat(testActivity.getImage2()).isEqualTo(DEFAULT_IMAGE_2);
        assertThat(testActivity.getImage3()).isEqualTo(DEFAULT_IMAGE_3);
        assertThat(testActivity.getImage4()).isEqualTo(DEFAULT_IMAGE_4);
        assertThat(testActivity.getActivityType()).isEqualTo(DEFAULT_ACTIVITY_TYPE);
        assertThat(testActivity.getDelFlag()).isEqualTo(DEFAULT_DEL_FLAG);
        assertThat(testActivity.getDatetime()).isEqualTo(DEFAULT_DATETIME);
        assertThat(testActivity.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testActivity.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activityRepository.findAll().size();

        // Create the Activity with an existing ID
        Activity existingActivity = new Activity();
        existingActivity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingActivity)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllActivities() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList
        restActivityMockMvc.perform(get("/api/activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].originaztion").value(hasItem(DEFAULT_ORIGINAZTION.toString())))
            .andExpect(jsonPath("$.[*].ageRanger").value(hasItem(DEFAULT_AGE_RANGER.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].locationCity").value(hasItem(DEFAULT_LOCATION_CITY.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].starCount").value(hasItem(DEFAULT_STAR_COUNT)))
            .andExpect(jsonPath("$.[*].joinPersonCount").value(hasItem(DEFAULT_JOIN_PERSON_COUNT)))
            .andExpect(jsonPath("$.[*].commentCount").value(hasItem(DEFAULT_COMMENT_COUNT)))
            .andExpect(jsonPath("$.[*].image1").value(hasItem(DEFAULT_IMAGE_1.toString())))
            .andExpect(jsonPath("$.[*].image2").value(hasItem(DEFAULT_IMAGE_2.toString())))
            .andExpect(jsonPath("$.[*].image3").value(hasItem(DEFAULT_IMAGE_3.toString())))
            .andExpect(jsonPath("$.[*].image4").value(hasItem(DEFAULT_IMAGE_4.toString())))
            .andExpect(jsonPath("$.[*].activityType").value(hasItem(DEFAULT_ACTIVITY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].delFlag").value(hasItem(DEFAULT_DEL_FLAG)))
            .andExpect(jsonPath("$.[*].datetime").value(hasItem(sameInstant(DEFAULT_DATETIME))))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))));
    }

    @Test
    @Transactional
    public void getActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get the activity
        restActivityMockMvc.perform(get("/api/activities/{id}", activity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.originaztion").value(DEFAULT_ORIGINAZTION.toString()))
            .andExpect(jsonPath("$.ageRanger").value(DEFAULT_AGE_RANGER.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.locationCity").value(DEFAULT_LOCATION_CITY.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.starCount").value(DEFAULT_STAR_COUNT))
            .andExpect(jsonPath("$.joinPersonCount").value(DEFAULT_JOIN_PERSON_COUNT))
            .andExpect(jsonPath("$.commentCount").value(DEFAULT_COMMENT_COUNT))
            .andExpect(jsonPath("$.image1").value(DEFAULT_IMAGE_1.toString()))
            .andExpect(jsonPath("$.image2").value(DEFAULT_IMAGE_2.toString()))
            .andExpect(jsonPath("$.image3").value(DEFAULT_IMAGE_3.toString()))
            .andExpect(jsonPath("$.image4").value(DEFAULT_IMAGE_4.toString()))
            .andExpect(jsonPath("$.activityType").value(DEFAULT_ACTIVITY_TYPE.toString()))
            .andExpect(jsonPath("$.delFlag").value(DEFAULT_DEL_FLAG))
            .andExpect(jsonPath("$.datetime").value(sameInstant(DEFAULT_DATETIME)))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingActivity() throws Exception {
        // Get the activity
        restActivityMockMvc.perform(get("/api/activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Update the activity
        Activity updatedActivity = activityRepository.findOne(activity.getId());
        updatedActivity
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .originaztion(UPDATED_ORIGINAZTION)
                .ageRanger(UPDATED_AGE_RANGER)
                .price(UPDATED_PRICE)
                .remark(UPDATED_REMARK)
                .locationCity(UPDATED_LOCATION_CITY)
                .address(UPDATED_ADDRESS)
                .starCount(UPDATED_STAR_COUNT)
                .joinPersonCount(UPDATED_JOIN_PERSON_COUNT)
                .commentCount(UPDATED_COMMENT_COUNT)
                .image1(UPDATED_IMAGE_1)
                .image2(UPDATED_IMAGE_2)
                .image3(UPDATED_IMAGE_3)
                .image4(UPDATED_IMAGE_4)
                .activityType(UPDATED_ACTIVITY_TYPE)
                .delFlag(UPDATED_DEL_FLAG)
                .datetime(UPDATED_DATETIME)
                .createTime(UPDATED_CREATE_TIME)
                .updateTime(UPDATED_UPDATE_TIME);

        restActivityMockMvc.perform(put("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActivity)))
            .andExpect(status().isOk());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testActivity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testActivity.getOriginaztion()).isEqualTo(UPDATED_ORIGINAZTION);
        assertThat(testActivity.getAgeRanger()).isEqualTo(UPDATED_AGE_RANGER);
        assertThat(testActivity.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testActivity.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testActivity.getLocationCity()).isEqualTo(UPDATED_LOCATION_CITY);
        assertThat(testActivity.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testActivity.getStarCount()).isEqualTo(UPDATED_STAR_COUNT);
        assertThat(testActivity.getJoinPersonCount()).isEqualTo(UPDATED_JOIN_PERSON_COUNT);
        assertThat(testActivity.getCommentCount()).isEqualTo(UPDATED_COMMENT_COUNT);
        assertThat(testActivity.getImage1()).isEqualTo(UPDATED_IMAGE_1);
        assertThat(testActivity.getImage2()).isEqualTo(UPDATED_IMAGE_2);
        assertThat(testActivity.getImage3()).isEqualTo(UPDATED_IMAGE_3);
        assertThat(testActivity.getImage4()).isEqualTo(UPDATED_IMAGE_4);
        assertThat(testActivity.getActivityType()).isEqualTo(UPDATED_ACTIVITY_TYPE);
        assertThat(testActivity.getDelFlag()).isEqualTo(UPDATED_DEL_FLAG);
        assertThat(testActivity.getDatetime()).isEqualTo(UPDATED_DATETIME);
        assertThat(testActivity.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testActivity.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Create the Activity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActivityMockMvc.perform(put("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isCreated());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);
        int databaseSizeBeforeDelete = activityRepository.findAll().size();

        // Get the activity
        restActivityMockMvc.perform(delete("/api/activities/{id}", activity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Activity.class);
    }
}
