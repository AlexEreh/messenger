package com.alexereh.messenger.chat.repository;

import com.alexereh.messenger.chat.model.ChatMessage;
import com.alexereh.messenger.chat.model.MessageStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository
		extends MongoRepository<ChatMessage, String> {

	long countBySenderIdAndRecipientIdAndStatus(
			String senderId, String recipientId, MessageStatus status);

	List<ChatMessage> findByChatId(String chatId);
}