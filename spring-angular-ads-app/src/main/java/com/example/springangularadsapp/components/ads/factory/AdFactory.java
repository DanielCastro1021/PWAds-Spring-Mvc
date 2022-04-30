package com.example.springangularadsapp.components.ads.factory;


import com.example.springangularadsapp.components.ads.domain.ad.AdDto;
import com.example.springangularadsapp.components.ads.domain.basic_ad.BasicAdDto;
import com.example.springangularadsapp.components.ads.domain.car_ad.CarAdDto;
import com.example.springangularadsapp.components.ads.domain.ad.Ad;
import com.example.springangularadsapp.components.ads.domain.basic_ad.BasicAd;
import com.example.springangularadsapp.components.ads.domain.car_ad.CarAd;
import org.springframework.stereotype.Component;

@Component
public class AdFactory {
    public Ad getAdInstance(Class<? extends AdDto> entity) {
        if (entity == BasicAdDto.class) {
            return new BasicAd();
        }

        if (entity == CarAdDto.class) {
            return new CarAd();
        }
        return null;
    }
}
