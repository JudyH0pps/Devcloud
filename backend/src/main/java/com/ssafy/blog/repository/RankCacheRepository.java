package com.ssafy.blog.repository;

import java.util.Optional;

import com.ssafy.blog.model.RankCache;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankCacheRepository extends JpaRepository<RankCache, Long> {

    Optional<RankCache> findByUserId(Long user_id);
}