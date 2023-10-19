package com.alexereh.messenger.chat;

import com.alexereh.messenger.chat.model.ChatMessage;
import com.alexereh.messenger.chat.model.ChatNotification;
import com.alexereh.messenger.chat.requests.MessageInRequest;
import com.alexereh.messenger.chat.responses.MessageListResponse;
import com.alexereh.messenger.chat.service.ChatMessageService;
import com.alexereh.messenger.chat.service.ChatRoomService;
import com.alexereh.messenger.user.model.User;
import com.alexereh.messenger.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Chat Controller", description = "API для операций с чатом")
public class ChatController {
	private final SimpMessagingTemplate messagingTemplate;
	private final ChatMessageService chatMessageService;
	private final ChatRoomService chatRoomService;
	private final UserRepository repository;

	@MessageMapping("/messages")
	@SendToUser("/queue/messages")
	public ChatNotification processMessage(
			@Payload MessageInRequest chatMessage,
			Principal currentUser
	) {
		var user = (User) ((UsernamePasswordAuthenticationToken) currentUser).getPrincipal();
		var chatId = chatRoomService
				.getChatId(user.getId(), chatMessage.getRecipientId());
		var savedMessage = ChatMessage.builder()
				.chatId(chatId)
				.sender(user)
				.recipient(repository.findById(chatMessage.getRecipientId()).orElseThrow())
				.content(chatMessage.getContent())
				.build();
		log.debug(savedMessage.toString());
		ChatMessage saved = chatMessageService.save(savedMessage);
		return new ChatNotification(
				saved.getId(),
				saved.getSender().getId(),
				saved.getContent());
	}

	@Operation(
			summary = "Найти все сообщения в чате",
			description = "Возвращает все сообщения в чате с пользователем"
	)
	@GetMapping("/chat/{recipientId}")
	public MessageListResponse findChatMessages(Principal currentUser,
	                                          @PathVariable Integer recipientId) {
		var user = (User) ((UsernamePasswordAuthenticationToken) currentUser).getPrincipal();
		var messages = chatMessageService.findChatMessages(user, recipientId);
		return new MessageListResponse(messages);
	}
}
