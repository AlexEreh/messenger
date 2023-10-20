package com.alexereh.messenger.chat.model;

import com.alexereh.messenger.user.model.User;
import jakarta.persistence.*;
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
	@GeneratedValue
	private Integer id;
	@ManyToOne(fetch = FetchType.EAGER)
	private User sender;
	@ManyToOne(fetch = FetchType.EAGER)
	private User recipient;
	@ManyToOne
	private ChatRoom chat;
	private String content;
}
