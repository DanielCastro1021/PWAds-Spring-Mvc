package com.example.springangularadsapp.factory;

import com.example.springangularadsapp.models.Ad;
import com.example.springangularadsapp.models.BasicAd;
import com.example.springangularadsapp.models.CarAd;
import com.example.springangularadsapp.repository.AdRepository;
import com.example.springangularadsapp.repository.BasicAdRepository;
import com.example.springangularadsapp.repository.CarAdRepository;
import org.springframework.stereotype.Component;

@Component
public class AdRepositoryFactory {
    private final BasicAdRepository basicAdRepository;
    private final CarAdRepository carAdRepository;

    public AdRepositoryFactory(BasicAdRepository basicAdRepository, CarAdRepository carAdRepository) {
        this.basicAdRepository = basicAdRepository;
        this.carAdRepository = carAdRepository;
    }

    public AdRepository<?> getRepository(Class<? extends Ad> type) {
        if (type == CarAd.class) return this.carAdRepository;
        else if (type == BasicAd.class) return this.basicAdRepository;
        return null ;
    }
}
