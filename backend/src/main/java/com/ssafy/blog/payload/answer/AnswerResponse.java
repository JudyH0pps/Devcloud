package com.ssafy.blog.payload.answer;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.blog.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerResponse {

    private Long id;
    private User user;

    @JsonProperty("question_id")
    private Long questionId;

    @JsonProperty("question_title")
    private String questionTitle;
    private String content;
    
    @JsonProperty("like_cnt")
    private int likeCnt;
    private boolean selected;

    @JsonProperty("updated_at")
    private Date updatedAt;
}