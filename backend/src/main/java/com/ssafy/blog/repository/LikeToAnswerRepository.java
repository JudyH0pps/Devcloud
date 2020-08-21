package com.ssafy.blog.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.blog.model.LikeToAnswer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeToAnswerRepository extends JpaRepository<LikeToAnswer, Long> {

    List<LikeToAnswer> findAllByUserId(Long userId);

    List<LikeToAnswer> findAllByAnswerId(Long answerId);

    Optional<LikeToAnswer> findByUserIdAndAnswerId(Long userId, Long answerId);
}