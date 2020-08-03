package com.ssafy.blog.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.blog.model.Answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer>findAllByQuestionId(Long question_id);

    List<Answer> findAllByUserId(Long user_id);

    Optional<Answer> findById(Long id);
}