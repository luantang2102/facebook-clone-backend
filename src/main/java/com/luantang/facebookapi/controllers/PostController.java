package com.luantang.facebookapi.controllers;

import com.luantang.facebookapi.dto.PostDto;
import com.luantang.facebookapi.dto.response.PostResponse;
import com.luantang.facebookapi.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
public class PostController{

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post/create")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(postService.getPosts(pageNo, pageSize), HttpStatus.OK);
    }

    @DeleteMapping("/post/{postId}/delete")
    public ResponseEntity<String> deletePost(@PathVariable("postId") UUID postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>("Post deleted!", HttpStatus.OK);
    }
}
