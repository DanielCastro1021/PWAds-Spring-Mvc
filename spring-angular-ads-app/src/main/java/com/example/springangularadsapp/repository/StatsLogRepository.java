package com.example.springangularadsapp.repository;

import com.example.springangularadsapp.models.StatsLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatsLogRepository extends MongoRepository<StatsLog, String> {
}
