package com.ssafy.blog.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.blog.model.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByTitleContainsOrContentContains(String keyword1, String keyword2);

    List<Question> findAllByUserId(Long userId);

    Optional<Question> findById(Long id);
}