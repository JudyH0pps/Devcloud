package com.ssafy.blog.payload.tech;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTechRequest {
    
    private Long user_id;
    private Long tech_id;
}