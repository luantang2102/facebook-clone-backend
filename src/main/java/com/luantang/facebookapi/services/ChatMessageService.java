package com.luantang.facebookapi.services;

import com.luantang.facebookapi.models.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    ChatMessage save(ChatMessage chatMessage);

    List<ChatMessage> findChatMessages(String recipientId);
}
