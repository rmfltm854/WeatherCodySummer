package com.example.weathercodysummer.Conrtoller;

import com.example.weathercodysummer.Service.Crawling2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    Crawling2Service service = new Crawling2Service();

    @GetMapping("/")
    @ResponseBody
    public List<String> Test(){
        List<String> list = service.main();
        return list;
    }


}
