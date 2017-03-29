package com.kaoguan.app.repository;

import com.kaoguan.app.domain.JoinActivity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the JoinActivity entity.
 */
@SuppressWarnings("unused")
public interface JoinActivityRepository extends JpaRepository<JoinActivity,Long> {

    @Query("select joinActivity from JoinActivity joinActivity where joinActivity.joinUser.login = ?#{principal.username}")
    List<JoinActivity> findByJoinUserIsCurrentUser();

}
