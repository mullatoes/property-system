package com.service.property.notificationservice.services;

import com.service.property.notificationservice.model.Mail;

public interface MailService {
    boolean sendMail(Mail mail);
}
