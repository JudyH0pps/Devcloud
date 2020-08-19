package com.ssafy.blog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ssafy.blog.model.Notification;
import com.ssafy.blog.model.User;
import com.ssafy.blog.payload.notification.NotificationResponse;
import com.ssafy.blog.repository.NotificationRepository;
import com.ssafy.blog.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class NotificationController {
    
    @Autowired
    private NotificationRepository nr;

    @Autowired
    private UserRepository ur;

    @GetMapping("/api/notification/{user_id}")
    @ApiOperation(value = "유저 아이디 기준 모든 알람을 조회한다")
    public ResponseEntity<Object> searchNotificationByUserId(@PathVariable("user_id") Long userId) {
        List<NotificationResponse> list = new ArrayList<>();
        for(Notification n : nr.findAllByUserIdAndIsRead(userId, false))
            list.add(makeResponse(n));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/api/notification/read/{notification_id}")
    @ApiOperation(value = "알림 아이디로 알림을 조회한다")
    public ResponseEntity<Object> readNotification(@PathVariable("notification_id") Long notificationId) {
        Optional<Notification> optionalNotification = nr.findById(notificationId);
        if(!optionalNotification.isPresent()) 
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Notification n = optionalNotification.get();
        n.setIsRead(true);
        nr.save(n);

        Optional<User> optionalUser = ur.findById(n.getUser().getId());
        if(!optionalUser.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User user = optionalUser.get();
        user.setUnReadNotificationCnt(user.getUnReadNotificationCnt() - 1);
        ur.save(user);
        
        NotificationResponse res = makeResponse(optionalNotification.get());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    private NotificationResponse makeResponse(Notification n) {
        NotificationResponse res = new NotificationResponse();
        res.setNotificationId(n.getId());
        res.setUserId(n.getUser().getId());
        res.setQuestionId(n.getQuestion().getId());
        res.setContent(n.getContent());
        res.setIsRead(n.getIsRead());
        res.setCreatedAt(n.getCreatedAt());
        return res;
    }
}