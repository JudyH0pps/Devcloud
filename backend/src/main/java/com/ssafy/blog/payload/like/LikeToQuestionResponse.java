package com.ssafy.blog.payload.like;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeToQuestionResponse {
    
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("question_id")
    private Long questionId;
}