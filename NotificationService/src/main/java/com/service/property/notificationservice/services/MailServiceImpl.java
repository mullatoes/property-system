package com.service.property.notificationservice.services;

import com.service.property.notificationservice.model.Mail;
import com.service.property.notificationservice.model.Message;
import com.service.property.notificationservice.repository.MessageRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MailServiceImpl implements MailService {

    public static final String SENDER_EMAIL = "jaydevsengineer@gmail.com";
    public static final String SUBJECT = "Welcome Tenant";

    @Qualifier("getMailSender")
    private final JavaMailSender javaMailSender;

    private final MessageRepository messageRepository;

    public MailServiceImpl(JavaMailSender javaMailSender, MessageRepository messageRepository) {
        this.javaMailSender = javaMailSender;
        this.messageRepository = messageRepository;
    }

    @Override
    public boolean sendMail(Mail mail) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom()));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getMailContent());
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Mail createMailObject(String receiverEmail, String content) {
        Mail mail = new Mail();
        mail.setMailFrom(SENDER_EMAIL);
        mail.setMailTo(receiverEmail);
        mail.setMailSubject(SUBJECT);
        mail.setMailContent(content);
        return mail;
    }

    public Boolean sendTenantEmail(String receiverEmail, String content) {
        try {

            Date now = new Date();
            Mail mail = createMailObject(receiverEmail, content);
            Message message = new Message();
            message.setTo(mail.getMailTo());
            message.setFrom(SENDER_EMAIL);
            message.setMessage(content);
            message.setSendDate(now);

            boolean isMailSend = sendMail(mail);
            if (isMailSend) {
                messageRepository.save(message);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Something went wrong ... Failed to send tenant email: " + e.getMessage());
            return false;
        }
    }
}
