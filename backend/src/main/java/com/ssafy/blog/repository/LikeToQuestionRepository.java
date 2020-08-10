package com.ssafy.blog.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.blog.model.LikeToQuestion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeToQuestionRepository extends JpaRepository<LikeToQuestion, Long> {

    List<LikeToQuestion> findAllByUserId(Long user_id);

    List<LikeToQuestion> findAllByQuestionId(Long question_id);

    Optional<LikeToQuestion> findByUserIdAndQuestionId(Long user_id, Long question_id);
}