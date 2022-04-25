package com.example.springangularadsapp.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document(collection = "messages")
public class Message {
    @Id
    private String id;

    @DBRef
    private User from;

    @DBRef
    private User to;

    @DBRef
    private Ad ad;

    private String message;

    public Message() {
    }

}
