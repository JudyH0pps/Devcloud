package com.ssafy.blog.payload.answer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddAnswerRequest {

    private Long user_id;
    private Long question_id;
    private String content;

}