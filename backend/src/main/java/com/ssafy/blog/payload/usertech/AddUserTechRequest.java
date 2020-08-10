package com.ssafy.blog.payload.usertech;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserTechRequest {
    
    private Long user_id;
    private Long tech_id;
}