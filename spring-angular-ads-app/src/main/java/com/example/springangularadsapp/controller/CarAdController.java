package com.example.springangularadsapp.controller;

import com.example.springangularadsapp.assembler.AdModelAssembler;
import com.example.springangularadsapp.constants.annotation.UserAccess;
import com.example.springangularadsapp.constants.exceptions.AdNotFoundException;
import com.example.springangularadsapp.dto.CarAdDto;
import com.example.springangularadsapp.factory.AdRepositoryFactory;
import com.example.springangularadsapp.mapper.AdMapper;
import com.example.springangularadsapp.models.CarAd;
import com.example.springangularadsapp.repository.CarAdRepository;
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
@RequestMapping("/api/ads/cars")
public class CarAdController implements HateoasController<CarAd, CarAdDto> {
    private final CarAdRepository repository;
    private final AdModelAssembler<CarAd> assembler;
    private final AdMapper mapper;

    public CarAdController(CarAdRepository repository, AdModelAssembler<CarAd> assembler, AdMapper mapper) {
        this.repository = repository;
        this.assembler = assembler;
        this.mapper = mapper;
    }

    @Override
    @GetMapping("/")
    public CollectionModel<EntityModel<CarAd>> all() {
        List<EntityModel<CarAd>> ads = this.repository.findAll().stream().map(this.assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(ads, linkTo(methodOn(CarAdController.class).all()).withSelfRel());
    }

    @Override
    @GetMapping("/{id}")
    public EntityModel<CarAd> one(@PathVariable String id) {
        CarAd carAd = (CarAd) this.repository.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        return this.assembler.toModel(carAd);
    }

    @UserAccess
    @Override
    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody CarAdDto newEntity) {
        EntityModel<CarAd> entityModel = this.assembler.toModel(this.repository.save(this.mapper.carAdDtoToCarAd(newEntity)));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @UserAccess
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody CarAdDto newEntity, @PathVariable String id) {
        //TODO: Check if user that made this request is the owner
        CarAd newCarAd = this.mapper.carAdDtoToCarAd(newEntity);
        CarAd updatedAd = this.repository.findById(id).map(carAd -> {
            carAd.setMaker(newCarAd.getMaker());
            carAd.setModel(newCarAd.getModel());
            carAd.setYear(newCarAd.getYear());
            carAd.setOwner(newCarAd.getOwner());
            carAd.setImageList(newCarAd.getImageList());
            return repository.save(carAd);
        }).orElseGet(() -> {
            newCarAd.setId(id);
            return this.repository.save(newCarAd);
        });

        EntityModel<CarAd> entityModel = this.assembler.toModel(updatedAd);
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
