package com.example.springangularadsapp.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface HateoasController<T> {

    public CollectionModel<EntityModel<T>> all();

    public EntityModel<T> one(@PathVariable String id);

    public ResponseEntity<?> save(@RequestBody T newEntity);

    public ResponseEntity<?> update(@RequestBody T newEntity, @PathVariable String id);

    public ResponseEntity<?> delete(@PathVariable String id);
}
