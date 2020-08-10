package com.ssafy.blog.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ssafy.blog.model.Answer;
import com.ssafy.blog.model.Question;
import com.ssafy.blog.model.Rank;
import com.ssafy.blog.model.User;
import com.ssafy.blog.payload.answer.AddAnswerRequest;
import com.ssafy.blog.payload.answer.UpdateAnswerRequest;
import com.ssafy.blog.repository.AnswerRepository;
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
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private RankRepository rankRepository;

    private ResponseEntity<Answer> badResponse = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    @GetMapping("/api/answers")
    @ApiOperation(value = "답변 검색")
    public ResponseEntity<List<Answer>> read(@RequestParam(required = false) Long user_id,
            @RequestParam(required = false) Long question_id) {
        List<Answer> list = new ArrayList<>();

        if (user_id == null && question_id == null) {
            list = answerRepository.findAll();

        } else if (user_id != null) {
            list = answerRepository.findAllByUserId(user_id);

        } else if (question_id != null) {
            list = answerRepository.findAllByQuestionId(question_id);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/api/answers")
    @ApiOperation(value = "답변 등록")
    public ResponseEntity<Answer> create(@RequestBody AddAnswerRequest request) {
        // 1. 유저 있는지 찾기
        Optional<User> optionalUser = userRepository.findById(request.getUser_id());
        if (!optionalUser.isPresent())
            return badResponse;

        // 2. 질문 있는지 찾기
        Optional<Question> optionalQuestion = questionRepository.findById(request.getQuestion_id());
        if (!optionalQuestion.isPresent())
            return badResponse;

        // 3. 둘다 있으면 답변 등록
        Answer answer = new Answer();
        answer.setUser(optionalUser.get());
        answer.setQuestion(optionalQuestion.get());
        answer.setContent(request.getContent());
        answer.setLikeCnt(0);
        answer.setSelected(false);
        answer.setUpdatedAt(new Date());
        answer = answerRepository.save(answer);

        // 4. rank cnt
        updateRank(request.getUser_id(), 1);

        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

    @PutMapping("/api/answers")
    @ApiOperation(value = "답변 수정")
    public ResponseEntity<Answer> update(@RequestBody UpdateAnswerRequest request) {
        Optional<Answer> optionalAnswer = answerRepository.findById(request.getAnswer_id());
        if (!optionalAnswer.isPresent())
            return badResponse;

        Answer answer = optionalAnswer.get();
        answer.setContent(request.getContent());
        answer = answerRepository.save(answer);

        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

    @DeleteMapping("/api/answers")
    @ApiOperation(value = "답변 삭제")
    public ResponseEntity<String> delete(@RequestParam("answer_id") Long answer_id) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answer_id);
        if (!optionalAnswer.isPresent())
            return new ResponseEntity<>("not exist", HttpStatus.OK);

        Long user_id = optionalAnswer.get().getUser().getId();
        answerRepository.deleteById(answer_id);

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