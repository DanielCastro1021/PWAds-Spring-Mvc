package com.example.springangularadsapp.controller;

import com.example.springangularadsapp.assembler.AdModelAssembler;
import com.example.springangularadsapp.dto.AdDTO;
import com.example.springangularadsapp.exceptions.AdNotFoundException;
import com.example.springangularadsapp.models.Ad;
import com.example.springangularadsapp.repository.AdRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/ads")
public class AdController implements HateoasController<Ad> {
    private final AdRepository adRepository;
    private final AdModelAssembler assembler;

    public AdController(AdRepository adRepository, AdModelAssembler assembler) {
        this.adRepository = adRepository;
        this.assembler = assembler;
    }

    @Override
    @GetMapping("/")
    public CollectionModel<EntityModel<Ad>> all() {
        List<EntityModel<Ad>> ads = this.adRepository.findAll().stream().map(this.assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(ads, linkTo(methodOn(AdController.class).all()).withSelfRel());
    }

    @Override
    @GetMapping("/{id}")
    public EntityModel<Ad> one(@PathVariable String id) {
        Ad ad = this.adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        return this.assembler.toModel(ad);
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody Ad newEntity) {
        EntityModel<Ad> entityModel = this.assembler.toModel(this.adRepository.save(newEntity));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Ad newEntity, @PathVariable String id) {
        Ad updatedAd = this.adRepository.findById(id).map(ad -> {
            ad.setTitle(newEntity.getTitle());
            ad.setDescription(newEntity.getDescription());
            ad.setOwner(newEntity.getOwner());
            ad.setImageList(newEntity.getImageList());
            return this.adRepository.save(ad);
        }).orElseGet(() -> {
            newEntity.setId(id);
            return this.adRepository.save(newEntity);
        });

        EntityModel<Ad> entityModel = this.assembler.toModel(updatedAd);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        this.adRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
