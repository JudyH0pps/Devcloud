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
import com.ssafy.blog.payload.answer.AnswerResponse;
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

    @GetMapping("/api/answer")
    @ApiOperation(value = "답변 검색")
    public ResponseEntity<List<AnswerResponse>> searchAnswer(
            @RequestParam(required = false, name = "user_id") Long user_id,
            @RequestParam(required = false, name = "question_id") Long question_id) {
        List<AnswerResponse> list = new ArrayList<>();

        if (user_id == null && question_id == null) {
            for (Answer answer : answerRepository.findAll())
                list.add(makeAnswerResponse(answer));

        } else if (user_id != null) {
            for (Answer answer : answerRepository.findAllByUserId(user_id))
                list.add(makeAnswerResponse(answer));

        } else if (question_id != null) {
            for (Answer answer : answerRepository.findAllByQuestionId(question_id))
                list.add(makeAnswerResponse(answer));
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/api/answer")
    @ApiOperation(value = "답변 등록")
    public ResponseEntity<AnswerResponse> addAnswer(@RequestBody AddAnswerRequest request) {
        // 1. 유저 있는지 찾기
        Optional<User> optionalUser = userRepository.findById(request.getUser_id());
        if (!optionalUser.isPresent())
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        // 2. 질문 있는지 찾기
        Optional<Question> optionalQuestion = questionRepository.findById(request.getQuestion_id());
        if (!optionalQuestion.isPresent())
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

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

        AnswerResponse response = makeAnswerResponse(answer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/api/answer")
    @ApiOperation(value = "답변 수정")
    public ResponseEntity<AnswerResponse> modifyAnswer(@RequestBody UpdateAnswerRequest request) {
        Optional<Answer> optionalAnswer = answerRepository.findById(request.getAnswer_id());
        if (!optionalAnswer.isPresent())
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        Answer answer = optionalAnswer.get();
        answer.setContent(request.getContent());
        answer.setUpdatedAt(new Date());
        answer = answerRepository.save(answer);

        AnswerResponse response = makeAnswerResponse(answer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/answer")
    @ApiOperation(value = "답변 삭제")
    public ResponseEntity<String> deleteAnswer(@RequestParam("answer_id") Long answer_id) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answer_id);
        if (!optionalAnswer.isPresent())
            return new ResponseEntity<>("not exist", HttpStatus.OK);

        Long user_id = optionalAnswer.get().getUser().getId();
        answerRepository.deleteById(answer_id);

        updateRank(user_id, -1);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/api/answer/select")
    @ApiOperation(value = "답변 채택")
    public ResponseEntity<Answer> selectAnswer(@RequestParam("answer_id") Long answer_id) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answer_id);
        if (!optionalAnswer.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Answer answer = optionalAnswer.get();
        answer.setSelected(true);
        answer = answerRepository.save(answer);

        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

    private void updateRank(Long user_id, int step) {
        Optional<Rank> optionalRank = rankRepository.findByUserId(user_id);
        Rank rank = optionalRank.get();
        rank.setAnswerCnt(rank.getAnswerCnt() + step);
        rankRepository.save(rank);
    }

    private AnswerResponse makeAnswerResponse(Answer answer) {
        AnswerResponse response = new AnswerResponse();
        response.setId(answer.getId());
        response.setUser(answer.getUser());
        response.setQuestion_id(answer.getQuestion().getId());
        response.setQuestion_title(answer.getQuestion().getTitle());
        response.setContent(answer.getContent());
        response.setLike_cnt(answer.getLikeCnt());
        response.setSelected(answer.isSelected());
        response.setUpdated_at(answer.getUpdatedAt());
        return response;
    }
}