package com.luantang.facebookapi.repositories;

import com.luantang.facebookapi.models.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, UUID> {
    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);
}
