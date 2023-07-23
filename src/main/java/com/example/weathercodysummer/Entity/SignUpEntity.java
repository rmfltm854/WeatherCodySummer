package com.example.weathercodysummer.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import com.example.weathercodysummer.Dto.Login;
import com.example.weathercodysummer.Dto.SignUp;

@Entity
@Getter
@Table(name = "memberInfo")

public class SignUpEntity {

    @Id
    @GeneratedValue
    @Column(name = "mem_id")
    private Long id;

    private String userName;
    private String userId;
    private String userPassword;
    private String userEmail;

    public SignUpEntity(Long id, String userName, String userId, String userPassword, String userEmail) {
        this.id = id;
        this.userName = userName;
        this.userId = userId;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
    }

    public SignUpEntity(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public SignUpEntity(){

    }

    public SignUp toDto(){
        return new SignUp(userName, userId, userPassword, userEmail);
    }


    public Login toLogin(){
        return new Login(userId,userPassword);
    }
}