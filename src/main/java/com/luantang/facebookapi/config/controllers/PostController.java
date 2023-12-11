package com.luantang.facebookapi.config.controllers;

import com.luantang.facebookapi.dto.PostDto;
import com.luantang.facebookapi.dto.response.PostResponse;
import com.luantang.facebookapi.models.Post;
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
            @RequestParam(value = "pageSize", defaultValue = "100", required = false) int pageSize
    ) {
        return new ResponseEntity<>(postService.getPosts(pageNo, pageSize), HttpStatus.OK);
    }

    @PutMapping("/post/{postId}/update")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("postId") UUID postId) {
        return new ResponseEntity<>(postService.updatePost(postDto, postId), HttpStatus.OK);
    }

    @PutMapping("post/{postId}/update/likes/add")
    public ResponseEntity<PostDto> addUserToLikedList(@PathVariable("postId") UUID postId) {
        return new ResponseEntity<>(postService.addUserToLikedList(postId), HttpStatus.OK);
    }

    @PutMapping("post/{postId}/update/likes/remove")
    public ResponseEntity<PostDto> removeUserFromLikedList(@PathVariable("postId") UUID postId) {
        return new ResponseEntity<>(postService.removeUserFromLikedList(postId), HttpStatus.OK);
    }

    @DeleteMapping("/post/{postId}/delete")
    public ResponseEntity<String> deletePost(@PathVariable("postId") UUID postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>("Post deleted!", HttpStatus.OK);
    }

    @RequestMapping("/post/{postId}/likes/isLiked")
    public ResponseEntity<Boolean> isLiked(@PathVariable("postId") UUID postId) {
        return new ResponseEntity<>(postService.isLiked(postId), HttpStatus.OK);
    }

}
