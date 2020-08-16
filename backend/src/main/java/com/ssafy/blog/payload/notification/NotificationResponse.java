package com.ssafy.blog.payload.notification;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponse {
    
    private Long notification_id;
    private Long user_id;
    private Long question_id;
    private String content;
    private Boolean is_read;
    private Date created_at;
}