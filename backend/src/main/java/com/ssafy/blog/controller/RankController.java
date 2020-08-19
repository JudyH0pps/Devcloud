package com.ssafy.blog.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ssafy.blog.model.Rank;
import com.ssafy.blog.payload.rank.RankResponse;
import com.ssafy.blog.repository.RankRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class RankController {

    @Autowired
    private RankRepository rankRepository;

    @GetMapping("/api/rank")
    @ApiOperation(value = "랭크 조회")
    public ResponseEntity<List<RankResponse>> searchRank() {
        List<RankResponse> list = new ArrayList<>();
        List<Rank> rlist = rankRepository.findAll();

        for (Rank rank : rlist)
            calculateRanking(rank);
        rlist.sort((r1, r2) -> r2.getRankPoint() - r1.getRankPoint());

        for (int i = 0; i < rlist.size(); i++) {
            Rank rank = rlist.get(i);
            rank.setRanking(i + 1);
            rankRepository.save(rank);
            list.add(makeResponse(rank));
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/api/rank/{user_id}")
    @ApiOperation(value = "유저 아이디로 랭크 조회")
    public ResponseEntity<RankResponse> searchRankByUserId(@PathVariable("user_id") Long userId) {
        Optional<Rank> optionalRank = rankRepository.findByUserId(userId);
        if (!optionalRank.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        RankResponse response = makeResponse(optionalRank.get());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void calculateRanking(Rank rank) {
        rank.setRankPoint((
            rank.getQuestionCnt() + 
            rank.getAnswerCnt() + 
            rank.getLikeCnt() + 
            rank.getSelectCnt() * 10
        ) * 5);
        rank.setUpdatedAt(new Date());
    }

    private RankResponse makeResponse(Rank rank) {
        RankResponse res = new RankResponse();
        res.setRankId(rank.getId());
        res.setUserId(rank.getUser().getId());
        res.setRankPoint(rank.getRankPoint());
        res.setRanking(rank.getRanking());
        res.setQuestionCnt(rank.getQuestionCnt());
        res.setAnswerCnt(rank.getAnswerCnt());
        res.setLikeCnt(rank.getLikeCnt());
        res.setSelectCnt(rank.getSelectCnt());
        res.setUpdatedAt(rank.getUpdatedAt());
        return res;
    }
}