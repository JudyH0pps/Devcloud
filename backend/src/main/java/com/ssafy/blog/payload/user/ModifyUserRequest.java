package com.ssafy.blog.payload.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.blog.model.Tech;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyUserRequest {

    @JsonProperty("user_id")
    private Long userId;
    private String introduction;
    private String githubUrl;
    private List<Tech> techList;
}