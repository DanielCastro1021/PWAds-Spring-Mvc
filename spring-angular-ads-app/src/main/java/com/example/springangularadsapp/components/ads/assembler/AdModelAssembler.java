package com.example.springangularadsapp.components.ads.assembler;

import com.example.springangularadsapp.components.ads.basic_ad.BasicAdController;
import com.example.springangularadsapp.components.ads.car_ad.CarAdController;
import com.example.springangularadsapp.components.ads.ad.Ad;
import com.example.springangularadsapp.components.ads.basic_ad.BasicAd;
import com.example.springangularadsapp.components.ads.car_ad.CarAd;
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
            return EntityModel.of(entity, WebMvcLinkBuilder.linkTo(methodOn(BasicAdController.class).one(entity.getId())).withSelfRel(), linkTo(methodOn(BasicAdController.class).all()).withRel("basic-ads"));
        else if (entity.getClass() == CarAd.class)
            return EntityModel.of(entity, WebMvcLinkBuilder.linkTo(methodOn(CarAdController.class).one(entity.getId())).withSelfRel(), linkTo(methodOn(CarAdController.class).all()).withRel("car-ads"));


        return null;
    }
}

