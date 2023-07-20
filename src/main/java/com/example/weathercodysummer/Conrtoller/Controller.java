package com.example.weathercodysummer.Conrtoller;

import com.example.weathercodysummer.Service.Crawling2Service;
import com.example.weathercodysummer.Service.Crawling3Service;
import com.example.weathercodysummer.Service.Crawling4Service;
import com.example.weathercodysummer.Service.Crawling4ServiceMadeByJMS;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    Crawling2Service service2 = new Crawling2Service();
    Crawling3Service service3 = new Crawling3Service();

    Crawling4Service service4 = new Crawling4Service();
    Crawling4ServiceMadeByJMS jms = new Crawling4ServiceMadeByJMS();
    @GetMapping("/")
    @ResponseBody
    public List<HashMap<String,List<String>>> Test(){
//        List<String> list = service4.main5();
        List<HashMap<String,List<String>>> list = jms.main5();
        return list;
    }


}
