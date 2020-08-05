package com.ssafy.blog.controller;

import java.util.Optional;

import com.ssafy.blog.exception.ResourceNotFoundException;
import com.ssafy.blog.model.User;
import com.ssafy.blog.model.UserProfile;
import com.ssafy.blog.repository.UserProfileRepository;
import com.ssafy.blog.repository.UserRepository;
import com.ssafy.blog.security.CurrentUser;
import com.ssafy.blog.security.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "사용자 정보 검색")
    // public ResponseEntity<User> getCurrentUser(@RequestParam(required = true) Long id) {
    //     Optional<User> optionalUser = userRepository.findById(id);
    //     if(optionalUser.isPresent()) {
    //         return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
    //     }
    //     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    // }
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @GetMapping("/api/user")
    public ResponseEntity<User> getUser(@RequestParam("id") Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/api/user_profile")
    public ResponseEntity<UserProfile> getUserProfile(@RequestParam("user_id") Long userId) {
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findByUserId(userId);
        UserProfile userProfile = optionalUserProfile.get();
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }
}
