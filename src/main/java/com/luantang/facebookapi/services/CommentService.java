package com.luantang.facebookapi.services;

import com.luantang.facebookapi.dto.CommentDto;
import com.luantang.facebookapi.dto.response.CommentResponse;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto);
    CommentResponse getComments(int pageNo, int pageSize);

    List<CommentDto> getCommentsByPostId(UUID postId);
}
