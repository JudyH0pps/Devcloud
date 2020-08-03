package com.ssafy.blog.payload.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.blog.model.Question;
import com.ssafy.blog.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAnswerRequest {
    
    private Long id;
    private Long user_id;
    private Long question_id;
    private String content;

    @JsonIgnore
    public User getUser() {
        User user = new User();
        user.setId(user_id);
        return user;
    }

    @JsonIgnore
    public Question getQuestion() {
        Question question = new Question();
        question.setId(question_id);
        return question;
    }
}