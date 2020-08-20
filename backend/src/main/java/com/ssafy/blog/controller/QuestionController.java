package com.ssafy.blog.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ssafy.blog.model.Answer;
import com.ssafy.blog.model.Question;
import com.ssafy.blog.model.QuestionTag;
import com.ssafy.blog.model.Rank;
import com.ssafy.blog.model.Tag;
import com.ssafy.blog.model.User;
import com.ssafy.blog.payload.question.AddQuestionRequest;
import com.ssafy.blog.payload.question.UpdateQuestionRequest;
import com.ssafy.blog.repository.AnswerRepository;
import com.ssafy.blog.repository.QuestionRepository;
import com.ssafy.blog.repository.QuestionTagRepository;
import com.ssafy.blog.repository.RankRepository;
import com.ssafy.blog.repository.UserRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private AnswerRepository answerRepository;

    private ResponseEntity<Question> badResponse = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    private static final String PATH_PREFIX = "/home/ubuntu/static/images/";
    private static final String BASE_IMAGE_URL = "http://i3c202.p.ssafy.io/api/images/";

    @GetMapping("/api/question")
    @ApiOperation(value = "질문 검색")
    // paging 기법 적용하기
    public ResponseEntity<Object> searchQuestion(@RequestParam(required = false, name = "keyword") String keyword,
            @RequestParam(required = false, name = "user_id") Long userId,
            @RequestParam(required = false, name = "question_id") Long questionId,
            @RequestParam(required = false, name = "page") Integer page) {
        Page<Question> pageList = null;
        if (page == null)
            page = 1;
        if (keyword == null && userId == null && questionId == null) {
            pageList = questionRepository.findAll(PageRequest.of(page - 1, 10, Sort.by("id").descending()));

        } else if (keyword != null && userId == null && questionId == null) {
            pageList = questionRepository.findAllByTitleContainsOrContentTextContains(keyword, keyword,
                    PageRequest.of(page - 1, 10, Sort.by("id").descending()));

        } else if (keyword == null && userId != null && questionId == null) {
            pageList = questionRepository.findAllByUserId(userId,
                    PageRequest.of(page - 1, 10, Sort.by("id").descending()));

        } else if (keyword == null && userId == null && questionId != null) {
            Optional<Question> optionalQuestion = questionRepository.findById(questionId);
            if (!optionalQuestion.isPresent())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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

        Optional<User> optionalUser = userRepository.findById(request.getUserId());
        if (!optionalUser.isPresent())
            return badResponse;
        question.setUser(optionalUser.get());
        question.setTitle(request.getTitle());
        question.setContent(request.getContent());
        // 검색을 위해 text영역 추출
        Document doc = Jsoup.parseBodyFragment(request.getContent());
        question.setContentText(doc.text());
        //
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

        updateRank(request.getUserId(), 1);

        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @PutMapping("/api/question")
    @ApiOperation(value = "질문 수정")
    public ResponseEntity<Question> modifyQuestion(@RequestBody UpdateQuestionRequest request) {
        Optional<Question> optionalQuestion = questionRepository.findById(request.getQuestionId());
        if (!optionalQuestion.isPresent())
            return badResponse;

        Question question = optionalQuestion.get();
        question.setTitle(request.getTitle());
        question.setContent(request.getContent());
        // 검색을 위해 text영역 추출
        Document doc = Jsoup.parseBodyFragment(request.getContent());
        question.setContentText(doc.text());
        //
        question.setViewCnt(question.getViewCnt() + 1);
        question.setUpdatedAt(new Date());
        question = questionRepository.save(question);

        // tag 수정
        questionTagRepository.deleteAllByQuestionId(request.getQuestionId());
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
    public ResponseEntity<String> deleteQuestion(@RequestParam("question_id") Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        if (!optionalQuestion.isPresent())
            return new ResponseEntity<>("not exist", HttpStatus.OK);

        Optional<Answer> optionalAnswer = answerRepository.findFirstByQuestionId(questionId);
        if (optionalAnswer.isPresent())
            return new ResponseEntity<>("already answer is exist", HttpStatus.OK);

        Long userId = optionalQuestion.get().getUser().getId();
        questionRepository.deleteById(questionId);

        updateRank(userId, -1);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/api/question/tag")
    @ApiOperation(value = "질문 태그로 검색")
    public ResponseEntity<Object> searchQuestionByTag(@RequestParam(required = true, name = "tag_id") Long tagId,
            @RequestParam(required = false, name = "page") Integer page) {
        if (page == null)
            page = 1;

        Page<Question> list = questionRepository.findAllByQuestionTags_TagId(tagId,
                PageRequest.of(page - 1, 10, Sort.by("id").descending()));

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/api/question/today")
    @ApiOperation(value = "오늘 작성된 질문 리턴")
    public ResponseEntity<Object> searchQuestionByDate() throws ParseException {
        Calendar calendar = Calendar.getInstance();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String nowString = df.format(new Date());
        Date start = df.parse(nowString);

        calendar.setTime(start);
        calendar.add(Calendar.DATE, 1);
        Date end = calendar.getTime();

        Long count = questionRepository.countByUpdatedAtBetween(start, end);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/api/question/all")
    @ApiOperation(value = "작성된 모든 질문 리턴")
    public ResponseEntity<Object> searchAllQuestionCnt() {
        Long count = questionRepository.count();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @PostMapping("/api/question/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        Date now = new Date();
        String filename = "";
        try {
            filename = now.getTime() + file.getOriginalFilename();
            File newFile = new File(PATH_PREFIX + filename);
            newFile.mkdirs();
            file.transferTo(newFile);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        String url = BASE_IMAGE_URL + filename;
        Map<String, String> data = new HashMap<>();
        data.put("url", url);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    private void updateRank(Long userId, int step) {
        Optional<Rank> optionalRank = rankRepository.findByUserId(userId);
        if (optionalRank.isPresent()) {
            Rank rank = optionalRank.get();
            rank.setQuestionCnt(rank.getQuestionCnt() + step);
            rankRepository.save(rank);
        }
    }
}