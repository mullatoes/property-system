package com.service.property.notificationservice.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Objects;
import java.util.Properties;

@Configuration
public class MailConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public JavaMailSender getMailSender() {
        if (environment == null) {
            System.out.println("environment is null");
            throw new IllegalStateException("Environment object is not properly injected.");
        }

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(environment.getProperty("spring.mail.host"));
        String port = environment.getProperty("spring.mail.port");
        if (port != null) {
            javaMailSender.setPort(Integer.parseInt(port));
        }
        javaMailSender.setUsername(environment.getProperty("spring.mail.username"));
        javaMailSender.setPassword(environment.getProperty("spring.mail.password"));

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");
        javaMailProperties.put("mail.smtp.ssl.trust", "*");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }
}
