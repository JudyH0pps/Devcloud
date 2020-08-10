package com.ssafy.blog.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ssafy.blog.model.Question;
import com.ssafy.blog.model.Rank;
import com.ssafy.blog.model.User;
import com.ssafy.blog.payload.question.AddQuestionRequest;
import com.ssafy.blog.payload.question.UpdateQuestionRequest;
import com.ssafy.blog.repository.QuestionRepository;
import com.ssafy.blog.repository.RankRepository;
import com.ssafy.blog.repository.UserRepository;

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
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RankRepository rankRepository;

    private ResponseEntity<Question> badResponse = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    @GetMapping("/api/question")
    @ApiOperation(value = "질문 검색")
    public ResponseEntity<List<Question>> searchQuestion(
            @RequestParam(required = false, name = "keyword") String keyword,
            @RequestParam(required = false, name = "user_id") Long user_id,
            @RequestParam(required = false, name = "question_id") Long question_id) {
        List<Question> list = new ArrayList<>();

        if (keyword == null && user_id == null && question_id == null) {
            list = questionRepository.findAll();

        } else if (keyword != null) {
            list = questionRepository.findByTitleContainsOrContentContains(keyword, keyword);

        } else if (user_id != null) {
            list = questionRepository.findAllByUserId(user_id);

        } else if (question_id != null) {
            Optional<Question> optionalQuestion = questionRepository.findById(question_id);
            if (!optionalQuestion.isPresent())
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            // view cnt + 1
            Question question = optionalQuestion.get();
            question.setViewCnt(question.getViewCnt() + 1);
            question = questionRepository.save(question);

            list.add(question);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/api/question")
    @ApiOperation(value = "질문 등록")
    public ResponseEntity<Question> create(@RequestBody AddQuestionRequest request) {
        Question question = new Question();

        Optional<User> optionalUser = userRepository.findById(request.getUser_id());
        if (!optionalUser.isPresent())
            return badResponse;
        question.setUser(optionalUser.get());
        question.setTitle(request.getTitle());
        question.setContent(request.getContent());
        question.setViewCnt(1);
        question.setUpdatedAt(new Date());

        question = questionRepository.save(question);

        updateRank(request.getUser_id(), 1);

        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @PutMapping("/api/question")
    @ApiOperation(value = "질문 수정")
    public ResponseEntity<Question> update(@RequestBody UpdateQuestionRequest request) {
        Optional<Question> optionalQuestion = questionRepository.findById(request.getQuestion_id());
        if (!optionalQuestion.isPresent())
            return badResponse;

        Question question = optionalQuestion.get();
        question.setTitle(request.getTitle());
        question.setContent(request.getContent());
        question.setViewCnt(question.getViewCnt() + 1);
        question = questionRepository.save(question);

        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @DeleteMapping("/api/question")
    @ApiOperation(value = "질문 삭제")
    public ResponseEntity<String> delete(@RequestParam("question_id") Long question_id) {
        Optional<Question> optionalQuestion = questionRepository.findById(question_id);
        if (!optionalQuestion.isPresent())
            return new ResponseEntity<>("not exist", HttpStatus.OK);

        Long user_id = optionalQuestion.get().getUser().getId();
        questionRepository.deleteById(question_id);

        updateRank(user_id, -1);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    // 좋아요 증감 api 만들기

    private void updateRank(Long user_id, int step) {
        Optional<Rank> optionalRank = rankRepository.findByUserId(user_id);
        Rank rank = optionalRank.get();
        rank.setQuestionCnt(rank.getQuestionCnt() + step);
        rankRepository.save(rank);
    }
}