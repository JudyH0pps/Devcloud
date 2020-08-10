package com.ssafy.blog.repository;

import java.util.List;

import com.ssafy.blog.model.QuestionTag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionTagRepository extends JpaRepository<QuestionTag, Long> {

    List<QuestionTag> findAllByQuestionId(Long question_id);

    void deleteAllByQuestionId(Long user_id);
}