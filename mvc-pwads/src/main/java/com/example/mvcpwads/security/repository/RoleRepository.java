package com.example.mvcpwads.security.repository;

import java.util.Optional;

import com.example.mvcpwads.security.models.ERole;
import com.example.mvcpwads.security.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);

    Boolean existsByName(ERole name);
}
