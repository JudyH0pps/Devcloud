package com.ssafy.blog.payload.like;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeToAnswerResponse {
    
    private Long id;
    private Long user_id;
    private Long answer_id;
}