package com.ssafy.blog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ssafy.blog.exception.BadRequestException;
import com.ssafy.blog.model.Question;
import com.ssafy.blog.payload.question.AddQuestionRequest;
import com.ssafy.blog.payload.question.QuestionResponse;
import com.ssafy.blog.payload.question.ReadQuestionResponse;
import com.ssafy.blog.payload.question.UpdateQuestionRequest;
import com.ssafy.blog.repository.QuestionRepository;
import com.ssafy.blog.util.ParseNumber;

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

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/api/questions")
    @ApiOperation(value = "질문 검색")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "keyword", value = "검색어", required = false),
        @ApiImplicitParam(name = "user_id", value = "user id", required = false),
        @ApiImplicitParam(name = "id", value = "question id", required = false)
    })
    public ResponseEntity<List<ReadQuestionResponse>> read(@RequestParam(required = false, name = "keyword") String keyword, 
            @RequestParam(required = false, name = "user_id") String user_id, @RequestParam(required = false, name = "id") String id) {

        List<ReadQuestionResponse> list = new ArrayList<>();
        if(keyword == null && user_id == null && id == null) {
            for (Question question : questionRepository.findAll()) {
                list.add(new ReadQuestionResponse(
                    question.getId(), 
                    question.getUser(), 
                    question.getTitle(),
                    question.getContent())
                );
            }
        } else if (keyword != null) {
            for (Question question : questionRepository.findByTitleContainsOrContentContains(keyword, keyword)) {
                list.add(new ReadQuestionResponse(
                    question.getId(), 
                    question.getUser(), 
                    question.getTitle(),
                    question.getContent())
                );
            }
        } else if (user_id != null) {
            for (Question question : questionRepository.findAllByUserId(Long.parseLong(user_id))) {
                list.add(new ReadQuestionResponse(
                    question.getId(), 
                    question.getUser(), 
                    question.getTitle(),
                    question.getContent())
                );
            }
        } else if (id != null) {
            list = new ArrayList<>();
            if (!ParseNumber.isParseLong(id)) throw new BadRequestException("id is not number");
            Optional<Question> optionalQuestion = questionRepository.findById(Long.parseLong(id));
            if (optionalQuestion.isPresent()) {
                Question question = optionalQuestion.get();
                list.add(new ReadQuestionResponse(
                    question.getId(), 
                    question.getUser(), 
                    question.getTitle(),
                    question.getContent())
                );
            }
        }

        ResponseEntity<List<ReadQuestionResponse>> responseEntity = new ResponseEntity<>(list, HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping("/api/questions")
    @ApiOperation(value = "질문 등록")
    public ResponseEntity<QuestionResponse> create(@RequestBody AddQuestionRequest request) {
        Question question = new Question(
            (long) 0, 
            request.getTitle(), 
            request.getContent(), 
            request.getUser()
        );
        question = questionRepository.save(question);

        QuestionResponse response = new QuestionResponse(
            question.getId(), 
            question.getUser().getId(),
            question.getTitle(), 
            question.getContent()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/api/questions")
    @ApiOperation(value = "질문 수정")
    public ResponseEntity<QuestionResponse> update(@RequestBody UpdateQuestionRequest request) {
        Question question = new Question(
            request.getId(), 
            request.getTitle(), 
            request.getContent(), 
            request.getUser()
        );
        question = questionRepository.save(question);
        System.out.println(question);
        QuestionResponse response = new QuestionResponse(
            question.getId(), 
            question.getUser().getId(),
            question.getTitle(),
            question.getContent()
        );
        System.out.println(response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/questions")
    @ApiOperation(value = "질문 삭제")
    public ResponseEntity<String> delete(@RequestParam(required = true) Long id) {
        Optional<Question> optionQuestion = questionRepository.findById(id);
        if (optionQuestion.isPresent()) {
            questionRepository.deleteById(id);
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);
    }
}