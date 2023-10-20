package com.alexereh.messenger.chat.repository;

import com.alexereh.messenger.chat.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
	Optional<ChatRoom> findBySenderIdAndRecipientId(Integer senderId, Integer recipientId);
}