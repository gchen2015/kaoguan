package com.kaoguan.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kaoguan.app.domain.JoinActivity;

import com.kaoguan.app.repository.JoinActivityRepository;
import com.kaoguan.app.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing JoinActivity.
 */
@RestController
@RequestMapping("/api")
public class JoinActivityResource {

    private final Logger log = LoggerFactory.getLogger(JoinActivityResource.class);

    private static final String ENTITY_NAME = "joinActivity";
        
    private final JoinActivityRepository joinActivityRepository;

    public JoinActivityResource(JoinActivityRepository joinActivityRepository) {
        this.joinActivityRepository = joinActivityRepository;
    }

    /**
     * POST  /join-activities : Create a new joinActivity.
     *
     * @param joinActivity the joinActivity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new joinActivity, or with status 400 (Bad Request) if the joinActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/join-activities")
    @Timed
    public ResponseEntity<JoinActivity> createJoinActivity(@RequestBody JoinActivity joinActivity) throws URISyntaxException {
        log.debug("REST request to save JoinActivity : {}", joinActivity);
        if (joinActivity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new joinActivity cannot already have an ID")).body(null);
        }
        JoinActivity result = joinActivityRepository.save(joinActivity);
        return ResponseEntity.created(new URI("/api/join-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /join-activities : Updates an existing joinActivity.
     *
     * @param joinActivity the joinActivity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated joinActivity,
     * or with status 400 (Bad Request) if the joinActivity is not valid,
     * or with status 500 (Internal Server Error) if the joinActivity couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/join-activities")
    @Timed
    public ResponseEntity<JoinActivity> updateJoinActivity(@RequestBody JoinActivity joinActivity) throws URISyntaxException {
        log.debug("REST request to update JoinActivity : {}", joinActivity);
        if (joinActivity.getId() == null) {
            return createJoinActivity(joinActivity);
        }
        JoinActivity result = joinActivityRepository.save(joinActivity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, joinActivity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /join-activities : get all the joinActivities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of joinActivities in body
     */
    @GetMapping("/join-activities")
    @Timed
    public List<JoinActivity> getAllJoinActivities() {
        log.debug("REST request to get all JoinActivities");
        List<JoinActivity> joinActivities = joinActivityRepository.findAll();
        return joinActivities;
    }

    /**
     * GET  /join-activities/:id : get the "id" joinActivity.
     *
     * @param id the id of the joinActivity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the joinActivity, or with status 404 (Not Found)
     */
    @GetMapping("/join-activities/{id}")
    @Timed
    public ResponseEntity<JoinActivity> getJoinActivity(@PathVariable Long id) {
        log.debug("REST request to get JoinActivity : {}", id);
        JoinActivity joinActivity = joinActivityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(joinActivity));
    }

    /**
     * DELETE  /join-activities/:id : delete the "id" joinActivity.
     *
     * @param id the id of the joinActivity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/join-activities/{id}")
    @Timed
    public ResponseEntity<Void> deleteJoinActivity(@PathVariable Long id) {
        log.debug("REST request to delete JoinActivity : {}", id);
        joinActivityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
