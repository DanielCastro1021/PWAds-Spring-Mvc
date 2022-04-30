package com.example.springangularadsapp.components.ads.domain.ad;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdRepository<T extends Ad> extends MongoRepository<T, String> {
}
