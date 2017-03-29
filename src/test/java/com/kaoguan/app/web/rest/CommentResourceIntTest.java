package com.kaoguan.app.web.rest;

import com.kaoguan.app.KaoguanApp;

import com.kaoguan.app.domain.Comment;
import com.kaoguan.app.repository.CommentRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CommentResource REST controller.
 *
 * @see CommentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KaoguanApp.class)
public class CommentResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_IMAGE_1 = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_2 = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_3 = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_4 = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_4 = "BBBBBBBBBB";

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommentMockMvc;

    private Comment comment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CommentResource commentResource = new CommentResource(commentRepository);
        this.restCommentMockMvc = MockMvcBuilders.standaloneSetup(commentResource)
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
    public static Comment createEntity(EntityManager em) {
        Comment comment = new Comment()
                .description(DEFAULT_DESCRIPTION)
                .remark(DEFAULT_REMARK)
                .dateTime(DEFAULT_DATE_TIME)
                .image1(DEFAULT_IMAGE_1)
                .image2(DEFAULT_IMAGE_2)
                .image3(DEFAULT_IMAGE_3)
                .image4(DEFAULT_IMAGE_4);
        return comment;
    }

    @Before
    public void initTest() {
        comment = createEntity(em);
    }

    @Test
    @Transactional
    public void createComment() throws Exception {
        int databaseSizeBeforeCreate = commentRepository.findAll().size();

        // Create the Comment

        restCommentMockMvc.perform(post("/api/comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comment)))
            .andExpect(status().isCreated());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeCreate + 1);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testComment.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testComment.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
        assertThat(testComment.getImage1()).isEqualTo(DEFAULT_IMAGE_1);
        assertThat(testComment.getImage2()).isEqualTo(DEFAULT_IMAGE_2);
        assertThat(testComment.getImage3()).isEqualTo(DEFAULT_IMAGE_3);
        assertThat(testComment.getImage4()).isEqualTo(DEFAULT_IMAGE_4);
    }

    @Test
    @Transactional
    public void createCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commentRepository.findAll().size();

        // Create the Comment with an existing ID
        Comment existingComment = new Comment();
        existingComment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentMockMvc.perform(post("/api/comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingComment)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllComments() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList
        restCommentMockMvc.perform(get("/api/comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comment.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(DEFAULT_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].image1").value(hasItem(DEFAULT_IMAGE_1.toString())))
            .andExpect(jsonPath("$.[*].image2").value(hasItem(DEFAULT_IMAGE_2.toString())))
            .andExpect(jsonPath("$.[*].image3").value(hasItem(DEFAULT_IMAGE_3.toString())))
            .andExpect(jsonPath("$.[*].image4").value(hasItem(DEFAULT_IMAGE_4.toString())));
    }

    @Test
    @Transactional
    public void getComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get the comment
        restCommentMockMvc.perform(get("/api/comments/{id}", comment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comment.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.dateTime").value(DEFAULT_DATE_TIME.toString()))
            .andExpect(jsonPath("$.image1").value(DEFAULT_IMAGE_1.toString()))
            .andExpect(jsonPath("$.image2").value(DEFAULT_IMAGE_2.toString()))
            .andExpect(jsonPath("$.image3").value(DEFAULT_IMAGE_3.toString()))
            .andExpect(jsonPath("$.image4").value(DEFAULT_IMAGE_4.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingComment() throws Exception {
        // Get the comment
        restCommentMockMvc.perform(get("/api/comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);
        int databaseSizeBeforeUpdate = commentRepository.findAll().size();

        // Update the comment
        Comment updatedComment = commentRepository.findOne(comment.getId());
        updatedComment
                .description(UPDATED_DESCRIPTION)
                .remark(UPDATED_REMARK)
                .dateTime(UPDATED_DATE_TIME)
                .image1(UPDATED_IMAGE_1)
                .image2(UPDATED_IMAGE_2)
                .image3(UPDATED_IMAGE_3)
                .image4(UPDATED_IMAGE_4);

        restCommentMockMvc.perform(put("/api/comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComment)))
            .andExpect(status().isOk());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testComment.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testComment.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testComment.getImage1()).isEqualTo(UPDATED_IMAGE_1);
        assertThat(testComment.getImage2()).isEqualTo(UPDATED_IMAGE_2);
        assertThat(testComment.getImage3()).isEqualTo(UPDATED_IMAGE_3);
        assertThat(testComment.getImage4()).isEqualTo(UPDATED_IMAGE_4);
    }

    @Test
    @Transactional
    public void updateNonExistingComment() throws Exception {
        int databaseSizeBeforeUpdate = commentRepository.findAll().size();

        // Create the Comment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommentMockMvc.perform(put("/api/comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comment)))
            .andExpect(status().isCreated());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);
        int databaseSizeBeforeDelete = commentRepository.findAll().size();

        // Get the comment
        restCommentMockMvc.perform(delete("/api/comments/{id}", comment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comment.class);
    }
}
