package com.ssafy.blog.repository;

import java.util.Optional;

import com.ssafy.blog.model.UserProfile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByUserId(Long id);
}