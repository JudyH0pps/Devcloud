package com.ssafy.blog.payload.comment;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyCommentRequest {
    
    @JsonProperty("comment_id")
    private Long commentId;
    private String content;
}