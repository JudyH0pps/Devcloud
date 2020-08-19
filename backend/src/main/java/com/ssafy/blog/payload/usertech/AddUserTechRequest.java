package com.ssafy.blog.payload.usertech;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserTechRequest {
    
    @JsonProperty("user_id")
    private Long userId;

    
    @JsonProperty("tech_id")
    private Long techId;
}