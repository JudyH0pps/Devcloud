package com.ssafy.blog.payload.like;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeToQuestionResponse {
    
    private Long id;
    private Long user_id;
    private Long question_id;
}