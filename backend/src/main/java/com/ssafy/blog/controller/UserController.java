package com.ssafy.blog.controller;

import java.util.Date;
import java.util.Optional;

import com.ssafy.blog.exception.ResourceNotFoundException;
import com.ssafy.blog.model.User;
import com.ssafy.blog.payload.user.ModifyUserRequest;
import com.ssafy.blog.repository.UserRepository;
import com.ssafy.blog.security.CurrentUser;
import com.ssafy.blog.security.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @GetMapping("/api/user")
    @ApiOperation(value = "사용자 검색")
    public ResponseEntity<User> getUser(@RequestParam("user_id") Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/api/user")
    @ApiOperation(value = "사용자 프로필 수정")
    public ResponseEntity<User> modifyUser(@RequestBody ModifyUserRequest request) {
        Optional<User> optionalUser = userRepository.findById(request.getUser_id());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setIntroduction(request.getIntroduction());
            user.setGithubUrl(request.getGithubUrl());
            user.setUpdatedAt(new Date());
            user = userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
