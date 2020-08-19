package com.ssafy.blog.repository;

import java.util.List;

import com.ssafy.blog.model.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByAnswerId(Long answerId);
}