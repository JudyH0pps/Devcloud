package com.ssafy.blog.repository;

import java.util.List;

import com.ssafy.blog.model.Notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{
    
    List<Notification> findAllByUserId(Long userId);

    List<Notification> findAllByUserIdAndIsRead(Long userId, Boolean isRead);
}