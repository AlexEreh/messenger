package com.alexereh.messenger.chat.responses;

import com.alexereh.messenger.user.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseMessage {
	private String content;
	@JsonProperty("sender_id")
	private Integer senderId;
	private String sender;
	@JsonProperty("recipient_id")
	private Integer recipient_id;
	private String recipient;
}
