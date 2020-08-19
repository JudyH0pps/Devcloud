package com.ssafy.blog.payload.like;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddLikeToAnswerRequest {
    
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("answer_id")
    private Long answerId;
}