package com.example.mvcpwads.audit;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatsLogRepository extends MongoRepository<StatsLog, String> {
}
