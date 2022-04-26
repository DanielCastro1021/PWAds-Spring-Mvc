package com.example.springangularadsapp.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.Binary;

import java.util.List;

@Getter
@Setter
@ToString
public class AdDto {
    private List<Binary> imageList;
}