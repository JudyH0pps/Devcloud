package com.ssafy.blog.payload.comment;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCommentRequest {
    
    @JsonProperty("user_id")
    private Long userId;
    
    @JsonProperty("answer_id")
    private Long answerId;
    private String content;
}