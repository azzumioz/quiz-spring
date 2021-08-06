package com.example.quiz.auth.repository;

import com.example.quiz.auth.entity.Activity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Activity a SET a.activated = :active WHERE a.uuid=:uuid")
    int updateActivated(@Param("uuid") String uuid, @Param("active") boolean active);

    Optional<Activity> findByUser_Id(int id);
    Optional<Activity> findByUuid(String uuid);

}
