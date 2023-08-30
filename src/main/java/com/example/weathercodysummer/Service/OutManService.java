package com.example.weathercodysummer.Service;

import com.example.weathercodysummer.Entity.OutManImage;
import com.example.weathercodysummer.Repository.OutManRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutManService {

    @Autowired
    OutManRepository repository;

    public List<OutManImage> allOutSRC(){
        List<OutManImage> list = repository.findAll();
        for(int i = 0;i<list.size();i++){
            System.out.println("___________________________");
            System.out.println(list.get(i).getId());
            System.out.println(list.get(i).getSrc());
            System.out.println("___________________________");
        }

        return list;
    }

}
