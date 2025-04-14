package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.example.service.AccountService;
import com.example.entity.Account;
import com.example.service.MessageService;
import com.example.entity.Message;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody Account account) {
        
        if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            return ResponseEntity.status(400).body("Username or password is invalid.");
        }

        if (accountService.usernameExists(account.getUsername())) {
            return ResponseEntity.status(409).body("Username already exists.");
        }

        Account registeredAccount = accountService.registerAccount(account);
        return ResponseEntity.ok(registeredAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAccount(@RequestBody Account account) {

        Account authAccount = accountService.authenticate(account.getUsername(), account.getPassword());

        if (authAccount == null) {
            return ResponseEntity.status(401).body("Unauthorized access.");
        }

        return ResponseEntity.ok(authAccount);
    }

    @PostMapping("/messages")
    public ResponseEntity<?> postMessage(@RequestBody Message message) {

        if (message.getMessageText().length() == 0 || message.getMessageText().length() > 255) {
            return ResponseEntity.status(400).body("Invalid message length.");
        }

        if (!messageService.postedByExists(message.getPostedBy())) {
            return ResponseEntity.status(400).body("Posted by non-existing user.");
        }

        Message newMessage = messageService.addNewMessage(message);
        return ResponseEntity.ok(newMessage);

    }

    @GetMapping("/messages")
    public ResponseEntity<?> getAllMessages() {
        List<Message> allMessages = messageService.getAllMessages();
        return ResponseEntity.ok(allMessages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<?> getMessageByMessageId(@PathVariable int messageId) {
        Message retrievedMessage = messageService.getMessageByMessageId(messageId);
        return ResponseEntity.ok(retrievedMessage);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessageByMessageId(@PathVariable int messageId) {
        Message retrievedMessage = messageService.getMessageByMessageId(messageId);

        if (retrievedMessage == null) {
            return ResponseEntity.ok().body("");
        }

        messageService.deleteByMessageId(messageId);
        return ResponseEntity.ok().body("1");
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> updateMessageByMessageId(@PathVariable int messageId, @RequestBody Message message) {
        String newText = message.getMessageText();

        if (newText == null || newText.trim().isEmpty() || newText.length() > 255) {
            return ResponseEntity.status(400).body("Invalid message length.");
        }

        int rowsAffected = messageService.updateMessageByMessageId(messageId, newText);

        if (rowsAffected == 0) {
            return ResponseEntity.status(400).body("Message does not exist.");
        }

        return ResponseEntity.ok().body("1");
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<?> getAllMessagesByAccountId(@PathVariable int accountId) {
        List<Message> allMessages = messageService.getAllMessageByAccountId(accountId);
        return ResponseEntity.ok(allMessages);
    }
}
