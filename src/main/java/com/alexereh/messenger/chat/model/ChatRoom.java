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
public class ChatRoom {
	@Id
	@GeneratedValue
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "chat_id", nullable = false)
	private Chat chat;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "sender_id", nullable = false)
	private User sender;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "recipient_id", nullable = false)
	private User recipient;
}