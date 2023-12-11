package com.luantang.facebookapi.services;

import com.luantang.facebookapi.dto.PostDto;
import com.luantang.facebookapi.dto.response.PostResponse;

import java.util.UUID;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getPosts(int pageNo, int pageSize);

    PostDto updatePost(PostDto postDto, UUID postId);

    PostDto addUserToLikedList(UUID postId);

    PostDto removeUserFromLikedList(UUID postId);

    boolean isLiked(UUID postId);

    void deletePost(UUID postId);
}
