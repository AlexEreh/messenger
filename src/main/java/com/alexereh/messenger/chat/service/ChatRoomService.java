package com.alexereh.messenger.chat.service;

import com.alexereh.messenger.chat.model.ChatRoom;
import com.alexereh.messenger.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

	private final ChatRoomRepository chatRoomRepository;

	public Optional<String> getChatId(
			Integer senderId, Integer recipientId, boolean createIfNotExist) {

		return chatRoomRepository
				.findBySenderIdAndRecipientId(senderId, recipientId)
				.map(ChatRoom::getChatId)
				.or(() -> {
					if(!createIfNotExist) {
						return  Optional.empty();
					}
					var chatId =
							String.format("%s_%s", senderId, recipientId);

					ChatRoom senderRecipient = ChatRoom
							.builder()
							.chatId(chatId)
							.senderId(senderId)
							.recipientId(recipientId)
							.build();

					ChatRoom recipientSender = ChatRoom
							.builder()
							.chatId(chatId)
							.senderId(recipientId)
							.recipientId(senderId)
							.build();
					chatRoomRepository.save(senderRecipient);
					chatRoomRepository.save(recipientSender);

					return Optional.of(chatId);
				});
	}
}
