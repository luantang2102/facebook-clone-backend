package com.luantang.facebookapi.controllers;

import com.luantang.facebookapi.dto.ChatNotification;
import com.luantang.facebookapi.models.ChatMessage;
import com.luantang.facebookapi.services.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatController(SimpMessagingTemplate simpMessagingTemplate, ChatMessageService chatMessageService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatMessageService = chatMessageService;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> processChatMessage(@RequestBody ChatMessage chatMessage) {
        ChatMessage savedMsg = chatMessageService.save(chatMessage);
        simpMessagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(), "/queue/messages",
                new ChatNotification(
                        savedMsg.getChatId(),
                        savedMsg.getSenderId(),
                        savedMsg.getRecipientId(),
                        savedMsg.getContent()
                )
        );
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("current/messages/{recipientId}")
    public ResponseEntity<List<ChatMessage>> getChatMessages(@PathVariable String recipientId) {
        return new ResponseEntity<>(chatMessageService.findChatMessages(recipientId), HttpStatus.OK);
    }
}
