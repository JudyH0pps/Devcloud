package com.ssafy.blog.payload.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {
    
    private Long id;
    private Long user_id;
    private String title;
    private String content;
}