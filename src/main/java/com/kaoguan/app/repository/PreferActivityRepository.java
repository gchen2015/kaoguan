package com.kaoguan.app.repository;

import com.kaoguan.app.domain.PreferActivity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PreferActivity entity.
 */
@SuppressWarnings("unused")
public interface PreferActivityRepository extends JpaRepository<PreferActivity,Long> {

    @Query("select preferActivity from PreferActivity preferActivity where preferActivity.perferUser.login = ?#{principal.username}")
    List<PreferActivity> findByPerferUserIsCurrentUser();

}
