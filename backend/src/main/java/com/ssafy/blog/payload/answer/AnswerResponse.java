package com.ssafy.blog.payload.answer;

import java.util.Date;

import com.ssafy.blog.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerResponse {

    private Long id;
    private User user;
    private Long question_id;
    private String content;
    private int like_cnt;
    private boolean selected;
    private Date updated_at;
}