package com.luantang.facebookapi.repositories;

import com.luantang.facebookapi.models.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends MongoRepository<Comment, UUID> {
    List<Comment> findByPostId(UUID postId);

}
