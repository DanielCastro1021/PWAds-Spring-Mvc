package com.example.springangularadsapp.dto;

import com.example.springangularadsapp.models.Ad;
import com.example.springangularadsapp.models.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@Setter
@Data
public class MessageDto {
    private String from;

    private String to;

    private String adId;

    private String message;
}
