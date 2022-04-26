package com.example.springangularadsapp.assembler;

import com.example.springangularadsapp.controller.BasicAdController;
import com.example.springangularadsapp.controller.CarAdController;
import com.example.springangularadsapp.models.Ad;
import com.example.springangularadsapp.models.BasicAd;
import com.example.springangularadsapp.models.CarAd;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AdModelAssembler<T extends Ad> implements RepresentationModelAssembler<T, EntityModel<T>> {
    @Override
    public EntityModel<T> toModel(T entity) {
        if (entity.getClass() == BasicAd.class)
            return EntityModel.of(entity, linkTo(methodOn(BasicAdController.class).one(entity.getId())).withSelfRel(), linkTo(methodOn(BasicAdController.class).all()).withRel("basic-ads"));
        else if (entity.getClass() == CarAd.class)
            return EntityModel.of(entity, linkTo(methodOn(CarAdController.class).one(entity.getId())).withSelfRel(), linkTo(methodOn(CarAdController.class).all()).withRel("car-ads"));


        return null;
    }
}

