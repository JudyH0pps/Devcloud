package com.ssafy.blog.payload.rank;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankResponse {
    
    @JsonProperty("rank_id")
    private Long rankId;

    @JsonProperty("user_id")
    private Long userId;
    
    @JsonProperty("rank_point")
    private int rankPoint;

    private int ranking;
    
    @JsonProperty("question_cnt")
    private int questionCnt;
    
    @JsonProperty("answer_cnt")
    private int answerCnt;

    @JsonProperty("like_cnt")
    private int likeCnt;
    
    @JsonProperty("select_cnt")
    private int selectCnt;
    
    @JsonProperty("updated_at")
    private Date updatedAt;
}