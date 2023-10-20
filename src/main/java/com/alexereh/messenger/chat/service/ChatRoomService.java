package com.alexereh.messenger.chat.service;

import com.alexereh.messenger.chat.model.Chat;
import com.alexereh.messenger.chat.model.ChatRoom;
import com.alexereh.messenger.chat.repository.ChatRepository;
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
	private final ChatRepository chatRepository;
	private final UserRepository userRepository;

	public ChatRoom getChatId(
			Integer senderId, Integer recipientId) {
		var sender = userRepository.findById(senderId);
		if (sender.isEmpty() || sender.get().isDeleted()) {
			throw new UserNotFoundException("Отправителя не существует или он удалён");
		}
		var recipient = userRepository.findById(recipientId);
		if (recipient.isEmpty() || recipient.get().isDeleted()) {
			throw new UserNotFoundException("Получателя не существует или он удалён");
		}

		var minOfRecipientAndSender = Math.min(senderId, recipientId);
		var maxOfRecipientAndSender = Math.max(senderId, recipientId);
		var chatOptional = chatRoomRepository
				.findBySenderIdAndRecipientId(minOfRecipientAndSender, maxOfRecipientAndSender);
		if (chatOptional.isEmpty()) {
			var newChat = Chat.builder().build();
			ChatRoom senderRecipient = ChatRoom
					.builder()
					.chat(newChat)
					.sender(sender.get())
					.recipient(recipient.get())
					.build();

			ChatRoom recipientSender = ChatRoom
					.builder()
					.chat(newChat)
					.sender(recipient.get())
					.recipient(sender.get())
					.build();
			chatRepository.save(newChat);
			chatRoomRepository.save(senderRecipient);
			chatRoomRepository.save(recipientSender);
			return senderRecipient;
		}

		return chatOptional.orElseThrow();
	}
}
