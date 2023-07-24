package com.example.weathercodysummer.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.example.weathercodysummer.Entity.SignUpEntity;

@Getter @Setter
@ToString
public class SignUp {

    private String userName;
    private String userId;
    private String userPassword;
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
