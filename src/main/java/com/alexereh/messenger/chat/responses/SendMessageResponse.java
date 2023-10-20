package com.alexereh.messenger.chat.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SendMessageResponse {
	private boolean success;
}
