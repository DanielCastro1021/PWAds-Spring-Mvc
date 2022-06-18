package com.example.mvcpwads.security.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document(collection = "roles")
public class Role {
    @Id
    private String id;

    private ERole name;

    public Role() {

    }

    public Role(ERole name) {
        this.name = name;
    }
}