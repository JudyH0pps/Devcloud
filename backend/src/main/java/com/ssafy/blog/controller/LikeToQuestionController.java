package com.ssafy.blog.controller;

import java.util.Optional;

import com.ssafy.blog.model.LikeToQuestion;
import com.ssafy.blog.model.Question;
import com.ssafy.blog.model.Rank;
import com.ssafy.blog.model.User;
import com.ssafy.blog.payload.like.AddLikeToQuestionRequest;
import com.ssafy.blog.payload.like.LikeToQuestionResponse;
import com.ssafy.blog.repository.LikeToQuestionRepository;
import com.ssafy.blog.repository.QuestionRepository;
import com.ssafy.blog.repository.RankRepository;
import com.ssafy.blog.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> searchLikeToQuestion(
            @RequestParam("question_id") Long questionId,
            @RequestParam("user_id") Long userId) {
        Optional<LikeToQuestion> optionalLTQ = likeToQuestionRepository.findByUserIdAndQuestionId(userId, questionId);
        if(optionalLTQ.isPresent()) {
            LikeToQuestionResponse response = makeResponse(optionalLTQ.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("Resource not bound", HttpStatus.OK);
    }

    @PostMapping("/api/liketoquestion")
    @ApiOperation(value = "질문에 대한 좋아요: 없으면 추가, 있으면 삭제 (toggle)")
    public ResponseEntity<String> addLikeToQuestion(@RequestBody AddLikeToQuestionRequest request) {
        Optional<User> optionalUser = userRepository.findById(request.getUserId());
        if (!optionalUser.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<Question> optionalQuestion = questionRepository.findById(request.getQuestionId());
        if (!optionalQuestion.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<LikeToQuestion> optionalLikeToQuestion = likeToQuestionRepository
                .findByUserIdAndQuestionId(request.getUserId(), request.getQuestionId());
        LikeToQuestion likeToQuestion = null;
        if (optionalLikeToQuestion.isPresent()) {
            // 있으면 삭제
            likeToQuestion = optionalLikeToQuestion.get();
            likeToQuestionRepository.delete(likeToQuestion);

            // 좋아요 숫자 반영
            Question question = likeToQuestion.getQuestion();
            question.setLikeCnt(question.getLikeCnt() - 1);
            questionRepository.save(question);

            // rank 반영
            updateRank(question.getUser().getId(), -1);
            return new ResponseEntity<>("delete", HttpStatus.OK);
        } else {
            // 없으면 추가
            likeToQuestion = new LikeToQuestion();
            likeToQuestion.setUser(optionalUser.get());
            likeToQuestion.setQuestion(optionalQuestion.get());
            likeToQuestionRepository.save(likeToQuestion);

            // 좋아요 숫자 반영
            Question question = optionalQuestion.get();
            question.setLikeCnt(question.getLikeCnt() + 1);
            questionRepository.save(question);

            // rank 반영
            updateRank(question.getUser().getId(), 1);
            return new ResponseEntity<>("add", HttpStatus.OK);
        }
    }

    private void updateRank(Long userId, int step) {
        Optional<Rank> optionalRank = rankRepository.findByUserId(userId);
        if(optionalRank.isPresent()) {
            Rank rank = optionalRank.get();
            rank.setLikeCnt(rank.getLikeCnt() + step);
            rankRepository.save(rank);
        }
    }

    private LikeToQuestionResponse makeResponse(LikeToQuestion likeToQuestion) {
        LikeToQuestionResponse response = new LikeToQuestionResponse();
        response.setId(likeToQuestion.getId());
        response.setUserId(likeToQuestion.getUser().getId());
        response.setQuestionId(likeToQuestion.getQuestion().getId());
        return response;
    }
}
