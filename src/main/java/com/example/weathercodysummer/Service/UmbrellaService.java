package com.example.weathercodysummer.Service;

import com.example.weathercodysummer.CrawlerModule.UmbrellaCrawler;
import com.example.weathercodysummer.Entity.UmbrellaEntity;
import com.example.weathercodysummer.Repository.UmbrellaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UmbrellaService {
    @Autowired
    UmbrellaRepository repo;
    UmbrellaCrawler crawler = new UmbrellaCrawler();
    public void setUmbrella(){
        List<String> result = crawler.Umbrella();
        for(int i = 0;i<result.size();i++){
            UmbrellaEntity entity = new UmbrellaEntity(result.get(i));
            repo.save(entity);
        }
    }
}
