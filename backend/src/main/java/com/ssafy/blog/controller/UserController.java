package com.ssafy.blog.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ssafy.blog.exception.ResourceNotFoundException;
import com.ssafy.blog.model.Tech;
import com.ssafy.blog.model.User;
import com.ssafy.blog.model.UserTech;
import com.ssafy.blog.payload.user.ModifyUserRequest;
import com.ssafy.blog.repository.UserRepository;
import com.ssafy.blog.repository.UserTechRepository;
import com.ssafy.blog.security.CurrentUser;
import com.ssafy.blog.security.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTechRepository userTechRepository;

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

        List<Tech> after = request.getTechList();

        Optional<User> optionalUser = userRepository.findById(request.getUser_id());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            updateTechStack(user, after);

            user.setIntroduction(request.getIntroduction());
            user.setGithubUrl(request.getGithubUrl());
            user.setUpdatedAt(new Date());
            user = userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    private void updateTechStack(User user, List<Tech> after) {
        List<UserTech> before = user.getUserTechs();
        System.out.println("####" + before.size());

        for(Tech tech : after) {
            Optional<UserTech> optionalUserTech = 
                userTechRepository.findByUserIdAndTechId(user.getId(), tech.getId());
            if(optionalUserTech.isPresent()) continue;

            UserTech userTech = new UserTech();
            userTech.setUser(user);
            userTech.setTech(tech);
            before.add(userTech);
            userTechRepository.save(userTech);
        }

        before = user.getUserTechs();
        System.out.println("####" + before.size());

        List<UserTech> deleteList = new ArrayList<>();
        for(UserTech userTech : before) {
            boolean flag = false;
            for(Tech tech : after) {
                if(userTech.getTech().getId().equals(tech.getId())){
                    flag = true;
                    break;
                }
            }
            if(!flag)
                deleteList.add(userTech);
        }

        for(UserTech userTech : deleteList) {
            before.remove(userTech);
            userTechRepository.delete(userTech);
        }
    }
}
