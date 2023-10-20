package com.alexereh.messenger.chat.repository;

import com.alexereh.messenger.chat.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {

}
