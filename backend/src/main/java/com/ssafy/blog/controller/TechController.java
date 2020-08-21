package com.ssafy.blog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ssafy.blog.model.Tech;
import com.ssafy.blog.payload.tech.AddTechRequest;
import com.ssafy.blog.repository.TechRepository;

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
public class TechController {

    @Autowired
    private TechRepository techRepository;

    @GetMapping("/api/tech")
    @ApiOperation(value = "기술스택 검색")
    public ResponseEntity<List<Tech>> searchTech(@RequestParam(required = false, name = "tech_id") Long techId,
            @RequestParam(required = false, name = "name") String name) {
        List<Tech> list = new ArrayList<>();

        if (techId == null && name == null) {
            list = techRepository.findAll();

        } else if (techId != null) {
            Optional<Tech> optionalTech = techRepository.findById(techId);
            if(optionalTech.isPresent()) {
                list.add(optionalTech.get());
            }

        } else if (name != null) {
            Optional<Tech> optionalTech = techRepository.findByName(name);
            if(optionalTech.isPresent())
                list.add(optionalTech.get());
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/api/tech")
    @ApiOperation(value = "기술스택 추가")
    public ResponseEntity<Tech> addTech(@RequestBody AddTechRequest request) {
        Tech tech = new Tech();
        tech.setName(request.getName());
        // 같은 이름의 기술스택을 추가할때 에러처리
        tech = techRepository.save(tech);
        return new ResponseEntity<>(tech, HttpStatus.OK);
    }

    @DeleteMapping("/api/tech")
    @ApiOperation(value = "기술스택 삭제")
    public ResponseEntity<String> deleteTech(@RequestParam Long techId) {
        // 존재하지 않는 id의 기술스택을 삭제할 때 에러처리
        techRepository.deleteById(techId);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}