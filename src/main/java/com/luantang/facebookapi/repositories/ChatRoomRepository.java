package com.luantang.facebookapi.repositories;

import com.luantang.facebookapi.models.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, UUID> {
    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);
}
