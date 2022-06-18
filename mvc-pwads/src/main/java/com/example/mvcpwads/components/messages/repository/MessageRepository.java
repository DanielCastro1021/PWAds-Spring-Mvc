package com.example.mvcpwads.components.messages.repository;

import com.example.mvcpwads.components.messages.model.Message;
import com.example.mvcpwads.security.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByTo(User to);

    List<Message> findByFrom(User from);
}
