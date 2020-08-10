package com.ssafy.blog.payload.question;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateQuestionRequest {

    private Long question_id;
    private String title;
    private String content;

}