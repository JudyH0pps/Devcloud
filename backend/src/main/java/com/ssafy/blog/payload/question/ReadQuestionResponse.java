package com.ssafy.blog.payload.question;

import com.ssafy.blog.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadQuestionResponse {
    
    private Long id;
    private User user;
    private String title;
    private String content;
}