package com.ssafy.blog.repository;

import java.util.Optional;

import com.ssafy.blog.model.Tech;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechRepository extends JpaRepository<Tech, Long> {
    
    Optional<Tech> findByName(String name);
}