package com.example.weathercodysummer.Service;

import com.example.weathercodysummer.CrawlerModule.MusinsaManChildCrawler;
import com.example.weathercodysummer.CrawlerModule.MusinsaManCrawler;
import com.example.weathercodysummer.Entity.*;
import com.example.weathercodysummer.Repository.CrawlingRepository;
import com.example.weathercodysummer.Repository.MusinsaManRepository;
import com.example.weathercodysummer.Repository.MusinsaManSubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MunsinsaManService {

    @Autowired
    private CrawlingRepository Crepo;

    @Autowired
    MusinsaManRepository repo;

    @Autowired
    MusinsaManSubRepository subrepo;

    public  void getSubManImage() throws IOException{
        MusinsaManCrawler manC = new MusinsaManCrawler();
        MusinsaManChildCrawler manChild = new MusinsaManChildCrawler();
       List<HashMap<String,String>> man = manC.setData();
       List<String> manChildResult = new ArrayList<>();
       for(int j = 0;j<man.size();j++){
           MusinsaManEntity entity = new MusinsaManEntity();
           entity.setSrc(man.get(j).get("url"));
           entity.setLikeNum(0);
           repo.save(entity);
           manChildResult = manChild.getProductDetail(man.get(j).get("productDetailURL"));
           for(int i = 0;i<manChildResult.size();i++){
               MusinasManSubEntity subtity = new MusinasManSubEntity();
               subtity.setSrc(manChildResult.get(i));
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

    public void saveReview(String text, String src, String c,String gender) {
        if (gender.equals("man")) {
            SignUpEntity b = Crepo.b(c);
            Review review = new Review();
            review.setSrc(src);
            review.setReviewByUser(b);
            review.setReview(text);
            Crepo.saveReview(review);
        } else if (gender.equals("women")) {
            SignUpEntity b = Crepo.b(c);
            Review review = new Review();
            review.setSrc(src);
            review.setReviewByUser(b);
            review.setReview(text);
            Crepo.saveReview(review);


        }
    }



}
