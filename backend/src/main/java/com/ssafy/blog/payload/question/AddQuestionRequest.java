package com.ssafy.blog.payload.question;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddQuestionRequest {

    private Long user_id;
    private String title;
    private String content;

}