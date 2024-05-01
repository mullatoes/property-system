package com.service.property.notificationservice.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.service.property.notificationservice.model.Mail;
import com.service.property.notificationservice.repository.MessageRepository;

import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.MimeMessage;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MailServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class MailServiceImplTest {
    @MockBean
    private JavaMailSender javaMailSender;

    @Autowired
    private MailServiceImpl mailServiceImpl;

    @MockBean
    private MessageRepository messageRepository;

    @Test
    void testSendMail() throws MailException {
        // Arrange
        doNothing().when(javaMailSender).send(Mockito.<MimeMessage>any());
        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));

        Mail mail = new Mail();
        mail.setAttachments(new ArrayList<>());
        mail.setContentType("text/plain");
        mail.setMailBcc("ada.lovelace@example.org");
        mail.setMailCc("ada.lovelace@example.org");
        mail.setMailContent("Not all who wander are lost");
        mail.setMailFrom("jane.doe@example.org");
        mail.setMailSubject("Hello from the Dreaming Spires");
        mail.setMailTo("alice.liddell@example.org");

        // Act
        boolean actualSendMailResult = mailServiceImpl.sendMail(mail);

        // Assert
        verify(javaMailSender).createMimeMessage();
        verify(javaMailSender).send(isA(MimeMessage.class));
        assertTrue(actualSendMailResult);
    }

    @Test
    void testSendMail2() throws MessagingException, MailException {
        // Arrange
        MimeMessage mimeMessage = mock(MimeMessage.class);
        doNothing().when(mimeMessage).setRecipient(Mockito.<Message.RecipientType>any(), Mockito.<Address>any());
        doNothing().when(mimeMessage).setContent(Mockito.<Multipart>any());
        doNothing().when(mimeMessage).setFrom(Mockito.<Address>any());
        doNothing().when(mimeMessage).setSubject(Mockito.<String>any());
        doNothing().when(javaMailSender).send(Mockito.<MimeMessage>any());
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        Mail mail = new Mail();
        mail.setAttachments(new ArrayList<>());
        mail.setContentType("text/plain");
        mail.setMailBcc("ada.lovelace@example.org");
        mail.setMailCc("ada.lovelace@example.org");
        mail.setMailContent("Not all who wander are lost");
        mail.setMailFrom("jane.doe@example.org");
        mail.setMailSubject("Hello from the Dreaming Spires");
        mail.setMailTo("alice.liddell@example.org");

        // Act
        boolean actualSendMailResult = mailServiceImpl.sendMail(mail);

        // Assert
        verify(mimeMessage).setRecipient(isA(Message.RecipientType.class), isA(Address.class));
        verify(mimeMessage).setContent(isA(Multipart.class));
        verify(mimeMessage).setFrom(isA(Address.class));
        verify(mimeMessage).setSubject(eq("Hello from the Dreaming Spires"));
        verify(javaMailSender).createMimeMessage();
        verify(javaMailSender).send(isA(MimeMessage.class));
        assertTrue(actualSendMailResult);
    }

    @Test
    void testSendMail3() throws MessagingException, MailException {
        // Arrange
        MimeMessage mimeMessage = mock(MimeMessage.class);
        doNothing().when(mimeMessage).setRecipient(Mockito.<Message.RecipientType>any(), Mockito.<Address>any());
        doNothing().when(mimeMessage).setContent(Mockito.<Multipart>any());
        doNothing().when(mimeMessage).setFrom(Mockito.<Address>any());
        doNothing().when(mimeMessage).setSubject(Mockito.<String>any());
        doNothing().when(javaMailSender).send(Mockito.<MimeMessage>any());
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        Mail mail = mock(Mail.class);
        when(mail.getMailContent()).thenReturn("Not all who wander are lost");
        when(mail.getMailTo()).thenReturn("foo");
        when(mail.getMailFrom()).thenReturn("jane.doe@example.org");
        when(mail.getMailSubject()).thenReturn("Hello from the Dreaming Spires");
        doNothing().when(mail).setAttachments(Mockito.<List<Object>>any());
        doNothing().when(mail).setContentType(Mockito.<String>any());
        doNothing().when(mail).setMailBcc(Mockito.<String>any());
        doNothing().when(mail).setMailCc(Mockito.<String>any());
        doNothing().when(mail).setMailContent(Mockito.<String>any());
        doNothing().when(mail).setMailFrom(Mockito.<String>any());
        doNothing().when(mail).setMailSubject(Mockito.<String>any());
        doNothing().when(mail).setMailTo(Mockito.<String>any());
        mail.setAttachments(new ArrayList<>());
        mail.setContentType("text/plain");
        mail.setMailBcc("ada.lovelace@example.org");
        mail.setMailCc("ada.lovelace@example.org");
        mail.setMailContent("Not all who wander are lost");
        mail.setMailFrom("jane.doe@example.org");
        mail.setMailSubject("Hello from the Dreaming Spires");
        mail.setMailTo("alice.liddell@example.org");

        // Act
        boolean actualSendMailResult = mailServiceImpl.sendMail(mail);

        // Assert
        verify(mail).getMailContent();
        verify(mail).getMailFrom();
        verify(mail).getMailSubject();
        verify(mail).getMailTo();
        verify(mail).setAttachments(isA(List.class));
        verify(mail).setContentType(eq("text/plain"));
        verify(mail).setMailBcc(eq("ada.lovelace@example.org"));
        verify(mail).setMailCc(eq("ada.lovelace@example.org"));
        verify(mail).setMailContent(eq("Not all who wander are lost"));
        verify(mail).setMailFrom(eq("jane.doe@example.org"));
        verify(mail).setMailSubject(eq("Hello from the Dreaming Spires"));
        verify(mail).setMailTo(eq("alice.liddell@example.org"));
        verify(mimeMessage).setRecipient(isA(Message.RecipientType.class), isA(Address.class));
        verify(mimeMessage).setContent(isA(Multipart.class));
        verify(mimeMessage).setFrom(isA(Address.class));
        verify(mimeMessage).setSubject(eq("Hello from the Dreaming Spires"));
        verify(javaMailSender).createMimeMessage();
        verify(javaMailSender).send(isA(MimeMessage.class));
        assertTrue(actualSendMailResult);
    }

    @Test
    void testSendTenantEmail() throws MailException {
        // Arrange
        doNothing().when(javaMailSender).send(Mockito.<MimeMessage>any());
        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));

        com.service.property.notificationservice.model.Message message = new com.service.property.notificationservice.model.Message();
        message.setFrom("jane.doe@example.org");
        message.setId(1L);
        message.setMessage("Not all who wander are lost");
        message.setSendDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        message.setTo("alice.liddell@example.org");
        when(messageRepository.save(Mockito.<com.service.property.notificationservice.model.Message>any()))
                .thenReturn(message);

        // Act
        Boolean actualSendTenantEmailResult = mailServiceImpl.sendTenantEmail("jane.doe@example.org",
                "Not all who wander are lost");

        // Assert
        verify(messageRepository).save(isA(com.service.property.notificationservice.model.Message.class));
        verify(javaMailSender).createMimeMessage();
        verify(javaMailSender).send(isA(MimeMessage.class));
        assertTrue(actualSendTenantEmailResult);
    }

    @Test
    void testSendTenantEmail2() {
        // Arrange
        when(javaMailSender.createMimeMessage()).thenReturn(null);

        // Act
        Boolean actualSendTenantEmailResult = mailServiceImpl.sendTenantEmail("jane.doe@example.org",
                "Not all who wander are lost");

        // Assert
        verify(javaMailSender).createMimeMessage();
        assertFalse(actualSendTenantEmailResult);
    }

    @Test
    void testSendTenantEmail3() throws MessagingException, MailException {
        // Arrange
        MimeMessage mimeMessage = mock(MimeMessage.class);
        doNothing().when(mimeMessage)
                .setRecipient(Mockito.<jakarta.mail.Message.RecipientType>any(), Mockito.<Address>any());
        doNothing().when(mimeMessage).setContent(Mockito.<Multipart>any());
        doNothing().when(mimeMessage).setFrom(Mockito.<Address>any());
        doNothing().when(mimeMessage).setSubject(Mockito.<String>any());
        doNothing().when(javaMailSender).send(Mockito.<MimeMessage>any());
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        com.service.property.notificationservice.model.Message message = new com.service.property.notificationservice.model.Message();
        message.setFrom("jane.doe@example.org");
        message.setId(1L);
        message.setMessage("Not all who wander are lost");
        message.setSendDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        message.setTo("alice.liddell@example.org");
        when(messageRepository.save(Mockito.<com.service.property.notificationservice.model.Message>any()))
                .thenReturn(message);

        // Act
        Boolean actualSendTenantEmailResult = mailServiceImpl.sendTenantEmail("jane.doe@example.org",
                "Not all who wander are lost");

        // Assert
        verify(mimeMessage).setRecipient(isA(jakarta.mail.Message.RecipientType.class), isA(Address.class));
        verify(mimeMessage).setContent(isA(Multipart.class));
        verify(mimeMessage).setFrom(isA(Address.class));
        verify(mimeMessage).setSubject(eq("Welcome Tenant"));
        verify(messageRepository).save(isA(com.service.property.notificationservice.model.Message.class));
        verify(javaMailSender).createMimeMessage();
        verify(javaMailSender).send(isA(MimeMessage.class));
        assertTrue(actualSendTenantEmailResult);
    }

    @Test
    void testSendTenantEmail4() throws MessagingException, MailException {
        // Arrange
        MimeMessage mimeMessage = mock(MimeMessage.class);
        doNothing().when(mimeMessage)
                .setRecipient(Mockito.<jakarta.mail.Message.RecipientType>any(), Mockito.<Address>any());
        doNothing().when(mimeMessage).setContent(Mockito.<Multipart>any());
        doNothing().when(mimeMessage).setFrom(Mockito.<Address>any());
        doNothing().when(mimeMessage).setSubject(Mockito.<String>any());
        doNothing().when(javaMailSender).send(Mockito.<MimeMessage>any());
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        com.service.property.notificationservice.model.Message message = new com.service.property.notificationservice.model.Message();
        message.setFrom("jane.doe@example.org");
        message.setId(1L);
        message.setMessage("Not all who wander are lost");
        message.setSendDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        message.setTo("alice.liddell@example.org");
        when(messageRepository.save(Mockito.<com.service.property.notificationservice.model.Message>any()))
                .thenReturn(message);

        // Act
        Boolean actualSendTenantEmailResult = mailServiceImpl.sendTenantEmail("text/plain", "Not all who wander are lost");

        // Assert
        verify(mimeMessage).setRecipient(isA(jakarta.mail.Message.RecipientType.class), isA(Address.class));
        verify(mimeMessage).setContent(isA(Multipart.class));
        verify(mimeMessage).setFrom(isA(Address.class));
        verify(mimeMessage).setSubject(eq("Welcome Tenant"));
        verify(messageRepository).save(isA(com.service.property.notificationservice.model.Message.class));
        verify(javaMailSender).createMimeMessage();
        verify(javaMailSender).send(isA(MimeMessage.class));
        assertTrue(actualSendTenantEmailResult);
    }
}
