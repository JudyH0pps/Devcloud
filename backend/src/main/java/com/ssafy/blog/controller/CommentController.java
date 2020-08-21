package com.ssafy.blog.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ssafy.blog.model.Answer;
import com.ssafy.blog.model.Comment;
import com.ssafy.blog.model.Notification;
import com.ssafy.blog.model.Question;
import com.ssafy.blog.model.User;
import com.ssafy.blog.payload.comment.AddCommentRequest;
import com.ssafy.blog.payload.comment.ModifyCommentRequest;
import com.ssafy.blog.repository.AnswerRepository;
import com.ssafy.blog.repository.CommentRepository;
import com.ssafy.blog.repository.NotificationRepository;
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
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository nr;

    @GetMapping("/api/comment")
    @ApiOperation(value = "댓글 조회하기")
    public ResponseEntity<Object> searchComment(@RequestParam("answer_id") Long answerId) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        if (!optionalAnswer.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Comment> list = commentRepository.findAllByAnswerId(answerId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/api/comment")
    @ApiOperation(value = "댓글 작성하기")
    public ResponseEntity<Object> addComment(@RequestBody AddCommentRequest request) {
        Optional<User> optionalUser = userRepository.findById(request.getUserId());
        if (!optionalUser.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<Answer> optionalAnswer = answerRepository.findById(request.getAnswerId());
        if (!optionalAnswer.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User user = optionalUser.get();
        Answer answer = optionalAnswer.get();

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setAnswer(answer);
        comment.setContent(request.getContent());
        comment.setUpdatedAt(new Date());
        comment = commentRepository.save(comment);

        // 알림 푸쉬
        Question question = answer.getQuestion();
        sendNotification(user, answer.getUser(), question);

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PutMapping("/api/comment")
    @ApiOperation(value = "댓글 수정하기")
    public ResponseEntity<Object> modifyComment(@RequestBody ModifyCommentRequest request) {
        Optional<Comment> optionalComment = commentRepository.findById(request.getCommentId());
        if (!optionalComment.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Comment comment = optionalComment.get();
        comment.setContent(request.getContent());
        comment.setUpdatedAt(new Date());
        comment = commentRepository.save(comment);

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @DeleteMapping("/api/comment")
    @ApiOperation(value = "댓글 삭제하기")
    public ResponseEntity<String> deleteComment(@RequestParam("comment_id") Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (!optionalComment.isPresent())
            return new ResponseEntity<>("not exist", HttpStatus.OK);

        commentRepository.deleteById(commentId);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    private void sendNotification(User sender, User receiver, Question question) {
        Notification n = new Notification();
        String content = sender.getName() + "님이 답변에 댓글을 달았습니다.";
        n.setUser(receiver);
        n.setContent(content);
        n.setQuestion(question);
        n.setIsRead(false);
        n.setCreatedAt(new Date());
        nr.save(n);
        
        receiver.setUnReadNotificationCnt(receiver.getUnReadNotificationCnt() + 1);
        userRepository.save(receiver);
    }
}