package com.ssafy.blog.controller;

import java.util.List;
import java.util.Optional;

import com.ssafy.blog.model.Question;
import com.ssafy.blog.model.QuestionTag;
import com.ssafy.blog.model.Tag;
import com.ssafy.blog.payload.questiontag.AddQuestionTagRequest;
import com.ssafy.blog.repository.QuestionRepository;
import com.ssafy.blog.repository.QuestionTagRepository;
import com.ssafy.blog.repository.TagRepository;

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
public class QuestionTagController {

    @Autowired
    private QuestionTagRepository questionTagRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/api/questiontag")
    @ApiOperation(value = "질문의 태그목록 조회")
    public ResponseEntity<Object> searchQuestionTag(@RequestParam("question_id") Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        if (!optionalQuestion.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<QuestionTag> list = questionTagRepository.findAllByQuestionId(questionId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/api/questiontag")
    @ApiOperation(value = "질문의 태그목록 추가")
    public ResponseEntity<Object> addQuestionTag(@RequestBody AddQuestionTagRequest request) {
        Optional<Question> optionalQuestion = questionRepository.findById(request.getQuestionId());
        if (!optionalQuestion.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        QuestionTag questionTag = new QuestionTag();
        questionTag.setQuestion(optionalQuestion.get());

        Optional<Tag> optionalTag = tagRepository.findById(request.getTagId());
        if(!optionalTag.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        questionTag.setTag(optionalTag.get());

        questionTag = questionTagRepository.save(questionTag);

        return new ResponseEntity<>(questionTag, HttpStatus.OK);
    }

    @DeleteMapping("/api/questiontag")
    @ApiOperation(value = "질문의 태그목록 삭제")
    public ResponseEntity<Object> deleteQuestionTag(@RequestParam("question_tag_id") Long questionTagId) {
        Optional<QuestionTag> optionalQuestionTag = questionTagRepository.findById(questionTagId);
        if (!optionalQuestionTag.isPresent())
            return new ResponseEntity<>("not exist", HttpStatus.BAD_REQUEST);

        questionTagRepository.delete(optionalQuestionTag.get());

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}