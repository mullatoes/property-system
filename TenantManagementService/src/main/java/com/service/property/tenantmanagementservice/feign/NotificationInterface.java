package com.service.property.tenantmanagementservice.feign;

import com.service.property.tenantmanagementservice.dto.MailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("NOTIFICATIONSERVICE")
public interface NotificationInterface {

    @GetMapping("/api/mail/send")
    ResponseEntity<Object> sendMail(@RequestBody MailDTO mailDto);

}
