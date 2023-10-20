package com.alexereh.messenger.chat.responses;

import com.alexereh.messenger.chat.model.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageListResponse {
	private List<ResponseMessage> data;
}
