package com.ssafy.blog.repository;

import java.util.Optional;

import com.ssafy.blog.model.Rank;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepository extends JpaRepository<Rank, Long> {
    
    Optional<Rank> findByUserId(Long user_id);
}