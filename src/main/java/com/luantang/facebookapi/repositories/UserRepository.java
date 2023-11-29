package com.luantang.facebookapi.repositories;

import com.luantang.facebookapi.models.Role;
import com.luantang.facebookapi.models.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
    UserEntity findByRole(Role role);
}
