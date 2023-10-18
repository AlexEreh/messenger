package com.alexereh.messenger.chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class ChatMessage {
	@Id
	private Integer id;
	private String chatId;
	private Integer senderId;
	private Integer recipientId;
	private String content;
	private MessageStatus status;
}
