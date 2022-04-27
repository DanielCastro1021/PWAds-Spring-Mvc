package com.example.springangularadsapp.components.stats_logs;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatsLogRepository extends MongoRepository<StatsLog, String> {
}
