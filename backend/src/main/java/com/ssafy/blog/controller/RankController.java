package com.ssafy.blog.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ssafy.blog.model.Rank;
import com.ssafy.blog.model.RankCache;
import com.ssafy.blog.model.RankRecord;
import com.ssafy.blog.payload.rank.RankResponse;
import com.ssafy.blog.repository.RankCacheRepository;
import com.ssafy.blog.repository.RankRecordRepository;
import com.ssafy.blog.repository.RankRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class RankController {

    @Autowired
    private RankRepository rankRepository;

    @Autowired
    private RankCacheRepository rankCacheRepository;

    @Autowired
    private RankRecordRepository rankRecordRepository;

    @GetMapping("/api/rank")
    @ApiOperation(value = "랭크 조회")
    public ResponseEntity<RankResponse> searchRank(@RequestParam("user_id") Long user_id) {
        Optional<Rank> optionalRank = rankRepository.findByUserId(user_id);
        if (!optionalRank.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<RankCache> optionalRankCache = rankCacheRepository.findByUserId(user_id);
        int ranking = 0;
        if (optionalRankCache.isPresent())
            ranking = optionalRankCache.get().getRanking();

        RankResponse rankResponse = new RankResponse();
        rankResponse.setRank(optionalRank.get());
        rankResponse.setRanking(ranking);

        return new ResponseEntity<>(rankResponse, HttpStatus.OK);
    }

    @GetMapping("/api/rankupdate")
    @ApiOperation(value = "랭크 업데이트")
    public ResponseEntity<String> updateRank() {
        List<Rank> rankList = rankRepository.findAll();
        for (Rank rank : rankList) {
            rank.setRankPoint(
                    rank.getQuestionCnt() + rank.getAnswerCnt() + rank.getLikeCnt() + rank.getSelectCnt() * 10);
        }
        Collections.sort(rankList, new Comparator<Rank>() {
            @Override
            public int compare(Rank o1, Rank o2) {
                return o2.getRankPoint() - o1.getRankPoint();
            }
        });

        for (int i = 0; i < rankList.size(); i++) {
            Rank rank = rankList.get(i);
            RankCache rankCache = null;
            Optional<RankCache> optionalRankCache = rankCacheRepository.findByUserId(rank.getUser().getId());
            if (!optionalRankCache.isPresent()) {
                rankCache = new RankCache();
                rankCache.setUser(rank.getUser());
                rankCache.setRanking(i + 1);
            } else {
                rankCache = optionalRankCache.get();
                rankCache.setRanking(i + 1);
            }
            rankCacheRepository.save(rankCache);
        }

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/api/rank/record")
    @ApiOperation(value = "랭크 기록 조회")
    public ResponseEntity<List<RankRecord>> searchRankRecord(
            @RequestParam(required = false, name = "user_id") Long user_id) {
        List<RankRecord> list = null;
        
        if(user_id != null) {
            list = rankRecordRepository.findByUserId(user_id);

        } else {
            list = rankRecordRepository.findAll();

        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/api/rank/recordadd")
    @ApiOperation(value = "랭크 기록 추가")
    public ResponseEntity<String> addRankRecord() {
        Date now = new Date();
        List<Rank> rankList = rankRepository.findAll();
        for(Rank rank : rankList) {
            RankRecord rankRecord = new RankRecord();
            rankRecord.setUser(rank.getUser());

            RankCache rankCache = rankCacheRepository.findByUserId(rank.getUser().getId()).get();
            rankRecord.setRanking(rankCache.getRanking());
            rankRecord.setRankPoint(rank.getRankPoint());
            rankRecord.setCreatedAt(now);
            
            rankRecordRepository.save(rankRecord);
        }

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}