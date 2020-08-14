package com.ssafy.blog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ssafy.blog.model.Answer;
import com.ssafy.blog.model.LikeToAnswer;
import com.ssafy.blog.model.Rank;
import com.ssafy.blog.model.User;
import com.ssafy.blog.payload.like.AddLikeToAnswerRequest;
import com.ssafy.blog.payload.like.LikeToAnswerResponse;
import com.ssafy.blog.repository.AnswerRepository;
import com.ssafy.blog.repository.LikeToAnswerRepository;
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
public class LikeToAnswerController {

    @Autowired
    private LikeToAnswerRepository likeToAnswerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private RankRepository rankRepository;

    @GetMapping("/api/liketoanswer")
    @ApiOperation(value = "답변에 대한 좋아요 목록 조회")
    public ResponseEntity<Object> searchLikeToAnswer(
            @RequestParam(required = false, name = "user_id") Long user_id,
            @RequestParam(required = false, name = "answer_id") Long answer_id) {
        List<LikeToAnswerResponse> list = new ArrayList<>();
        
        if(user_id != null && answer_id != null) {
            Optional<LikeToAnswer> optionalLikeToAnswer = likeToAnswerRepository.findByUserIdAndAnswerId(user_id, answer_id);
            return new ResponseEntity<>(optionalLikeToAnswer.get(), HttpStatus.OK);
        }
        if (user_id != null && answer_id == null) {
            for (LikeToAnswer likeToAnswer : likeToAnswerRepository.findAllByUserId(user_id)) {
                list.add(makeResponse(likeToAnswer));
            }

        } else if (user_id == null && answer_id != null) {
            for (LikeToAnswer likeToAnswer : likeToAnswerRepository.findAllByAnswerId(answer_id)) {
                list.add(makeResponse(likeToAnswer));
            }

        } else {
            for (LikeToAnswer likeToAnswer : likeToAnswerRepository.findAll()) {
                list.add(makeResponse(likeToAnswer));
            }
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/api/liketoanswer")
    @ApiOperation(value = "답변에 대한 좋아요: 없으면 추가, 있으면 삭제(toggle)")
    public ResponseEntity<String> addLikeToAnswer(@RequestBody AddLikeToAnswerRequest request) {
        Optional<User> optionalUser = userRepository.findById(request.getUser_id());
        if (!optionalUser.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Optional<Answer> optionalAnswer = answerRepository.findById(request.getAnswer_id());
        if (!optionalAnswer.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        LikeToAnswer likeToAnswer = null;
        Optional<LikeToAnswer> optionalLikeToAnswer = likeToAnswerRepository
                .findByUserIdAndAnswerId(request.getUser_id(), request.getAnswer_id());
        if (optionalLikeToAnswer.isPresent()) {
            likeToAnswer = optionalLikeToAnswer.get();
            likeToAnswerRepository.delete(likeToAnswer);

            // 좋아요 숫자 반영
            Answer answer = likeToAnswer.getAnswer();
            answer.setLikeCnt(answer.getLikeCnt() - 1);
            answerRepository.save(answer);

            // rank 반영
            updateRank(answer.getUser().getId(), -1);
            return new ResponseEntity<>("delete", HttpStatus.OK);
        } else {
            likeToAnswer = new LikeToAnswer();
            likeToAnswer.setUser(optionalUser.get());
            likeToAnswer.setAnswer(optionalAnswer.get());
            likeToAnswer = likeToAnswerRepository.save(likeToAnswer);

            // 좋아요 숫자 반영
            Answer answer = optionalAnswer.get();
            answer.setLikeCnt(answer.getLikeCnt() + 1);
            answerRepository.save(answer);

            // rank 반영
            updateRank(answer.getUser().getId(), 1);
            return new ResponseEntity<>("add", HttpStatus.OK);
        }
    }

    private void updateRank(Long user_id, int step) {
        Optional<Rank> optionalRank = rankRepository.findByUserId(user_id);
        Rank rank = optionalRank.get();
        rank.setLikeCnt(rank.getLikeCnt() + step);
        rankRepository.save(rank);
    }

    private LikeToAnswerResponse makeResponse(LikeToAnswer likeToAnswer) {
        LikeToAnswerResponse response = new LikeToAnswerResponse();
        response.setId(likeToAnswer.getId());
        response.setUser_id(likeToAnswer.getUser().getId());
        response.setAnswer_id(likeToAnswer.getAnswer().getId());
        return response;
    }
}