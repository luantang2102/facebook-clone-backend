package com.luantang.facebookapi.repositories;

import com.luantang.facebookapi.models.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StatusRepository extends MongoRepository<Status, UUID> {
}
