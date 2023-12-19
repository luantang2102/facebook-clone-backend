package com.luantang.facebookapi.services.impl;

import com.luantang.facebookapi.dto.PostDto;
import com.luantang.facebookapi.dto.UserDto;
import com.luantang.facebookapi.dto.response.PostResponse;
import com.luantang.facebookapi.exceptions.DuplicatedIdException;
import com.luantang.facebookapi.exceptions.PostNotFoundException;
import com.luantang.facebookapi.exceptions.UserNotFoundException;
import com.luantang.facebookapi.models.Post;
import com.luantang.facebookapi.models.UserEntity;
import com.luantang.facebookapi.repositories.PostRepository;
import com.luantang.facebookapi.services.PostService;
import com.luantang.facebookapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        postDto.setPostId(UUID.randomUUID());
        postDto.setUserId(getCurrentUser().getUserId());
        postDto.setUserName(getCurrentUser().getUsername());
        postDto.setImageURL(getCurrentUser().getUserImage());
        postDto.setDateTime(new Date());
        postDto.setLikes(0);
        postDto.setLikedList(new ArrayList<>());
        Post post = mapToEntity(postDto);

        Post newPost = postRepository.save(post);

        return mapToDto(newPost);
    }

    @Override
    public PostResponse getPosts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> postList = posts.getContent();

        List<Post> sortedPosts = new ArrayList<>(postList); // Copying elements to a new list

        sortedPosts.clear(); // Clear the list before sorting
        for (Post post : postList) {
            boolean added = false;
            for (int i = 0; i < sortedPosts.size(); i++) {
                if (post.getDateTime().compareTo(sortedPosts.get(i).getDateTime()) > 0) {
                    sortedPosts.add(i, post); // Insert at correct position
                    added = true;
                    break;
                }
            }
            if (!added) {
                sortedPosts.add(post); // Add at the end if it's the latest date
            }
        }

        List<PostDto> content = sortedPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setLast(posts.isLast());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());

        return postResponse;
    }

    @Override
    public PostResponse getPostsByUserId(int pageNo, int pageSize, String userId) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Post> posts = postRepository.findByUserId(pageable, userId);
        List<Post> postList = posts.getContent();

        List<Post> sortedPosts = new ArrayList<>(postList); // Copying elements to a new list

        sortedPosts.clear(); // Clear the list before sorting
        for (Post post : postList) {
            boolean added = false;
            for (int i = 0; i < sortedPosts.size(); i++) {
                if (post.getDateTime().compareTo(sortedPosts.get(i).getDateTime()) > 0) {
                    sortedPosts.add(i, post); // Insert at correct position
                    added = true;
                    break;
                }
            }
            if (!added) {
                sortedPosts.add(post); // Add at the end if it's the latest date
            }
        }

        List<PostDto> content = sortedPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setLast(posts.isLast());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());

        return postResponse;
    }

    @Override
    public PostDto updatePost(PostDto postDto, UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post could not be deleted"));

        post.setDescription(postDto.getDescription());
        post.setPostImgURL(postDto.getPostImgURL());

        Post updatePost = postRepository.save(post);

        return mapToDto(updatePost);
    }

    @Override
    public PostDto addUserToLikedList(UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post could not be found"));

        if(!isLiked(postId)) {
            List<String> newLikedList = post.getLikedList();
            newLikedList.add(getCurrentUser().getUserId());
            post.setLikedList(newLikedList);
            post.setLikes(post.getLikes() + 1);

            Post updatePost = postRepository.save(post);

            return mapToDto(updatePost);
        }
        else {
            throw new DuplicatedIdException("This user already liked");
        }
    }

    @Override
    public PostDto removeUserFromLikedList(UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post could not be found"));

        if(isLiked(postId)) {
            List<String> newLikedList = post.getLikedList();
            newLikedList.remove(getCurrentUser().getUserId());
            post.setLikedList(newLikedList);
            post.setLikes(post.getLikes() - 1);

            Post updatePost = postRepository.save(post);

            return mapToDto(updatePost);
        }
        else {
            throw new UserNotFoundException("This user has not liked the post yet");
        }
    }

    @Override
    public boolean isLiked(UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post could not be deleted"));
        List<String> likedList = post.getLikedList();
        return likedList.stream().anyMatch(id -> id.equals(getCurrentUser().getUserId()));
    }

    @Override
    public void deletePost(UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post could not be deleted"));
        postRepository.delete(post);
    }

    public PostDto mapToDto(Post post) {
        UserDto user = userService.getUserById(post.getUserId());
        return new PostDto(post.getPostId(),
                post.getUserId(),
                user.getUserName(),
                user.getUserImage(),
                post.getDescription(),
                post.getPostImgURL(),
                post.getLikes(),
                post.getDateTime(),
                post.getLikedList());
    }

    public Post mapToEntity(PostDto postDto) {
        return new Post(postDto.getPostId(),
                postDto.getUserId(),
                postDto.getDescription(),
                postDto.getPostImgURL(),
                postDto.getLikes(),
                postDto.getDateTime(),
                postDto.getLikedList());
    }

    public UserEntity getCurrentUser() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
