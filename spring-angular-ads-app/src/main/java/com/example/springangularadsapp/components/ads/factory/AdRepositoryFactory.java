package com.example.springangularadsapp.components.ads.factory;

import com.example.springangularadsapp.components.ads.ad.Ad;
import com.example.springangularadsapp.components.ads.basic_ad.BasicAd;
import com.example.springangularadsapp.components.ads.car_ad.CarAd;
import com.example.springangularadsapp.components.ads.ad.AdRepository;
import com.example.springangularadsapp.components.ads.basic_ad.BasicAdRepository;
import com.example.springangularadsapp.components.ads.car_ad.CarAdRepository;
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
