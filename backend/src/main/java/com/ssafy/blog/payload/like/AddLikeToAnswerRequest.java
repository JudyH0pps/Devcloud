package com.ssafy.blog.payload.like;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddLikeToAnswerRequest {
    
    private Long user_id;
    private Long answer_id;
}