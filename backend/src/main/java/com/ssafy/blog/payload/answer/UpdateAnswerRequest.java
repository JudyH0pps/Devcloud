package com.ssafy.blog.payload.answer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAnswerRequest {

    private Long answer_id;
    private String content;

}