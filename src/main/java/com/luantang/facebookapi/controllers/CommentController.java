package com.luantang.facebookapi.controllers;

import com.luantang.facebookapi.dto.CommentDto;
import com.luantang.facebookapi.dto.response.CommentResponse;
import com.luantang.facebookapi.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment/create")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/comments")
    public ResponseEntity<CommentResponse> getComments(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(commentService.getComments(pageNo, pageSize), HttpStatus.OK);
    }

}
