package com.ssafy.blog.payload.rank;

import com.ssafy.blog.model.Rank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankResponse {
    
    private Rank rank;
    private int ranking;
}