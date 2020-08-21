package com.ssafy.blog.payload.question;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.blog.model.Tag;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddQuestionRequest {

    @JsonProperty("user_id")
    private Long userId;
    private String title;
    private String content;
    private List<Tag> tagList;
}