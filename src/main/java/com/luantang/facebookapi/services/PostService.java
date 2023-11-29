package com.luantang.facebookapi.services;

import com.luantang.facebookapi.dto.PostDto;
import com.luantang.facebookapi.dto.response.PostResponse;

import java.util.UUID;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getPosts(int pageNo, int pageSize);
    void deletePost(UUID postId);
}
