package com.alexereh.messenger.chat.model;

import com.alexereh.messenger.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatMessage {
	@Id
	private Integer id;
	private String chatId;
	@ManyToOne
	private User sender;
	@ManyToOne
	private User recipient;
	private String content;
}
