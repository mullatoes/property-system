package com.service.property.notificationservice.controller;

import com.service.property.notificationservice.model.Message;
import com.service.property.notificationservice.services.MessageService;
import com.service.property.notificationservice.utils.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Message>>> getMessages() {
        List<Message> messages = messageService.getMessages();

        return ResponseEntity.ok(new ResponseWrapper<>("success",
                "Messages retrieved successfully", messages));
    }
}
