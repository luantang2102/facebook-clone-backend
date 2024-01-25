package com.luantang.facebookapi.services.impl;

import com.luantang.facebookapi.models.ChatRoom;
import com.luantang.facebookapi.repositories.ChatRoomRepository;
import com.luantang.facebookapi.services.ChatRoomService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;


    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    @Override
    public Optional<String> getChatId(String senderId, String recipientId, boolean createNewRoomIfNotExist) {
        return chatRoomRepository.findBySenderIdAndRecipientId(senderId, recipientId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if(createNewRoomIfNotExist) {
                        String chatId = createChatId(senderId, recipientId);
                        return Optional.of(chatId);
                    }
                    return Optional.empty();
                });
    }

    private String createChatId(String senderId, String recipientId) {
        String chatId = String.format("%s_%s", senderId, recipientId);

        ChatRoom senderToRecipient = new ChatRoom(UUID.randomUUID(), chatId, senderId, recipientId);
        ChatRoom recipientToSender = new ChatRoom(UUID.randomUUID(), chatId, recipientId, senderId);
        chatRoomRepository.save(senderToRecipient);
        chatRoomRepository.save(recipientToSender);

        return chatId;
    }


}
