package com.luantang.facebookapi.services.impl;

import com.luantang.facebookapi.dto.CommentDto;
import com.luantang.facebookapi.dto.UserDto;
import com.luantang.facebookapi.dto.response.CommentResponse;
import com.luantang.facebookapi.models.Comment;
import com.luantang.facebookapi.models.UserEntity;
import com.luantang.facebookapi.repositories.CommentRepository;
import com.luantang.facebookapi.services.CommentService;
import com.luantang.facebookapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto) {
        commentDto.setCommentId(UUID.randomUUID());
        commentDto.setTimeSummit(new Date());
        commentDto.setUserId(getCurrentUser().getUserId());
        commentDto.setImageURL(getCurrentUser().getUserImage());

        Comment comment = mapToEntity(commentDto);

        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);

    }

    @Override
    public CommentResponse getComments(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Comment> comments = commentRepository.findAll(pageable);
        List<Comment> commentList = comments.getContent();
        List<CommentDto> content = commentList.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());

        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setContent(content);
        commentResponse.setPageNo(comments.getNumber());
        commentResponse.setPageSize(comments.getSize());
        commentResponse.setTotalElements(comments.getTotalElements());
        commentResponse.setTotalPages(comments.getTotalPages());
        commentResponse.setLast(comments.isLast());

        return commentResponse;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(UUID postId) {
       List<Comment> comments = commentRepository.findByPostId(postId);
       List<CommentDto> commentDtoList = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
       return commentDtoList;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setCommentId(commentDto.getCommentId());
        comment.setUserId(commentDto.getUserId());
        comment.setPostId(commentDto.getPostId());
        comment.setComment(commentDto.getComment());
        comment.setTimeSummit(commentDto.getTimeSummit());
        return comment;
    }

    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentId(comment.getCommentId());
        commentDto.setUserId(comment.getUserId());
        UserDto user = userService.getUserById(comment.getUserId());
        commentDto.setUserName(user.getUserName());
        commentDto.setImageURL(user.getUserImage());
        commentDto.setPostId(comment.getPostId());
        commentDto.setComment(comment.getComment());
        commentDto.setTimeSummit(comment.getTimeSummit());
        return commentDto;
    }

    public UserEntity getCurrentUser() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
