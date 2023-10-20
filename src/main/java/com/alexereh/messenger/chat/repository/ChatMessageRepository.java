package com.alexereh.messenger.chat.repository;

import com.alexereh.messenger.chat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {

	List<ChatMessage> findByChatId(Integer chatId);
}