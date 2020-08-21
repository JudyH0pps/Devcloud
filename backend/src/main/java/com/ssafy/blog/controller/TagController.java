package com.ssafy.blog.controller;

import java.util.List;
import java.util.Optional;

import com.ssafy.blog.model.Tag;
import com.ssafy.blog.payload.tag.AddTagRequest;
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
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/api/tag")
    @ApiOperation(value = "태그 조회")
    public ResponseEntity<Object> searchTag() {
        List<Tag> tagList = tagRepository.findAll();
        return new ResponseEntity<>(tagList, HttpStatus.OK);
    }

    @PostMapping("/api/tag")
    @ApiOperation(value = "태그 등록")
    public ResponseEntity<Object> addTag(@RequestBody AddTagRequest request) {
        Optional<Tag> optionalTag = tagRepository.findByName(request.getName());
        if (optionalTag.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Tag tag = new Tag();
        tag.setName(request.getName());
        tag = tagRepository.save(tag);

        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @DeleteMapping("/api/tag")
    @ApiOperation(value = "태그 삭제")
    public ResponseEntity<String> deleteTag(@RequestParam("tag_id") Long tagId) {
        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        if (!optionalTag.isPresent())
            return new ResponseEntity<>("not exist", HttpStatus.OK);

        tagRepository.delete(optionalTag.get());

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}