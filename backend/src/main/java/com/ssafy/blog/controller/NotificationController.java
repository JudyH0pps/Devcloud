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
    public ResponseEntity<Object> searchNotificationByUserId(@PathVariable("user_id") Long user_id) {
        List<NotificationResponse> list = new ArrayList<>();
        for(Notification n : nr.findAllByUserId(user_id))
            list.add(makeResponse(n));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/api/notification/read/{notification_id}")
    @ApiOperation(value = "알림 아이디로 알림을 조회한다")
    public ResponseEntity<Object> readNotification(@PathVariable("notification_id") Long notification_id) {
        Optional<Notification> optionalNotification = nr.findById(notification_id);
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
        res.setNotification_id(n.getId());
        res.setUser_id(n.getUser().getId());
        res.setQuestion_id(n.getQuestion().getId());
        res.setContent(n.getContent());
        res.setIs_read(n.getIsRead());
        res.setCreated_at(n.getCreatedAt());
        return res;
    }
}