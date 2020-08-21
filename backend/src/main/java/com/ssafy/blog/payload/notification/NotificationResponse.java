package com.ssafy.blog.payload.notification;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponse {
    
    @JsonProperty("notification_id")
    private Long notificationId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("question_id")
    private Long questionId;
    private String content;

    @JsonProperty("is_read")
    private Boolean isRead;

    @JsonProperty("created_at")
    private Date createdAt;
}