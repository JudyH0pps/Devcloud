package com.ssafy.blog.repository;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import com.ssafy.blog.model.LikeToAnswer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeToAnswerRepository extends JpaRepository<LikeToAnswer, Long> {

    List<LikeToAnswer> findAllByUserId(Long user_id);

    List<LikeToAnswer> findAllByAnswerId(Long answer_id);

    Optional<LikeToAnswer> findByUserIdAndAnswerId(Long user_id, Long answer_id);
}