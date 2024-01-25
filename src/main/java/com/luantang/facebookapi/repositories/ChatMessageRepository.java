package com.luantang.facebookapi.repositories;

import com.luantang.facebookapi.models.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, UUID> {
    List<ChatMessage> findByChatId(String chatId);
}
