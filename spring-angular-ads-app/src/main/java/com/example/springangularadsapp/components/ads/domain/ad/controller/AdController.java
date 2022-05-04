package com.example.springangularadsapp.components.ads.domain.ad.controller;

import com.example.springangularadsapp.components.ads.assembler.AdModelAssembler;
import com.example.springangularadsapp.components.ads.domain.ad.Ad;
import com.example.springangularadsapp.components.ads.domain.ad.AdRepository;
import com.example.springangularadsapp.components.ads.domain.basic_ad.BasicAd;
import com.example.springangularadsapp.components.ads.domain.basic_ad.controller.BasicAdController;
import com.example.springangularadsapp.exceptions.AdNotFoundException;
import com.example.springangularadsapp.security.authorization.annotation.UserAccess;
import com.example.springangularadsapp.security.models.User;
import com.example.springangularadsapp.security.repository.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/ads")
public class AdController {
    private final AdRepository<Ad> repository;
    private final AdModelAssembler<Ad> assembler;
    private final UserRepository userRepository;

    public AdController(AdRepository<Ad> repository, AdModelAssembler<Ad> assembler, UserRepository userRepository) {
        this.repository = repository;
        this.assembler = assembler;
        this.userRepository = userRepository;
    }

    /**
     * Returns all Ads in repository, with HATEOAS links.
     *
     * @return CollectionModel of the Ad EntityModel.
     */
    @GetMapping("/all")
    public CollectionModel<EntityModel<Ad>> all() {
        List<EntityModel<Ad>> ads = this.repository.findAll().stream().map(this.assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(ads, linkTo(methodOn(AdController.class).all()).withSelfRel());
    }

    /**
     * Returns a specific Ad in repository, with HATEOAS links.
     *
     * @param id of an existing BasicAd
     * @return Ad EntityModel.
     */
    @GetMapping("/{id}")
    public EntityModel<Ad> one(@PathVariable String id) {
        Ad ad = this.repository.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        return this.assembler.toModel(ad);
    }

    /**
     * Return all Ads that the user, that made the GET request, owns.
     *
     * @return CollectionModel of the  Ad EntityModel.
     */
    @UserAccess
    @GetMapping("/personal")
    public CollectionModel<EntityModel<Ad>> getMyBasicAds() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = this.userRepository.findByUsername(userDetails.getUsername());

        List<EntityModel<Ad>> ads = this.repository.findByOwner(user.get()).stream().map(this.assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(ads, linkTo(methodOn(BasicAdController.class).all()).withSelfRel());
    }
}
