package com.example.springangularadsapp.dto;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class AdDTO {
    @Id
    private String id;
    @NotBlank
    @Size(min = 3, max = 60)
    private String title;
    @NotBlank
    @Size(max = 150)
    private String description;
    private List<Binary> imageList;
    private String ownerUsername;


}
