package com.ssafy.blog.payload.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyCommentRequest {
    
    private Long comment_id;
    private String content;
}