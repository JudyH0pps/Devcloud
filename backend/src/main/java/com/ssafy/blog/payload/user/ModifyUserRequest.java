package com.ssafy.blog.payload.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyUserRequest {

    private Long user_id;
    private String introduction;
    private String githubUrl;
}