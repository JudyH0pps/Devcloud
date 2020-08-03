package com.ssafy.blog.payload.answer;

import com.ssafy.blog.model.Question;
import com.ssafy.blog.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadAnswerResponse {
    
    private Long id;
    private User user;
    private Question question;
    private String content;
}