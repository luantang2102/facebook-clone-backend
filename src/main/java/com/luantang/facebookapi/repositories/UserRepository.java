package com.luantang.facebookapi.repositories;

import com.luantang.facebookapi.models.enums.ConnectStatus;
import com.luantang.facebookapi.models.enums.Role;
import com.luantang.facebookapi.models.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
    UserEntity findByRole(Role role);
    List<UserEntity> findAllByConnectStatus(ConnectStatus connectStatus);
}
