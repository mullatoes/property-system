package com.service.property.notificationservice.repository;

import com.service.property.notificationservice.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
