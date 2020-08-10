package com.ssafy.blog.payload.user;

import java.util.List;

import com.ssafy.blog.model.Tech;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyUserRequest {

    private Long user_id;
    private String introduction;
    private String githubUrl;
    private List<Tech> techList;
}