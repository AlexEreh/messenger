package com.alexereh.messenger.chat;

import com.alexereh.messenger.chat.model.ChatMessage;
import com.alexereh.messenger.chat.model.ChatNotification;
import com.alexereh.messenger.chat.requests.MessageInRequest;
import com.alexereh.messenger.chat.responses.MessageListResponse;
import com.alexereh.messenger.chat.responses.ResponseMessage;
import com.alexereh.messenger.chat.responses.SendMessageResponse;
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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Chat Controller", description = "API для операций с чатом")
public class ChatController {
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
		var chat = chatRoomService
				.getChatId(user.getId(), chatMessage.getRecipientId());
		var savedMessage = ChatMessage.builder()
				.chat(chat)
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
			summary = "Отослать сообщение пользователю"
	)
	@PostMapping("/chat/send")
	public SendMessageResponse sendMessage(
			Principal currentUser,
			@RequestBody MessageInRequest chatMessage) {

		var user = (User) ((UsernamePasswordAuthenticationToken) currentUser).getPrincipal();
		var chat = chatRoomService
				.getChatId(user.getId(), chatMessage.getRecipientId());
		var savedMessage = ChatMessage.builder()
				.chat(chat)
				.sender(user)
				.recipient(repository.findById(chatMessage.getRecipientId()).orElseThrow())
				.content(chatMessage.getContent())
				.build();
		chatMessageService.save(savedMessage);
		return new SendMessageResponse(true);
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
		var lol = messages.stream()
				.map(it ->
						new ResponseMessage(
								it.getContent(),
								it.getSender().getId(),
								"%s %s".formatted(it.getSender().getFirstName(), it.getSender().getLastName()),
								it.getRecipient().getId(),
								"%s %s".formatted(it.getRecipient().getFirstName(), it.getRecipient().getLastName())
						)
				)
				.toList();
		log.info(lol.toString());
		return new MessageListResponse(lol);
	}
}
