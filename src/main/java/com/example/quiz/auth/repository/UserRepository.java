package com.example.quiz.auth.repository;

import com.example.quiz.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Integer id);

    List<User> findAll();

    @Query("select case when count (u) > 0 then true else false end from User u where lower(u.email) = lower(:email) ")
    boolean existsByEmail(@Param("email") String email);

    @Query("select case when count (u) > 0 then true else false end from User u where lower(u.username) = lower(:username) ")
    boolean existsByUsername(@Param("username") String username);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password = :password WHERE u.username=:username")
    int updatePassword(@Param("password") String password, @Param("username") String username);

}
