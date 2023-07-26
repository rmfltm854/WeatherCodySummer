package com.example.weathercodysummer.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.example.weathercodysummer.Entity.SignUpEntity;

@Getter @Setter
@ToString
public class SignUp {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String userId;
    @NotEmpty
    private String userPassword;
    @NotEmpty
    private String userEmail;

    public SignUp(String userName, String userId, String userPassword, String userEmail) {
        this.userName = userName;
        this.userId = userId;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
    }

    public SignUp(){

    }

    public SignUpEntity toEntity(){
        return new SignUpEntity(null, userName, userId, userPassword, userEmail);
    }
}
