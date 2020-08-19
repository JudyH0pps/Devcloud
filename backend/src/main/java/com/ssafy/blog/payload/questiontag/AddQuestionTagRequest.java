package com.ssafy.blog.payload.questiontag;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddQuestionTagRequest {
    
    @JsonProperty("question_id")
    private Long questionId;

    @JsonProperty("tag_id")
    private Long tagId;
}