package com.ssafy.blog.payload.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.blog.model.Question;
import com.ssafy.blog.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddAnswerRequest {
    
    private Long user_id;
    private Long question_id;
    private String content;

}