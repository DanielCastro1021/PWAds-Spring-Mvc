package com.example.springangularadsapp.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@TypeAlias("car")
@Document(collection = "ads")
public class CarAd extends Ad {
    private String maker;
    private String model;
    private int year;
}
