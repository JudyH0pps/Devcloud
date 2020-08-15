package com.ssafy.blog.repository;

import java.util.List;

import com.ssafy.blog.model.QuestionTag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface QuestionTagRepository extends JpaRepository<QuestionTag, Long> {

    List<QuestionTag> findAllByQuestionId(Long question_id);

    @Transactional
    void deleteAllByQuestionId(Long user_id);
}