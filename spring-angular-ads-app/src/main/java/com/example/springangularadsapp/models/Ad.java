package com.example.springangularadsapp.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document(collection = "ads")
public class Ad {

    @Id
    private String id;

    @NotBlank
    @Size(max = 60)
    private String title;

    @NotBlank
    @Size(max = 150)
    private String description;


    private List<Binary> imageList;

    @DBRef
    private User owner;

    public Ad() {
    }

    public Ad(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
