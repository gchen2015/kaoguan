package com.kaoguan.app.repository;

import com.kaoguan.app.domain.Activity;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

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

    @Query(value="select * from Activity activity where activity.id >:afterId and (:ageRanger ='' or activity.age_ranger = :ageRanger) and (:city ='' or activity.location_city = :city) LIMIT 10", nativeQuery = true)
    List<Activity> findActivityInfiniteScrollData(@Param("afterId") Long afterId,@Param("ageRanger") String ageRanger,@Param("city") String city) ;

    List<Activity> findFirst10ByAgeRangerAndLocationCity(String ageRanger,String locationCity);

    List<Activity> findFirst10ByAgeRanger(String ageRanger);

    List<Activity> findFirst10ByLocationCity(String cityLocation);

    @Query(value="select * from Activity activity  LIMIT 10", nativeQuery = true)
    List<Activity> findFirst10();

}
