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
	private final ChatMessageRepository messageRepository;
	private final ChatRoomService chatRoomService;

	@Transactional
	public ChatMessage save(ChatMessage chatMessage) {
		return messageRepository.save(chatMessage);
	}

	public List<ChatMessage> findChatMessages(User user, Integer recipientId) {
		var chat = chatRoomService.getChatId(user.getId(), recipientId);

		return messageRepository.findByChatId(chat.getId());
	}
}
