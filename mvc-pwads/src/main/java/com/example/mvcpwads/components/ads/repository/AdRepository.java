package com.example.mvcpwads.components.ads.repository;

import com.example.mvcpwads.components.ads.model.ad.Ad;
import com.example.mvcpwads.security.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AdRepository<T extends Ad> extends MongoRepository<T, String> {
    List<T> findByOwner(User owner);
}

