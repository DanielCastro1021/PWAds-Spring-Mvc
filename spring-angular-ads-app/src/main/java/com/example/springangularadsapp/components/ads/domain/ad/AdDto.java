package com.example.springangularadsapp.components.ads.domain.ad;

import lombok.*;
import org.bson.types.Binary;

import java.util.List;

@Getter
@Setter
@ToString
@Data
public class AdDto {
    private List<String> imageList;
    private String ownerUsername;
}
