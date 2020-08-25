package com.ssafy.blog.payload.tag;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagCntResponse {
    
    private String name;
    private Integer cnt;
}