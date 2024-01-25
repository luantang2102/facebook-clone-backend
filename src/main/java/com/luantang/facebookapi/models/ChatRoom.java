package com.luantang.facebookapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
public class ChatRoom {
    @Id
    private UUID roomId;
    private String chatId;
    private String senderId;
    private String recipientId;

    public ChatRoom() {
    }

    public ChatRoom(UUID roomId, String chatId, String senderId, String recipientId) {
        this.roomId = roomId;
        this.chatId = chatId;
        this.senderId = senderId;
        this.recipientId = recipientId;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }
}
