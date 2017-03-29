package com.kaoguan.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kaoguan.app.domain.StarActivity;

import com.kaoguan.app.repository.StarActivityRepository;
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
 * REST controller for managing StarActivity.
 */
@RestController
@RequestMapping("/api")
public class StarActivityResource {

    private final Logger log = LoggerFactory.getLogger(StarActivityResource.class);

    private static final String ENTITY_NAME = "starActivity";
        
    private final StarActivityRepository starActivityRepository;

    public StarActivityResource(StarActivityRepository starActivityRepository) {
        this.starActivityRepository = starActivityRepository;
    }

    /**
     * POST  /star-activities : Create a new starActivity.
     *
     * @param starActivity the starActivity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new starActivity, or with status 400 (Bad Request) if the starActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/star-activities")
    @Timed
    public ResponseEntity<StarActivity> createStarActivity(@RequestBody StarActivity starActivity) throws URISyntaxException {
        log.debug("REST request to save StarActivity : {}", starActivity);
        if (starActivity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new starActivity cannot already have an ID")).body(null);
        }
        StarActivity result = starActivityRepository.save(starActivity);
        return ResponseEntity.created(new URI("/api/star-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /star-activities : Updates an existing starActivity.
     *
     * @param starActivity the starActivity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated starActivity,
     * or with status 400 (Bad Request) if the starActivity is not valid,
     * or with status 500 (Internal Server Error) if the starActivity couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/star-activities")
    @Timed
    public ResponseEntity<StarActivity> updateStarActivity(@RequestBody StarActivity starActivity) throws URISyntaxException {
        log.debug("REST request to update StarActivity : {}", starActivity);
        if (starActivity.getId() == null) {
            return createStarActivity(starActivity);
        }
        StarActivity result = starActivityRepository.save(starActivity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, starActivity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /star-activities : get all the starActivities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of starActivities in body
     */
    @GetMapping("/star-activities")
    @Timed
    public List<StarActivity> getAllStarActivities() {
        log.debug("REST request to get all StarActivities");
        List<StarActivity> starActivities = starActivityRepository.findAll();
        return starActivities;
    }

    /**
     * GET  /star-activities/:id : get the "id" starActivity.
     *
     * @param id the id of the starActivity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the starActivity, or with status 404 (Not Found)
     */
    @GetMapping("/star-activities/{id}")
    @Timed
    public ResponseEntity<StarActivity> getStarActivity(@PathVariable Long id) {
        log.debug("REST request to get StarActivity : {}", id);
        StarActivity starActivity = starActivityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(starActivity));
    }

    /**
     * DELETE  /star-activities/:id : delete the "id" starActivity.
     *
     * @param id the id of the starActivity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/star-activities/{id}")
    @Timed
    public ResponseEntity<Void> deleteStarActivity(@PathVariable Long id) {
        log.debug("REST request to delete StarActivity : {}", id);
        starActivityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
