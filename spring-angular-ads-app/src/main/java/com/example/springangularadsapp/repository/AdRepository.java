package com.example.springangularadsapp.repository;

import com.example.springangularadsapp.models.Ad;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdRepository<T extends Ad> extends MongoRepository<T, String> {
}
