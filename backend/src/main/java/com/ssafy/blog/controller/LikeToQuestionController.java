package com.ssafy.blog.controller;

import java.util.List;
import java.util.Optional;

import com.ssafy.blog.model.LikeToQuestion;
import com.ssafy.blog.model.Question;
import com.ssafy.blog.model.Rank;
import com.ssafy.blog.model.User;
import com.ssafy.blog.payload.like.AddLikeToQuestionRequest;
import com.ssafy.blog.repository.LikeToQuestionRepository;
import com.ssafy.blog.repository.QuestionRepository;
import com.ssafy.blog.repository.RankRepository;
import com.ssafy.blog.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class LikeToQuestionController {

    @Autowired
    private LikeToQuestionRepository likeToQuestionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private RankRepository rankRepository;

    @GetMapping("/api/liketoquestion")
    @ApiOperation(value = "질문에 대한 좋아요 목록 조회")
    public ResponseEntity<Object> searchLikeToQuestion(@RequestParam(required = false, name = "user_id") Long user_id,
            @RequestParam(required = false, name = "question_id") Long question_id) {
        List<LikeToQuestion> list = null;
        if (user_id != null) {
            list = likeToQuestionRepository.findAllByUserId(user_id);

        } else if (question_id != null) {
            list = likeToQuestionRepository.findAllByQuestionId(question_id);

        } else {
            list = likeToQuestionRepository.findAll();
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/api/liketoquestion")
    @ApiOperation(value = "질문에 대한 좋아요 추가")
    public ResponseEntity<Object> addLikeToQuestion(@RequestBody AddLikeToQuestionRequest request) {
        Optional<User> optionalUser = userRepository.findById(request.getUser_id());
        if (!optionalUser.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<Question> optionalQuestion = questionRepository.findById(request.getQuestion_id());
        if (!optionalQuestion.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<LikeToQuestion> optionalLikeToQuestion = likeToQuestionRepository
                .findByUserIdAndQuestionId(request.getUser_id(), request.getQuestion_id());
        if (optionalLikeToQuestion.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        LikeToQuestion likeToQuestion = new LikeToQuestion();
        likeToQuestion.setUser(optionalUser.get());
        likeToQuestion.setQuestion(optionalQuestion.get());
        likeToQuestion = likeToQuestionRepository.save(likeToQuestion);

        // 좋아요 숫자 반영
        Question question = optionalQuestion.get();
        question.setLikeCnt(question.getLikeCnt() + 1);
        questionRepository.save(question);

        // rank 반영
        updateRank(question.getUser().getId(), 1);

        return new ResponseEntity<>(likeToQuestion, HttpStatus.OK);
    }

    @DeleteMapping("/api/liketoquestion")
    @ApiOperation(value = "질문에 대한 좋아요 삭제")
    public ResponseEntity<Object> deleleLikeToQuestion(@RequestParam("like_to_question_id") Long like_to_question_id) {
        Optional<LikeToQuestion> optionalLikeToQuestion = likeToQuestionRepository.findById(like_to_question_id);
        if (!optionalLikeToQuestion.isPresent())
            return new ResponseEntity<>("not exist", HttpStatus.OK);
        LikeToQuestion likeToQuestion = optionalLikeToQuestion.get();
        likeToQuestionRepository.delete(likeToQuestion);

        // 좋아요 숫자 반영
        Question question = likeToQuestion.getQuestion();
        question.setLikeCnt(question.getLikeCnt() - 1);
        questionRepository.save(question);

        // rank 반영
        updateRank(question.getUser().getId(), -1);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    private void updateRank(Long user_id, int step) {
        Optional<Rank> optionalRank = rankRepository.findByUserId(user_id);
        Rank rank = optionalRank.get();
        rank.setLikeCnt(rank.getLikeCnt() + step);
        rankRepository.save(rank);
    }
}
