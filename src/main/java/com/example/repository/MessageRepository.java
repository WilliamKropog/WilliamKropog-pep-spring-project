package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{

    @Transactional
    void deleteByMessageId(int messageId);

    boolean existsByPostedBy(int postedBy);

    Message findByMessageId(int messageId);

    List<Message> findAllByPostedBy(int accountId);

    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.messageText = :newText WHERE m.messageId = :messageId")
    int updateMessageByMessageId(@Param("messageId") int messageId, @Param("newText") String newText);

}
