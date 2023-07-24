package com.example.weathercodysummer.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@ToString
public class Login {

    @NotEmpty
    private String userId;
    @NotEmpty
    private String userPassword;

    public Login(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public Login(){

    }
}
