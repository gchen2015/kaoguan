package com.kaoguan.app.web.rest;

import com.kaoguan.app.KaoguanApp;
import com.kaoguan.app.domain.Upload;
import com.kaoguan.app.mongo.repository.UploadRepository;
import com.kaoguan.app.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Test class for the UploadResource REST controller.
 *
 * @see UploadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KaoguanApp.class)
public class UploadResourceIntTest {

    private static final String DEFAULT_ORIGINAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPLOADED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPLOADED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MD_5_SUM = "AAAAAAAAAA";
    private static final String UPDATED_MD_5_SUM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_UPLOAD_COMPLETE = false;
    private static final Boolean UPDATED_UPLOAD_COMPLETE = true;

    private static final Integer DEFAULT_TOTAL_CHUNKS = 1;
    private static final Integer UPDATED_TOTAL_CHUNKS = 2;

    private static final Long DEFAULT_TOTAL_SIZE = 1L;
    private static final Long UPDATED_TOTAL_SIZE = 2L;

    private static final LocalDate DEFAULT_COMPLETED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMPLETED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private UploadRepository uploadRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUploadMockMvc;

    private Upload upload;

    @Before
    public void setup() {
        /**
        MockitoAnnotations.initMocks(this);
            UploadResource uploadResource = new UploadResource(uploadRepository);
        this.restUploadMockMvc = MockMvcBuilders.standaloneSetup(uploadResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
         **/
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */

    /**
    public static Upload createEntity(EntityManager em) {
        Upload upload = new Upload()
                .originalName(DEFAULT_ORIGINAL_NAME)
                .uploadedAt(DEFAULT_UPLOADED_AT)
                .md5sum(DEFAULT_MD_5_SUM)
                .uploadComplete(DEFAULT_UPLOAD_COMPLETE)
                .totalChunks(DEFAULT_TOTAL_CHUNKS)
                .totalSize(DEFAULT_TOTAL_SIZE)
                .completedAt(DEFAULT_COMPLETED_AT);
        return upload;
    }

    @Before
    public void initTest() {
        upload = createEntity(em);
    }

    @Test
    @Transactional
    public void createUpload() throws Exception {
        int databaseSizeBeforeCreate = uploadRepository.findAll().size();

        // Create the Upload

        restUploadMockMvc.perform(post("/api/uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(upload)))
            .andExpect(status().isCreated());

        // Validate the Upload in the database
        List<Upload> uploadList = uploadRepository.findAll();
        assertThat(uploadList).hasSize(databaseSizeBeforeCreate + 1);
        Upload testUpload = uploadList.get(uploadList.size() - 1);
        assertThat(testUpload.getOriginalName()).isEqualTo(DEFAULT_ORIGINAL_NAME);
        assertThat(testUpload.getUploadedAt()).isEqualTo(DEFAULT_UPLOADED_AT);
        assertThat(testUpload.getMd5sum()).isEqualTo(DEFAULT_MD_5_SUM);
        assertThat(testUpload.isUploadComplete()).isEqualTo(DEFAULT_UPLOAD_COMPLETE);
        assertThat(testUpload.getTotalChunks()).isEqualTo(DEFAULT_TOTAL_CHUNKS);
        assertThat(testUpload.getTotalSize()).isEqualTo(DEFAULT_TOTAL_SIZE);
        assertThat(testUpload.getCompletedAt()).isEqualTo(DEFAULT_COMPLETED_AT);
    }

    @Test
    @Transactional
    public void createUploadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uploadRepository.findAll().size();

        // Create the Upload with an existing ID
        Upload existingUpload = new Upload();
        existingUpload.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUploadMockMvc.perform(post("/api/uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUpload)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Upload> uploadList = uploadRepository.findAll();
        assertThat(uploadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUploads() throws Exception {
        // Initialize the database
        uploadRepository.saveAndFlush(upload);

        // Get all the uploadList
        restUploadMockMvc.perform(get("/api/uploads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(upload.getId().intValue())))
            .andExpect(jsonPath("$.[*].originalName").value(hasItem(DEFAULT_ORIGINAL_NAME.toString())))
            .andExpect(jsonPath("$.[*].uploadedAt").value(hasItem(DEFAULT_UPLOADED_AT.toString())))
            .andExpect(jsonPath("$.[*].md5sum").value(hasItem(DEFAULT_MD_5_SUM.toString())))
            .andExpect(jsonPath("$.[*].uploadComplete").value(hasItem(DEFAULT_UPLOAD_COMPLETE.booleanValue())))
            .andExpect(jsonPath("$.[*].totalChunks").value(hasItem(DEFAULT_TOTAL_CHUNKS)))
            .andExpect(jsonPath("$.[*].totalSize").value(hasItem(DEFAULT_TOTAL_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].completedAt").value(hasItem(DEFAULT_COMPLETED_AT.toString())));
    }

    @Test
    @Transactional
    public void getUpload() throws Exception {
        // Initialize the database
        uploadRepository.saveAndFlush(upload);

        // Get the upload
        restUploadMockMvc.perform(get("/api/uploads/{id}", upload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(upload.getId().intValue()))
            .andExpect(jsonPath("$.originalName").value(DEFAULT_ORIGINAL_NAME.toString()))
            .andExpect(jsonPath("$.uploadedAt").value(DEFAULT_UPLOADED_AT.toString()))
            .andExpect(jsonPath("$.md5sum").value(DEFAULT_MD_5_SUM.toString()))
            .andExpect(jsonPath("$.uploadComplete").value(DEFAULT_UPLOAD_COMPLETE.booleanValue()))
            .andExpect(jsonPath("$.totalChunks").value(DEFAULT_TOTAL_CHUNKS))
            .andExpect(jsonPath("$.totalSize").value(DEFAULT_TOTAL_SIZE.intValue()))
            .andExpect(jsonPath("$.completedAt").value(DEFAULT_COMPLETED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUpload() throws Exception {
        // Get the upload
        restUploadMockMvc.perform(get("/api/uploads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUpload() throws Exception {
        // Initialize the database
        uploadRepository.saveAndFlush(upload);
        int databaseSizeBeforeUpdate = uploadRepository.findAll().size();

        // Update the upload
        Upload updatedUpload = uploadRepository.findOne(upload.getId());
        updatedUpload
                .originalName(UPDATED_ORIGINAL_NAME)
                .uploadedAt(UPDATED_UPLOADED_AT)
                .md5sum(UPDATED_MD_5_SUM)
                .uploadComplete(UPDATED_UPLOAD_COMPLETE)
                .totalChunks(UPDATED_TOTAL_CHUNKS)
                .totalSize(UPDATED_TOTAL_SIZE)
                .completedAt(UPDATED_COMPLETED_AT);

        restUploadMockMvc.perform(put("/api/uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUpload)))
            .andExpect(status().isOk());

        // Validate the Upload in the database
        List<Upload> uploadList = uploadRepository.findAll();
        assertThat(uploadList).hasSize(databaseSizeBeforeUpdate);
        Upload testUpload = uploadList.get(uploadList.size() - 1);
        assertThat(testUpload.getOriginalName()).isEqualTo(UPDATED_ORIGINAL_NAME);
        assertThat(testUpload.getUploadedAt()).isEqualTo(UPDATED_UPLOADED_AT);
        assertThat(testUpload.getMd5sum()).isEqualTo(UPDATED_MD_5_SUM);
        assertThat(testUpload.isUploadComplete()).isEqualTo(UPDATED_UPLOAD_COMPLETE);
        assertThat(testUpload.getTotalChunks()).isEqualTo(UPDATED_TOTAL_CHUNKS);
        assertThat(testUpload.getTotalSize()).isEqualTo(UPDATED_TOTAL_SIZE);
        assertThat(testUpload.getCompletedAt()).isEqualTo(UPDATED_COMPLETED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingUpload() throws Exception {
        int databaseSizeBeforeUpdate = uploadRepository.findAll().size();

        // Create the Upload

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUploadMockMvc.perform(put("/api/uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(upload)))
            .andExpect(status().isCreated());

        // Validate the Upload in the database
        List<Upload> uploadList = uploadRepository.findAll();
        assertThat(uploadList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUpload() throws Exception {
        // Initialize the database
        uploadRepository.saveAndFlush(upload);
        int databaseSizeBeforeDelete = uploadRepository.findAll().size();

        // Get the upload
        restUploadMockMvc.perform(delete("/api/uploads/{id}", upload.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Upload> uploadList = uploadRepository.findAll();
        assertThat(uploadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Upload.class);
    }

    **/
}
