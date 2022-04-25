package com.example.springangularadsapp.repository;

import com.example.springangularadsapp.models.Ad;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdRepository extends MongoRepository<Ad, String>{
        }
