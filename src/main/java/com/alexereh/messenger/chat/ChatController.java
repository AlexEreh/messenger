package com.alexereh.messenger.chat;

import com.alexereh.messenger.chat.model.ChatMessage;
import com.alexereh.messenger.chat.model.ChatNotification;
import com.alexereh.messenger.chat.responses.MessageListResponse;
import com.alexereh.messenger.chat.service.ChatMessageService;
import com.alexereh.messenger.chat.service.ChatRoomService;
import com.alexereh.messenger.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@Tag(name = "Chat Controller", description = "API для операций с чатом")
public class ChatController {
	private final SimpMessagingTemplate messagingTemplate;
	private final ChatMessageService chatMessageService;
	private final ChatRoomService chatRoomService;

	@MessageMapping("/queue")
	public void processMessage(@Payload ChatMessage chatMessage) {
		var chatId = chatRoomService
				.getChatId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
				.orElseThrow();
		chatMessage.setChatId(chatId);

		ChatMessage saved = chatMessageService.save(chatMessage);
		messagingTemplate.convertAndSendToUser(
				String.valueOf(chatMessage.getRecipientId()),
				"/messages",
				new ChatNotification(
						saved.getId(),
						saved.getSenderId()));
	}

	@Operation(
			summary = "Число новых сообщений в диалоге",
			description = "Позволяет посмотреть число новых сообщений в диалоге с пользователем"
	)
	@GetMapping("/{recipientId}/count")
	public Long countNewMessages(
			Principal currentUser,
			@PathVariable Integer recipientId) {
		var user = (User) ((UsernamePasswordAuthenticationToken) currentUser).getPrincipal();
		return chatMessageService.countNewMessages(user, recipientId);
	}

	@Operation(
			summary = "Найти все сообщения в чате",
			description = "Возвращает все сообщения в чате с пользователем"
	)
	@GetMapping("/{recipientId}")
	public MessageListResponse findChatMessages(Principal currentUser,
	                                          @PathVariable Integer recipientId) {
		var user = (User) ((UsernamePasswordAuthenticationToken) currentUser).getPrincipal();
		var messages = chatMessageService.findChatMessages(user, recipientId);
		return new MessageListResponse(messages);
	}
}
