package com.ssafy.blog.payload.answer;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAnswerRequest {

    @JsonProperty("answer_id")
    private Long answerId;
    private String content;

}