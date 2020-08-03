package com.ssafy.blog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ssafy.blog.model.Answer;
import com.ssafy.blog.payload.answer.AddAnswerRequest;
import com.ssafy.blog.payload.answer.AnswerResponse;
import com.ssafy.blog.payload.answer.ReadAnswerResponse;
import com.ssafy.blog.payload.answer.UpdateAnswerRequest;
import com.ssafy.blog.repository.AnswerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class AnswerController {
    
    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/api/answers")
    @ApiOperation(value = "답변 검색")
    public ResponseEntity<List<ReadAnswerResponse>> read(@RequestParam(required = false) Long user_id,
            @RequestParam(required = false) Long question_id) {
        List<ReadAnswerResponse> list = new ArrayList<>();

        if(user_id == null && question_id == null) {
            for(Answer answer : answerRepository.findAll()) {
                list.add(new ReadAnswerResponse(
                    answer.getId(), 
                    answer.getUser(), 
                    answer.getQuestion(), 
                    answer.getContent())
                );
            }
        } else if(user_id != null) {
            for(Answer answer : answerRepository.findAllByUserId(user_id)) {
                list.add(new ReadAnswerResponse(
                    answer.getId(), 
                    answer.getUser(), 
                    answer.getQuestion(), 
                    answer.getContent())
                );
            }
        } else if(question_id != null) {
            for(Answer answer : answerRepository.findAllByQuestionId(question_id)) {
                list.add(new ReadAnswerResponse(
                    answer.getId(), 
                    answer.getUser(), 
                    answer.getQuestion(), 
                    answer.getContent())
                );
            }
        }

        ResponseEntity<List<ReadAnswerResponse>> responseEntity = new ResponseEntity<>(list, HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping("/api/answers")
    @ApiOperation(value = "답변 등록")
    public ResponseEntity<AnswerResponse> create(@RequestBody AddAnswerRequest request) {
        Answer answer = new Answer(
            (long) 0, 
            request.getUser(),
            request.getQuestion(),
            request.getContent()
        );
        answer = answerRepository.save(answer);

        AnswerResponse response = new AnswerResponse(
            answer.getId(), 
            answer.getUser().getId(), 
            answer.getQuestion().getId(), 
            answer.getContent()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/api/answers")
    @ApiOperation(value = "답변 수정")
    public ResponseEntity<AnswerResponse> update(@RequestBody UpdateAnswerRequest request) {
        Answer answer = new Answer(
            request.getId(), 
            request.getUser(),
            request.getQuestion(),
            request.getContent()
        );
        answer = answerRepository.save(answer);

        AnswerResponse response = new AnswerResponse(
            answer.getId(), 
            answer.getUser().getId(), 
            answer.getQuestion().getId(), 
            answer.getContent()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/answers")
    @ApiOperation(value = "답변 삭제")
    public ResponseEntity<String> delete(@RequestParam(required = true) Long id) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if(optionalAnswer.isPresent()) {
            answerRepository.deleteById(id);
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);
    }
}