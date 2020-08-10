package com.ssafy.blog.controller;

import java.util.List;
import java.util.Optional;

import com.ssafy.blog.model.Tech;
import com.ssafy.blog.model.User;
import com.ssafy.blog.model.UserTech;
import com.ssafy.blog.payload.usertech.AddUserTechRequest;
import com.ssafy.blog.repository.TechRepository;
import com.ssafy.blog.repository.UserRepository;
import com.ssafy.blog.repository.UserTechRepository;

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
public class UserTechController {

    @Autowired
    private UserTechRepository userTechRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TechRepository techRepository;

    @GetMapping("/api/user/techstack")
    @ApiOperation(value = "사용자 기술스택 검색")
    public ResponseEntity<List<UserTech>> searchUserTech(@RequestParam("user_id") Long user_id) {
        List<UserTech> list = userTechRepository.findByUserId(user_id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/api/user/techstack")
    @ApiOperation(value = "사용자 기술스택 추가")
    public ResponseEntity<String> addUserTech(@RequestBody AddUserTechRequest request) {
        Optional<UserTech> optionalUserTech = userTechRepository.findByUserIdAndTechId(request.getUser_id(),
                request.getTech_id());
        if (optionalUserTech.isPresent()) {
            return new ResponseEntity<>("already exist", HttpStatus.OK);
        }

        UserTech userTech = new UserTech();

        Optional<User> optionalUser = userRepository.findById(request.getUser_id());
        User user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else
            return new ResponseEntity<>("user_id is not valid", HttpStatus.OK);

        Optional<Tech> optionalTech = techRepository.findById(request.getTech_id());
        Tech tech = null;
        if (optionalTech.isPresent()) {
            tech = optionalTech.get();
        } else
            return new ResponseEntity<>("tech_id is not valid", HttpStatus.OK);

        userTech.setUser(user);
        userTech.setTech(tech);
        userTechRepository.save(userTech);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @DeleteMapping("/api/user/techstack")
    @ApiOperation(value = "사용자 기술스택 삭제")
    public ResponseEntity<String> deleteUserTech(@RequestParam("user_tech_id") Long user_tech_id) {
        Optional<UserTech> optionalUserTech = userTechRepository.findById(user_tech_id);
        if (optionalUserTech.isPresent()) {
            userTechRepository.delete(optionalUserTech.get());
            return new ResponseEntity<>("success", HttpStatus.OK);
        } else
            return new ResponseEntity<>("not exist", HttpStatus.OK);
    }
}