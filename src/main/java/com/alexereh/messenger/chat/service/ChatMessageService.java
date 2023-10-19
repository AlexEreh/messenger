package com.alexereh.messenger.chat.service;

import com.alexereh.messenger.chat.model.ChatMessage;
import com.alexereh.messenger.chat.repository.ChatMessageRepository;
import com.alexereh.messenger.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
	private final ChatMessageRepository repository;
	private final ChatRoomService chatRoomService;

	@Transactional
	public ChatMessage save(ChatMessage chatMessage) {
		return repository.save(chatMessage);
	}

	public List<ChatMessage> findChatMessages(User user, Integer recipientId) {
		var chatId = chatRoomService.getChatId(user.getId(), recipientId);

		return repository.findByChatId(chatId);
	}
}
