package com.luantang.facebookapi.repositories;

import com.luantang.facebookapi.models.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface StatusRepository extends MongoRepository<Status, UUID> {
}
