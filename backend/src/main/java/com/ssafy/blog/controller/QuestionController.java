package com.ssafy.blog.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ssafy.blog.model.Question;
import com.ssafy.blog.model.QuestionTag;
import com.ssafy.blog.model.Rank;
import com.ssafy.blog.model.Tag;
import com.ssafy.blog.model.User;
import com.ssafy.blog.payload.question.AddQuestionRequest;
import com.ssafy.blog.payload.question.UpdateQuestionRequest;
import com.ssafy.blog.repository.QuestionRepository;
import com.ssafy.blog.repository.QuestionTagRepository;
import com.ssafy.blog.repository.RankRepository;
import com.ssafy.blog.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Autowired
    private QuestionTagRepository questionTagRepository;

    private ResponseEntity<Question> badResponse = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    @GetMapping("/api/question")
    @ApiOperation(value = "질문 검색")
    // paging 기법 적용하기
    public ResponseEntity<Object> searchQuestion(@RequestParam(required = false, name = "keyword") String keyword,
            @RequestParam(required = false, name = "user_id") Long user_id,
            @RequestParam(required = false, name = "question_id") Long question_id,
            @RequestParam(required = false, name = "page") Integer page) {
        Page<Question> pageList = null;
        if (page == null)
            page = 1;

        if (keyword == null && user_id == null && question_id == null) {
            pageList = questionRepository.findAll(PageRequest.of(page - 1, 10));

        } else if (keyword != null) {
            pageList = questionRepository.findByTitleContainsOrContentContains(keyword, keyword,
                    PageRequest.of(page - 1, 10));

        } else if (user_id != null) {
            pageList = questionRepository.findAllByUserId(user_id, PageRequest.of(page - 1, 10));

        } else if (question_id != null) {
            Optional<Question> optionalQuestion = questionRepository.findById(question_id);
            if (!optionalQuestion.isPresent())
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            // view cnt + 1
            Question question = optionalQuestion.get();
            question.setViewCnt(question.getViewCnt() + 1);
            question = questionRepository.save(question);
            return new ResponseEntity<>(question, HttpStatus.OK);
        }

        return new ResponseEntity<>(pageList, HttpStatus.OK);
    }

    @PostMapping("/api/question")
    @ApiOperation(value = "질문 등록")
    public ResponseEntity<Question> addQuestion(@RequestBody AddQuestionRequest request) {
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

        // tag 등록
        if (request.getTagList() != null) {
            List<Tag> tagList = request.getTagList();
            for (Tag tag : tagList) {
                QuestionTag questionTag = new QuestionTag();
                questionTag.setQuestion(question);
                questionTag.setTag(tag);
                questionTagRepository.save(questionTag);
            }
        }

        updateRank(request.getUser_id(), 1);

        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @PutMapping("/api/question")
    @ApiOperation(value = "질문 수정")
    public ResponseEntity<Question> modifyQuestion(@RequestBody UpdateQuestionRequest request) {
        Optional<Question> optionalQuestion = questionRepository.findById(request.getQuestion_id());
        if (!optionalQuestion.isPresent())
            return badResponse;

        Question question = optionalQuestion.get();
        question.setTitle(request.getTitle());
        question.setContent(request.getContent());
        question.setViewCnt(question.getViewCnt() + 1);
        question = questionRepository.save(question);

        // tag 수정
        questionTagRepository.deleteAllByQuestionId(request.getQuestion_id());
        List<Tag> tagList = request.getTagList();
        for (Tag tag : tagList) {
            QuestionTag questionTag = new QuestionTag();
            questionTag.setQuestion(question);
            questionTag.setTag(tag);
            questionTagRepository.save(questionTag);
        }

        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @DeleteMapping("/api/question")
    @ApiOperation(value = "질문 삭제")
    public ResponseEntity<String> deleteQuestion(@RequestParam("question_id") Long question_id) {
        Optional<Question> optionalQuestion = questionRepository.findById(question_id);
        if (!optionalQuestion.isPresent())
            return new ResponseEntity<>("not exist", HttpStatus.OK);

        Long user_id = optionalQuestion.get().getUser().getId();
        questionRepository.deleteById(question_id);

        updateRank(user_id, -1);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/api/question/tag")
    @ApiOperation(value = "질문 태그로 검색")
    public ResponseEntity<Object> searchQuestionByTag(@RequestParam(required = true, name = "tag_id") Long tag_id,
            @RequestParam(required = false, name = "page") Integer page) {
        if (page == null)
            page = 1;

        Page<Question> list = questionRepository.findAllByQuestionTags_TagId(tag_id, PageRequest.of(page - 1, 10));

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    private void updateRank(Long user_id, int step) {
        Optional<Rank> optionalRank = rankRepository.findByUserId(user_id);
        Rank rank = optionalRank.get();
        rank.setQuestionCnt(rank.getQuestionCnt() + step);
        rankRepository.save(rank);
    }
}