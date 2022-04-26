package com.example.springangularadsapp.factory;


import com.example.springangularadsapp.dto.AdDto;
import com.example.springangularadsapp.dto.BasicAdDto;
import com.example.springangularadsapp.dto.CarAdDto;
import com.example.springangularadsapp.models.Ad;
import com.example.springangularadsapp.models.BasicAd;
import com.example.springangularadsapp.models.CarAd;
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
