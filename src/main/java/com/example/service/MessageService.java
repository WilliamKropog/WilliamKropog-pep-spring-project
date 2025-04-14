package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.repository.MessageRepository;
import com.example.entity.Message;

@Service
public class MessageService {

    private final MessageRepository repo;

    public MessageService(MessageRepository repo) {
        this.repo = repo;
    }

    public Message addNewMessage(Message message) {
        return repo.save(message);
    }

    public void deleteByMessageId(int messageId) {
        repo.deleteByMessageId(messageId);
    }

    public List<Message> getAllMessages() {
        return repo.findAll();
    }

    public List<Message> getAllMessageByAccountId(int accountId) {
        return repo.findAllByPostedBy(accountId);
    }

    public Message getMessageByMessageId(int messageId) {
        return repo.findByMessageId(messageId);
    }

    public boolean postedByExists(int postedBy) {
        return repo.existsByPostedBy(postedBy);
    }

    public int updateMessageByMessageId(int messageId, String newText) {
        return repo.updateMessageByMessageId(messageId, newText);
    }
}
