package com.ssafy.blog.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.blog.model.UserTech;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTechRepository extends JpaRepository<UserTech, Long> {

    List<UserTech> findByUserId(Long user_id);

    Optional<UserTech> findByUserIdAndTechId(Long userId, Long techId);
}