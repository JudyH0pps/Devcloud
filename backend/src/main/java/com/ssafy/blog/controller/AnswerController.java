package com.ssafy.blog.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ssafy.blog.model.Answer;
import com.ssafy.blog.model.Notification;
import com.ssafy.blog.model.Question;
import com.ssafy.blog.model.Rank;
import com.ssafy.blog.model.User;
import com.ssafy.blog.payload.answer.AddAnswerRequest;
import com.ssafy.blog.payload.answer.AnswerResponse;
import com.ssafy.blog.payload.answer.UpdateAnswerRequest;
import com.ssafy.blog.repository.AnswerRepository;
import com.ssafy.blog.repository.NotificationRepository;
import com.ssafy.blog.repository.QuestionRepository;
import com.ssafy.blog.repository.RankRepository;
import com.ssafy.blog.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private NotificationRepository nr;

    private static final String SELECTED = "selected";

    @GetMapping("/api/answer")
    @ApiOperation(value = "답변 검색")
    public ResponseEntity<List<AnswerResponse>> searchAnswer(
            @RequestParam(required = false, name = "user_id") Long userId,
            @RequestParam(required = false, name = "question_id") Long questionId) {
        List<AnswerResponse> list = new ArrayList<>();

        if (userId == null && questionId == null) {
            for (Answer answer : answerRepository.findAll(Sort.by(SELECTED)))
                list.add(makeAnswerResponse(answer));

        } else if (userId != null && questionId == null) {
            for (Answer answer : answerRepository.findAllByUserId(userId))
                list.add(makeAnswerResponse(answer));

        } else if (userId == null && questionId != null) {
            for (Answer answer : answerRepository.findAllByQuestionId(questionId, Sort.by(SELECTED).descending()))
                list.add(makeAnswerResponse(answer));
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/api/answer")
    @ApiOperation(value = "답변 등록")
    public ResponseEntity<AnswerResponse> addAnswer(@RequestBody AddAnswerRequest request) {
        // 1. 유저 있는지 찾기
        Optional<User> optionalUser = userRepository.findById(request.getUserId());
        if (!optionalUser.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // 2. 질문 있는지 찾기
        Optional<Question> optionalQuestion = questionRepository.findById(request.getQuestionId());
        if (!optionalQuestion.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User user = optionalUser.get();
        Question question = optionalQuestion.get();

        // 3. 둘다 있으면 답변 등록
        Answer answer = new Answer();
        answer.setUser(user);
        answer.setQuestion(question);
        answer.setContent(request.getContent());
        answer.setLikeCnt(0);
        answer.setSelected(false);
        answer.setUpdatedAt(new Date());
        answer = answerRepository.save(answer);

        // 4. rank cnt
        updateRank(request.getUserId(), 1, 1);

        // 5. 알림 푸쉬
        String message = user.getName() + "님이 질문: " + question.getTitle() + " 에 답변을 달았습니다.";
        sendNotification(question.getUser(), question, message);

        AnswerResponse response = makeAnswerResponse(answer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/api/answer")
    @ApiOperation(value = "답변 수정")
    public ResponseEntity<AnswerResponse> modifyAnswer(@RequestBody UpdateAnswerRequest request) {
        Optional<Answer> optionalAnswer = answerRepository.findById(request.getAnswerId());
        if (!optionalAnswer.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Answer answer = optionalAnswer.get();
        answer.setContent(request.getContent());
        answer.setUpdatedAt(new Date());
        answer = answerRepository.save(answer);

        AnswerResponse response = makeAnswerResponse(answer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/answer")
    @ApiOperation(value = "답변 삭제")
    public ResponseEntity<String> deleteAnswer(@RequestParam("answer_id") Long answerId) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        if (!optionalAnswer.isPresent())
            return new ResponseEntity<>("not exist", HttpStatus.OK);
        Answer answer = optionalAnswer.get();
        if(answer.getSelected().booleanValue())
            return new ResponseEntity<>(SELECTED, HttpStatus.OK);

        Long userId = optionalAnswer.get().getUser().getId();
        answerRepository.deleteById(answerId);

        updateRank(userId, -1, 1);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/api/answer/select")
    @ApiOperation(value = "답변 채택")
    public ResponseEntity<Answer> selectAnswer(@RequestParam("answer_id") Long answerId) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        if (!optionalAnswer.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Answer answer = optionalAnswer.get();

        // 해당 댓글이 달린 질문에 이미 채택된 답변이 있는지 검사
        Optional<Answer> optionalSelectedAnswer = answerRepository
                .findByQuestionIdAndSelected(answer.getQuestion().getId(), true);
        if (optionalSelectedAnswer.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        //
        answer.setSelected(true);
        answer = answerRepository.save(answer);

        // rank 반영
        updateRank(answer.getUser().getId(), 1, 2);

        // 알림 생성
        Question question = answer.getQuestion();
        String message = question.getUser().getName() + "님이 답변을 채택했습니다.";
        sendNotification(answer.getUser(), question, message);

        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

    @GetMapping("/api/answer/selected")
    @ApiOperation(value = "질문에 대한 채택답변을 리턴")
    public ResponseEntity<Object> searchSelectedAnswer(@RequestParam("question_id") Long quesitonId) {
        Optional<Answer> optionalAnswer = answerRepository.findByQuestionIdAndSelected(quesitonId, true);
        if (optionalAnswer.isPresent()) {
            Answer answer = optionalAnswer.get();
            return new ResponseEntity<>(answer, HttpStatus.OK);
        }
        return new ResponseEntity<>("Resource not bound", HttpStatus.OK);
    }

    @GetMapping("/api/answer/today")
    @ApiOperation(value = "오늘 작성된 답변 리턴")
    public ResponseEntity<Object> searchAnswerByDate() throws ParseException {
        Calendar calendar = Calendar.getInstance();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String nowString = df.format(new Date());
        Date start = df.parse(nowString);

        calendar.setTime(start);
        calendar.add(Calendar.DATE, 1);
        Date end = calendar.getTime();

        Long count = answerRepository.countByUpdatedAtBetween(start, end);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/api/answer/all")
    @ApiOperation(value = "작성된 모든 답변 리턴")
    public ResponseEntity<Object> searchAllAnswerCnt() {
        Long count = answerRepository.count();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }


    private void updateRank(Long userId, int step, int type) {
        Optional<Rank> optionalRank = rankRepository.findByUserId(userId);
        if (optionalRank.isPresent()) {
            Rank rank = optionalRank.get();
            if (type == 1)
                rank.setAnswerCnt(rank.getAnswerCnt() + step);
            else if (type == 2)
                rank.setSelectCnt(rank.getSelectCnt() + step);
            rankRepository.save(rank);
        }
    }

    private AnswerResponse makeAnswerResponse(Answer answer) {
        AnswerResponse response = new AnswerResponse();
        response.setId(answer.getId());
        response.setUser(answer.getUser());
        response.setQuestionId(answer.getQuestion().getId());
        response.setQuestionTitle(answer.getQuestion().getTitle());
        response.setContent(answer.getContent());
        response.setLikeCnt(answer.getLikeCnt());
        response.setSelected(answer.getSelected());
        response.setUpdatedAt(answer.getUpdatedAt());
        return response;
    }

    private void sendNotification(User receiver, Question question, String message) {
        Notification n = new Notification();
        n.setUser(receiver);
        n.setContent(message);
        n.setQuestion(question);
        n.setIsRead(false);
        n.setCreatedAt(new Date());
        nr.save(n);

        receiver.setUnReadNotificationCnt(receiver.getUnReadNotificationCnt() + 1);
        userRepository.save(receiver);
    }
}