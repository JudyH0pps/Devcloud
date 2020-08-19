package com.ssafy.blog.payload.answer;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddAnswerRequest {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("question_id")
    private Long questionId;
    
    private String content;

}