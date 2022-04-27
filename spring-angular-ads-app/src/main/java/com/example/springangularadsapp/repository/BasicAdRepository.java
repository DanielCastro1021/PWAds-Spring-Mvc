package com.example.springangularadsapp.repository;

import com.example.springangularadsapp.models.BasicAd;
import com.example.springangularadsapp.models.User;

import java.util.List;

public interface BasicAdRepository extends AdRepository<BasicAd> {
    List<BasicAd> findByOwner(User owner);
}
