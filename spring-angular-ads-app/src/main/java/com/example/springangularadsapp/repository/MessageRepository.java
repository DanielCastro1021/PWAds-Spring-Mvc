package com.example.springangularadsapp.repository;

import com.example.springangularadsapp.models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
}
