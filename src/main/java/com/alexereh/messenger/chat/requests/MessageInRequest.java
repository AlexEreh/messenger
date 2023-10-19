package com.alexereh.messenger.chat.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MessageInRequest {
	@JsonProperty("recipient_id")
	private int recipientId;
	@JsonProperty("content")
	private String content;
}
