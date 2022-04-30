package com.example.springangularadsapp.components.ads.domain.car_ad.controller;

import com.example.springangularadsapp.components.ads.assembler.AdModelAssembler;
import com.example.springangularadsapp.components.ads.domain.car_ad.CarAd;
import com.example.springangularadsapp.components.ads.domain.car_ad.CarAdDto;
import com.example.springangularadsapp.components.ads.domain.car_ad.CarAdRepository;
import com.example.springangularadsapp.components.ads.mapper.AdMapper;
import com.example.springangularadsapp.exceptions.UserNotFoundException;
import com.example.springangularadsapp.security.authorization.annotation.UserAccess;
import com.example.springangularadsapp.exceptions.AdNotFoundException;
import com.example.springangularadsapp.constants.HateoasController;
import com.example.springangularadsapp.security.models.User;
import com.example.springangularadsapp.security.repository.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/ads/cars")
public class CarAdController implements HateoasController<CarAd, CarAdDto> {
    private final CarAdRepository repository;
    private final UserRepository userRepository;
    private final AdModelAssembler<CarAd> assembler;
    private final AdMapper mapper;

    public CarAdController(CarAdRepository repository, UserRepository userRepository, AdModelAssembler<CarAd> assembler, AdMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.assembler = assembler;
        this.mapper = mapper;
    }

    /**
     * Returns all CarAds in repository, with HATEOAS links.
     *
     * @return CollectionModel of the CarAd EntityModel.
     */
    @GetMapping("/")
    public CollectionModel<EntityModel<CarAd>> all() {
        List<EntityModel<CarAd>> ads = this.repository.findAll().stream().map(this.assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(ads, linkTo(methodOn(CarAdController.class).all()).withSelfRel());
    }

    /**
     * Returns a specific CarAds in repository, with HATEOAS links.
     *
     * @param id of an existing BasicAd
     * @return BasicAd EntityModel.
     */
    @GetMapping("/{id}")
    public EntityModel<CarAd> one(@PathVariable String id) {
        CarAd carAd = this.repository.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        return this.assembler.toModel(carAd);
    }

    /**
     * Saves a CarAd using as argument a CarAdDto.
     *
     * @param newEntity a CarAdDto instance.
     * @return EntityModel of CarAd with HATEOAS links.
     * @throws UserNotFoundException If username of the user that made the POST request is not found.
     */
    @UserAccess
    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody CarAdDto newEntity) throws UserNotFoundException {
        EntityModel<CarAd> entityModel = this.assembler.toModel(this.repository.save(this.mapper.carAdDtoToCarAd(newEntity)));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    /**
     * Updates a CarAd with specific id with values of a CarAdDto instance.
     *
     * @param newEntity CarAdDto instance.
     * @param id        of an existing CarAd
     * @return EntityModel of CarAd with HATEOAS links.
     * @throws UserNotFoundException If username of the user that made the POST request is not found.
     */
    @UserAccess
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody CarAdDto newEntity, @PathVariable String id) throws UserNotFoundException {
        CarAd newAd = this.mapper.carAdDtoToCarAd(newEntity);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        if (!newAd.checkOwner(userDetails.getUsername()) || !request.isUserInRole("ROLE_ADMIN"))
            throw new AdNotFoundException(userDetails.getUsername(), "doesn't own this ad:" + newAd);
        else {

            CarAd updatedAd = this.repository.findById(id).map(carAd -> {
                carAd.setMaker(newAd.getMaker());
                carAd.setModel(newAd.getModel());
                carAd.setYear(newAd.getYear());
                carAd.setOwner(newAd.getOwner());
                carAd.setImageList(newAd.getImageList());
                return repository.save(carAd);
            }).orElseGet(() -> {
                newAd.setId(id);
                return this.repository.save(newAd);
            });

            EntityModel<CarAd> entityModel = this.assembler.toModel(updatedAd);
            return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
        }
    }

    /* *
     * Deletes CarAd if the user owns the ad or possesses the Admin access.
     *
     * @param id of an existing
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        CarAd ad = this.repository.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        if (ad.checkOwner(userDetails.getUsername()) || request.isUserInRole("ROLE_ADMIN")) {
            this.repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else throw new AdNotFoundException(userDetails.getUsername(), "doesn't own this ad:" + ad);

    }

    /**
     * Return all CarAds that the user, that made the GET request, owns.
     *
     * @return CollectionModel of the CarAd EntityModel.
     */
    @UserAccess
    @GetMapping("/personal")
    public CollectionModel<EntityModel<CarAd>> getMyCarAds() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = this.userRepository.findByUsername(userDetails.getUsername());
        List<EntityModel<CarAd>> ads = this.repository.findByOwner(user.get()).stream().map(this.assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(ads, linkTo(methodOn(CarAdController.class).all()).withSelfRel());
    }
}
