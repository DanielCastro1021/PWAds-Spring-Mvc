package com.example.springangularadsapp.repository;

import com.example.springangularadsapp.models.CarAd;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarAdRepository extends MongoRepository<CarAd, String> {

}
