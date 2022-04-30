package com.example.springangularadsapp.components.ads.assembler;

import com.example.springangularadsapp.components.ads.domain.basic_ad.controller.BasicAdController;
import com.example.springangularadsapp.components.ads.domain.car_ad.controller.CarAdController;
import com.example.springangularadsapp.components.ads.domain.ad.Ad;
import com.example.springangularadsapp.components.ads.domain.basic_ad.BasicAd;
import com.example.springangularadsapp.components.ads.domain.car_ad.CarAd;
import com.example.springangularadsapp.components.message.controller.MessageController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AdModelAssembler<T extends Ad> implements RepresentationModelAssembler<T, EntityModel<T>> {
    @Override
    public EntityModel<T> toModel(T entity) {
        if (entity.getClass() == BasicAd.class)
            return EntityModel.of(entity, WebMvcLinkBuilder.
                            linkTo(methodOn(BasicAdController.class).one(entity.getId())).withSelfRel(),
                            linkTo(methodOn(BasicAdController.class).all()).withRel("allBasicAds")
            );
        else if (entity.getClass() == CarAd.class)
            return EntityModel.of(entity, WebMvcLinkBuilder.
                    linkTo(methodOn(CarAdController.class).one(entity.getId())).withSelfRel(),
                    linkTo(methodOn(CarAdController.class).all()).withRel("allCarAds")
            );
        return null;
    }
}

