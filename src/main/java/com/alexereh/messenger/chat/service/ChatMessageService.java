package com.alexereh.messenger.chat.service;

import com.alexereh.messenger.chat.model.ChatMessage;
import com.alexereh.messenger.chat.model.MessageStatus;
import com.alexereh.messenger.chat.repository.ChatMessageRepository;
import com.alexereh.messenger.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
	private final ChatMessageRepository repository;
	private final ChatRoomService chatRoomService;
	private final MongoOperations mongoOperations;

	public ChatMessage save(ChatMessage chatMessage) {
		chatMessage.setStatus(MessageStatus.RECEIVED);
		repository.save(chatMessage);
		return chatMessage;
	}

	public long countNewMessages(User user, Integer recipientId) {
		return repository.countBySenderIdAndRecipientIdAndStatus(
				user.getId(), recipientId, MessageStatus.RECEIVED);
	}

	public List<ChatMessage> findChatMessages(User user, Integer recipientId) {
		var chatId = chatRoomService.getChatId(user.getId(), recipientId, false);
		var userId = user.getId();

		var messages =
				chatId.map(repository::findByChatId).orElse(new ArrayList<>());

		if(!messages.isEmpty()) {
			updateStatuses(userId, recipientId, MessageStatus.DELIVERED);
		}

		return messages;
	}

	public void updateStatuses(Integer senderId, Integer recipientId, MessageStatus status) {
		Query query = new Query(
				Criteria
						.where("senderId").is(senderId)
						.and("recipientId").is(recipientId));
		Update update = Update.update("status", status);
		mongoOperations.updateMulti(query, update, ChatMessage.class);
	}
}
