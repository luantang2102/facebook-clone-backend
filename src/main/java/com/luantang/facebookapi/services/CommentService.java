package com.luantang.facebookapi.services;

import com.luantang.facebookapi.dto.CommentDto;
import com.luantang.facebookapi.dto.response.CommentResponse;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto);
    CommentResponse getComments(int pageNo, int pageSize);
}
