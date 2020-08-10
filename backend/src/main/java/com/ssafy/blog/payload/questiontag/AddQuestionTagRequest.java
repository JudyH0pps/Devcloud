package com.ssafy.blog.payload.questiontag;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddQuestionTagRequest {
    
    private Long question_id;
    private Long tag_id;
}