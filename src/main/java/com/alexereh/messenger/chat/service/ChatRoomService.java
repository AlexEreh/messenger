package com.alexereh.messenger.chat.service;

import com.alexereh.messenger.chat.model.ChatRoom;
import com.alexereh.messenger.chat.repository.ChatRoomRepository;
import com.alexereh.messenger.exceptions.NoChatException;
import com.alexereh.messenger.exceptions.UserNotFoundException;
import com.alexereh.messenger.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

	private final ChatRoomRepository chatRoomRepository;
	private final UserRepository userRepository;

	public String getChatId(
			Integer senderId, Integer recipientId) {
		var recipient = userRepository.findById(recipientId);
		if (recipient.isEmpty() || recipient.get().isDeleted()) {
			throw new UserNotFoundException("Получателя не существует или он удалён");
		}

		var minOfRecipientAndSender = Math.min(senderId, recipientId);
		var maxOfRecipientAndSender = Math.max(senderId, recipientId);
		var chatIdOptional = chatRoomRepository
				.findBySenderIdAndRecipientId(minOfRecipientAndSender, maxOfRecipientAndSender);
		if (chatIdOptional.isEmpty()) {
			var chatId = minOfRecipientAndSender + "_" + maxOfRecipientAndSender;
			ChatRoom senderRecipient = ChatRoom
					.builder()
					.chatId(chatId)
					.senderId(minOfRecipientAndSender)
					.recipientId(maxOfRecipientAndSender)
					.build();

			ChatRoom recipientSender = ChatRoom
					.builder()
					.chatId(chatId)
					.senderId(minOfRecipientAndSender)
					.recipientId(maxOfRecipientAndSender)
					.build();
			chatRoomRepository.save(senderRecipient);
			chatRoomRepository.save(recipientSender);
			return chatId;
		}

		return chatIdOptional.orElseThrow().getChatId();
	}
}
