package com.example.weathercodysummer.Service;

import com.example.weathercodysummer.Entity.MainImage;
import com.example.weathercodysummer.Repository.MainImageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainImageService {
    @Autowired
    MainImageRepo repo;
    public List<com.example.weathercodysummer.Dto.MainImage> mainImageRank(){
        List<MainImage> mainRankEntity = repo.getRanking();
        List<com.example.weathercodysummer.Dto.MainImage> mainRank = new ArrayList<>();
        for(int i =0;i< mainRankEntity.size();i++){
            mainRank.add(i,mainRankEntity.get(i).toDto());
        }
        return mainRank;
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
