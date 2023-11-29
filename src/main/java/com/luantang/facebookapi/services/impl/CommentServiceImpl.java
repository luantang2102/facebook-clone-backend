package com.luantang.facebookapi.services.impl;

import com.luantang.facebookapi.dto.CommentDto;
import com.luantang.facebookapi.dto.response.CommentResponse;
import com.luantang.facebookapi.models.Comment;
import com.luantang.facebookapi.repositories.CommentRepository;
import com.luantang.facebookapi.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto) {
        commentDto.setCommentId(UUID.randomUUID());
        commentDto.setTimeSummit(new Date());
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
        commentDto.setPostId(comment.getPostId());
        commentDto.setComment(comment.getComment());
        commentDto.setTimeSummit(comment.getTimeSummit());
        return commentDto;
    }

}
