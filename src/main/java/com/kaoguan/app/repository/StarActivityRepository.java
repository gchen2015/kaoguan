package com.kaoguan.app.repository;

import com.kaoguan.app.domain.StarActivity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the StarActivity entity.
 */
@SuppressWarnings("unused")
public interface StarActivityRepository extends JpaRepository<StarActivity,Long> {

    @Query("select starActivity from StarActivity starActivity where starActivity.user.login = ?#{principal.username}")
    List<StarActivity> findByUserIsCurrentUser();

}
