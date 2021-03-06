package com.example.mvcpwads.components.ads.factory;


import com.example.mvcpwads.components.ads.model.ad.AdDto;
import com.example.mvcpwads.components.ads.model.basic_ad.BasicAdDto;
import com.example.mvcpwads.components.ads.model.car_ad.CarAdDto;
import com.example.mvcpwads.components.ads.model.ad.Ad;
import com.example.mvcpwads.components.ads.model.basic_ad.BasicAd;
import com.example.mvcpwads.components.ads.model.car_ad.CarAd;
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
