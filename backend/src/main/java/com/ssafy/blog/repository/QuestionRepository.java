package com.ssafy.blog.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.blog.model.Question;
import com.ssafy.blog.model.QuestionTag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findByTitleContainsOrContentContains(String keyword1, String keyword2, Pageable pageRequest);

    Page<Question> findAllByUserId(Long userId, Pageable pageRequest);

    Optional<Question> findById(Long id);

    Page<Question> findAllByQuestionTags_TagId(Long tag_id, Pageable pageRequest);
}