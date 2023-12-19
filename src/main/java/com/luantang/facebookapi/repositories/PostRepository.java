package com.luantang.facebookapi.repositories;

import com.luantang.facebookapi.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends MongoRepository<Post, UUID> {
    Page<Post> findByUserId(Pageable pageable, String userId);
}
