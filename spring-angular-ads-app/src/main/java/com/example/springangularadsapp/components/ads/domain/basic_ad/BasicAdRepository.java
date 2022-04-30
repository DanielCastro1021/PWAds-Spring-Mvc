package com.example.springangularadsapp.components.ads.domain.basic_ad;

import com.example.springangularadsapp.components.ads.domain.ad.AdRepository;
import com.example.springangularadsapp.security.models.User;

import java.util.List;

public interface BasicAdRepository extends AdRepository<BasicAd> {
    List<BasicAd> findByOwner(User owner);
}
