package com.example.springangularadsapp.components.ads.domain.car_ad;

import com.example.springangularadsapp.components.ads.domain.ad.AdRepository;
import com.example.springangularadsapp.security.models.User;

import java.util.List;

public interface CarAdRepository extends AdRepository<CarAd> {
    List<CarAd> findByOwner(User owner);
}
