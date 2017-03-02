package com.kaoguan.app.repository;

import com.kaoguan.app.domain.Activity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Activity entity.
 */
@SuppressWarnings("unused")
public interface ActivityRepository extends JpaRepository<Activity,Long> {

    @Query("select activity from Activity activity where activity.creator.login = ?#{principal.username}")
    List<Activity> findByCreatorIsCurrentUser();

    @Query("select activity from Activity activity where activity.updator.login = ?#{principal.username}")
    List<Activity> findByUpdatorIsCurrentUser();

}
