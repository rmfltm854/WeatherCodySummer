package com.example.weathercodysummer.Service;

import com.example.weathercodysummer.CrawlerModule.MusinsaManChildCrawler;
import com.example.weathercodysummer.CrawlerModule.MusinsaManCrawler;
import com.example.weathercodysummer.CrawlerModule.MusinsaWomenChildCrawler;
import com.example.weathercodysummer.CrawlerModule.MusinsaWomenCrawler;
import com.example.weathercodysummer.Entity.MusinasManSubEntity;
import com.example.weathercodysummer.Entity.MusinsaManEntity;
import com.example.weathercodysummer.Entity.MusinsaWomenEntity;
import com.example.weathercodysummer.Entity.MusinsaWomenSubEntity;
import com.example.weathercodysummer.Repository.MusinsaManRepository;
import com.example.weathercodysummer.Repository.MusinsaManSubRepository;
import com.example.weathercodysummer.Repository.MusinsaWomenRepository;
import com.example.weathercodysummer.Repository.MusinsaWomenSubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MusinsaWomenService {
    @Autowired
    MusinsaWomenRepository repo;

    @Autowired
    MusinsaWomenSubRepository subrepo;

    public  void getSubManImage() throws IOException {
        MusinsaWomenCrawler womenC = new MusinsaWomenCrawler();
        MusinsaWomenChildCrawler womenChild = new MusinsaWomenChildCrawler();
        List<HashMap<String,String>> women = womenC.setData();
        List<String> womenChildResult = new ArrayList<>();
        for(int j = 0;j<women.size();j++){
            MusinsaWomenEntity entity = new MusinsaWomenEntity();
            entity.setSrc(women.get(j).get("url"));
            entity.setLikeNum(0);
            repo.save(entity);
            womenChildResult = womenChild.getProductDetail(women.get(j).get("productDetailURL"));
            for(int i = 0;i<womenChildResult.size();i++){
                MusinsaWomenSubEntity subtity = new MusinsaWomenSubEntity();
                subtity.setSrc(womenChildResult.get(i));
                subtity.setImage(entity);
                subrepo.save(subtity);
            }
        }
    }

    public int countLike(String src,String stat){
        int resultNum = 0;
        if(stat.equals("like")){
            int num = repo.howLike(src)+1;
            System.out.println("like:" + num);
            repo.LikeNum(num,src);
            return num;

        }if (stat.equals("unlike")) {
            int num = repo.howLike(src)-1;
            System.out.println("like:" + num);
            resultNum = repo.LikeNum(num,src);
            return num;
        }else{
            System.out.println("오류");
            return 0;
        }
    }
}
