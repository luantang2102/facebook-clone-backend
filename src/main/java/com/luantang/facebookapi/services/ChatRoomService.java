package com.luantang.facebookapi.services;

import java.util.Optional;

public interface ChatRoomService {

    Optional<String> getChatId(String senderId, String recipientId, boolean createNewRoomIfNotExist);
}
