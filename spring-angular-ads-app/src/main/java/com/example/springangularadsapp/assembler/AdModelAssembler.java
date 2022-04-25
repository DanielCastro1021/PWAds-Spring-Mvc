package com.example.springangularadsapp.assembler;

import com.example.springangularadsapp.controller.AdController;
import com.example.springangularadsapp.models.Ad;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AdModelAssembler implements RepresentationModelAssembler<Ad, EntityModel<Ad>> {
    @Override
    public EntityModel<Ad> toModel(Ad ad) {
        return EntityModel.of(ad, linkTo(methodOn(AdController.class).one(ad.getId())).withSelfRel(), linkTo(methodOn(AdController.class).all()).withRel("ads"));
    }


}
