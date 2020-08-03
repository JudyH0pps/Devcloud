package com.ssafy.blog.payload.answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResponse {
    
    private Long id;
    private Long user_id;
    private Long question_id;
    private String content;
}