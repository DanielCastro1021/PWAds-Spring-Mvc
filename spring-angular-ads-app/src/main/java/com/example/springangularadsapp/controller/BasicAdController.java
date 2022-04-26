package com.example.springangularadsapp.controller;

import com.example.springangularadsapp.assembler.AdModelAssembler;
import com.example.springangularadsapp.constants.annotation.UserAccess;
import com.example.springangularadsapp.constants.exceptions.AdNotFoundException;
import com.example.springangularadsapp.constants.exceptions.UserNotFoundException;
import com.example.springangularadsapp.dto.BasicAdDto;
import com.example.springangularadsapp.mapper.AdMapper;
import com.example.springangularadsapp.models.BasicAd;
import com.example.springangularadsapp.repository.BasicAdRepository;
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
@RequestMapping("/api/ads/basic")
public class BasicAdController implements HateoasController<BasicAd, BasicAdDto> {
    private final BasicAdRepository repository;
    private final AdModelAssembler<BasicAd> assembler;
    private final AdMapper mapper;

    public BasicAdController(BasicAdRepository repository, AdModelAssembler<BasicAd> assembler, AdMapper mapper) {
        this.repository = repository;
        this.assembler = assembler;
        this.mapper = mapper;
    }

    @Override
    @GetMapping("/")
    public CollectionModel<EntityModel<BasicAd>> all() {
        List<EntityModel<BasicAd>> ads = this.repository.findAll().stream().map(this.assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(ads, linkTo(methodOn(BasicAdController.class).all()).withSelfRel());
    }

    @Override
    @GetMapping("/{id}")
    public EntityModel<BasicAd> one(@PathVariable String id) {
        BasicAd basicAd = this.repository.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        return this.assembler.toModel(basicAd);
    }

    @UserAccess
    @Override
    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody BasicAdDto newEntity) throws UserNotFoundException {
        EntityModel<BasicAd> entityModel = this.assembler.toModel(this.repository.save(this.mapper.basicAdDtoToBasicAd(newEntity)));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }


    @UserAccess
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody BasicAdDto newEntity, @PathVariable String id) throws UserNotFoundException {
        //TODO: Check if user that made this request is the owner
        BasicAd newAd = this.mapper.basicAdDtoToBasicAd(newEntity);
        BasicAd updatedAd = this.repository.findById(id).map(basicAd -> {
            basicAd.setTitle(newAd.getTitle());
            basicAd.setDescription(newAd.getDescription());
            basicAd.setOwner(newAd.getOwner());
            basicAd.setImageList(newAd.getImageList());
            return repository.save(basicAd);
        }).orElseGet(() -> {
            newAd.setId(id);
            return repository.save(newAd);
        });

        EntityModel<BasicAd> entityModel = this.assembler.toModel(updatedAd);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        //TODO: Check if user that made this request is the owner
        this.repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
