package com.example.springangularadsapp.components.ads.domain.ad;

import com.example.springangularadsapp.components.ads.domain.basic_ad.BasicAd;
import com.example.springangularadsapp.security.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AdRepository<T extends Ad> extends MongoRepository<T, String> {
    List<T> findByOwner(User owner);
}
