package com.example.weathercodysummer.Conrtoller;

import com.example.weathercodysummer.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {//윤서 등장

    @Autowired
    WeahterCodyService service;

    @GetMapping("/")
    @ResponseBody
    public void Test(){
        service.Test();
    }


    @GetMapping("/Main")
    public String mainPage(){
        return "product3madeByJms";
    }

    @GetMapping("/Login")
    public String loginPage(){
        return "login";
    }


    //hello
    @GetMapping("/SignUp")
    public String signUp(){
        return "signUp";
    }


}
