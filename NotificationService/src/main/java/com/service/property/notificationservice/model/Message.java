package com.service.property.notificationservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "send_messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "sender")
    private String from;

    @Column(name = "receiver")
    private String to;

    @Column(name = "send_date")
    private Date sendDate;

}
