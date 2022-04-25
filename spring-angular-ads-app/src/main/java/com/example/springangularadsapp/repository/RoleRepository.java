package com.example.springangularadsapp.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.springangularadsapp.enumerations.ERole;
import com.example.springangularadsapp.models.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);

    Boolean existsByName(ERole name);

}
