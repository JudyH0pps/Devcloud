package com.ssafy.blog.payload.rank;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankResponse {
    
    private Long rank_id;
    private Long user_id;
    private int rank_point;
    private int ranking;
    private int question_cnt;
    private int answer_cnt;
    private int like_cnt;
    private int select_cnt;
    private Date updated_at;
}