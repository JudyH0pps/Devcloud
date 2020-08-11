package com.ssafy.blog.payload.question;

import java.util.List;

import com.ssafy.blog.model.Tag;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateQuestionRequest {

    private Long question_id;
    private String title;
    private String content;
    private List<Tag> tagList;
}