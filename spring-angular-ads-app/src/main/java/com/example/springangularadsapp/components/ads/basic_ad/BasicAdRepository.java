package com.example.springangularadsapp.components.ads.basic_ad;

import com.example.springangularadsapp.components.ads.ad.AdRepository;
import com.example.springangularadsapp.security.models.User;

import java.util.List;

public interface BasicAdRepository extends AdRepository<BasicAd> {
    List<BasicAd> findByOwner(User owner);
}
