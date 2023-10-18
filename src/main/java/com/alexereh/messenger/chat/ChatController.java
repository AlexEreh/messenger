package com.alexereh.messenger.chat;

import com.alexereh.messenger.chat.model.ChatMessage;
import com.alexereh.messenger.chat.model.ChatNotification;
import com.alexereh.messenger.chat.service.ChatMessageService;
import com.alexereh.messenger.chat.service.ChatRoomService;
import com.alexereh.messenger.user.model.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/messages")
@RequiredArgsConstructor
@Tag(name = "Chat Controller", description = "API для операций с чатом")
public class ChatController {
	private SimpMessagingTemplate messagingTemplate;
	private ChatMessageService chatMessageService;
	private ChatRoomService chatRoomService;

	public ChatController(SimpMessagingTemplate messagingTemplate, ChatMessageService chatMessageService, ChatRoomService chatRoomService) {
		this.messagingTemplate = messagingTemplate;
		this.chatMessageService = chatMessageService;
		this.chatRoomService = chatRoomService;
	}

	@MessageMapping("/chat")
	public void processMessage(@Payload ChatMessage chatMessage) {
		var chatId = chatRoomService
				.getChatId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true);
		chatMessage.setChatId(chatId.get());

		ChatMessage saved = chatMessageService.save(chatMessage);
		messagingTemplate.convertAndSendToUser(
				chatMessage.getRecipientId(), "/queue/messages",
				new ChatNotification(
						saved.getId(),
						saved.getSenderId(),
						saved.getSenderName()));
	}

	@GetMapping("/{recipientId}/count")
	public ResponseEntity<Long> countNewMessages(
			Principal currentUser,
			@PathVariable String recipientId) {
		var user = (User) ((UsernamePasswordAuthenticationToken) currentUser).getPrincipal();
		return ResponseEntity
				.ok(chatMessageService.countNewMessages(user, recipientId));
	}

	@GetMapping("/{recipientId}")
	public ResponseEntity<?> findChatMessages(Principal currentUser,
	                                          @PathVariable String recipientId) {
		var user = (User) ((UsernamePasswordAuthenticationToken) currentUser).getPrincipal();
		return ResponseEntity
				.ok(chatMessageService.findChatMessages(user, recipientId));
	}
}
