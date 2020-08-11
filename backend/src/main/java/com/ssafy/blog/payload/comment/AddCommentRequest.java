package com.ssafy.blog.payload.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCommentRequest {
    
    private Long user_id;
    private Long answer_id;
    private String content;
}