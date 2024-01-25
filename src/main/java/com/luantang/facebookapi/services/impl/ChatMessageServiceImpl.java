package com.luantang.facebookapi.services.impl;

import com.luantang.facebookapi.models.ChatMessage;
import com.luantang.facebookapi.models.UserEntity;
import com.luantang.facebookapi.repositories.ChatMessageRepository;
import com.luantang.facebookapi.services.ChatMessageService;
import com.luantang.facebookapi.services.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    @Autowired
    public ChatMessageServiceImpl(ChatMessageRepository chatMessageRepository, ChatRoomService chatRoomService) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatRoomService = chatRoomService;
    }

    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setSenderId(getCurrentUser().getUserId());
        chatMessage.setMessageId(UUID.randomUUID());
        String chatId = chatRoomService.getChatId(
                chatMessage.getSenderId(),
                chatMessage.getRecipientId(),
                true
        ).orElseThrow();

        chatMessage.setTimestamp(new Date());
        chatMessage.setChatId(chatId);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    @Override
    public List<ChatMessage> findChatMessages(String recipientId) {
        String senderId = getCurrentUser().getUserId();
        List<ChatMessage> chatMessages = chatRoomService.getChatId(senderId, recipientId, false)
                .map(chatMessageRepository::findByChatId)
                .orElse(new ArrayList<>());

        // Sort the list by timestamp in descending order
        Collections.sort(chatMessages, (m1, m2) -> m2.getTimestamp().compareTo(m1.getTimestamp()));

        return chatMessages;
    }

    private UserEntity getCurrentUser() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
