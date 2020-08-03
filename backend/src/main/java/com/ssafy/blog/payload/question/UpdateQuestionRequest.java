package com.ssafy.blog.payload.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.blog.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateQuestionRequest {
    
    private Long id;
    private Long user_id;
    private String title;
    private String content;

    @JsonIgnore
    public User getUser() {
        User user = new User();
        user.setId(user_id);
        return user;
    }
}