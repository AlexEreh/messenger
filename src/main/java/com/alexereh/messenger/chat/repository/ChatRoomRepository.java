package com.alexereh.messenger.chat.repository;

import com.alexereh.messenger.chat.model.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
	Optional<ChatRoom> findBySenderIdAndRecipientId(Integer senderId, Integer recipientId);
}