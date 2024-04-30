package com.service.property.notificationservice.controller;

import com.service.property.notificationservice.dto.MailDto;
import com.service.property.notificationservice.services.MailServiceImpl;
import com.service.property.notificationservice.utils.ResponseWrapper;
import com.service.property.notificationservice.utils.UnSuccessfulWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
public class SendMailController {

    private final MailServiceImpl mailService;

    public SendMailController(MailServiceImpl mailService) {
        this.mailService = mailService;
    }


    @PostMapping("/send")
    public ResponseEntity<Object> sendMail(@RequestBody MailDto mailDto) {
        Boolean isMailSend = mailService.sendTenantEmail(mailDto.getTo(), mailDto.getContent());

        return isMailSend ? ResponseEntity.ok(new ResponseWrapper<>("success",
                "Mail send successfully", "success"))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new UnSuccessfulWrapper("failure",
                        "Failed to send mail")
        );
    }
}
