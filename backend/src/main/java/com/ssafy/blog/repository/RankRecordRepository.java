package com.ssafy.blog.repository;

import java.util.List;

import com.ssafy.blog.model.RankRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRecordRepository extends JpaRepository<RankRecord, Long> {

    List<RankRecord> findByUserId(Long user_id);
}