package com.kaoguan.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kaoguan.app.domain.PreferActivity;

import com.kaoguan.app.repository.PreferActivityRepository;
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
 * REST controller for managing PreferActivity.
 */
@RestController
@RequestMapping("/api")
public class PreferActivityResource {

    private final Logger log = LoggerFactory.getLogger(PreferActivityResource.class);

    private static final String ENTITY_NAME = "preferActivity";
        
    private final PreferActivityRepository preferActivityRepository;

    public PreferActivityResource(PreferActivityRepository preferActivityRepository) {
        this.preferActivityRepository = preferActivityRepository;
    }

    /**
     * POST  /prefer-activities : Create a new preferActivity.
     *
     * @param preferActivity the preferActivity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new preferActivity, or with status 400 (Bad Request) if the preferActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prefer-activities")
    @Timed
    public ResponseEntity<PreferActivity> createPreferActivity(@RequestBody PreferActivity preferActivity) throws URISyntaxException {
        log.debug("REST request to save PreferActivity : {}", preferActivity);
        if (preferActivity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new preferActivity cannot already have an ID")).body(null);
        }
        PreferActivity result = preferActivityRepository.save(preferActivity);
        return ResponseEntity.created(new URI("/api/prefer-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prefer-activities : Updates an existing preferActivity.
     *
     * @param preferActivity the preferActivity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated preferActivity,
     * or with status 400 (Bad Request) if the preferActivity is not valid,
     * or with status 500 (Internal Server Error) if the preferActivity couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prefer-activities")
    @Timed
    public ResponseEntity<PreferActivity> updatePreferActivity(@RequestBody PreferActivity preferActivity) throws URISyntaxException {
        log.debug("REST request to update PreferActivity : {}", preferActivity);
        if (preferActivity.getId() == null) {
            return createPreferActivity(preferActivity);
        }
        PreferActivity result = preferActivityRepository.save(preferActivity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, preferActivity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prefer-activities : get all the preferActivities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of preferActivities in body
     */
    @GetMapping("/prefer-activities")
    @Timed
    public List<PreferActivity> getAllPreferActivities() {
        log.debug("REST request to get all PreferActivities");
        List<PreferActivity> preferActivities = preferActivityRepository.findAll();
        return preferActivities;
    }

    /**
     * GET  /prefer-activities/:id : get the "id" preferActivity.
     *
     * @param id the id of the preferActivity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the preferActivity, or with status 404 (Not Found)
     */
    @GetMapping("/prefer-activities/{id}")
    @Timed
    public ResponseEntity<PreferActivity> getPreferActivity(@PathVariable Long id) {
        log.debug("REST request to get PreferActivity : {}", id);
        PreferActivity preferActivity = preferActivityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(preferActivity));
    }

    /**
     * DELETE  /prefer-activities/:id : delete the "id" preferActivity.
     *
     * @param id the id of the preferActivity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prefer-activities/{id}")
    @Timed
    public ResponseEntity<Void> deletePreferActivity(@PathVariable Long id) {
        log.debug("REST request to delete PreferActivity : {}", id);
        preferActivityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
