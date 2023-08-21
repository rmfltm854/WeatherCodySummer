package com.example.weathercodysummer.Service;

import com.example.weathercodysummer.Dto.MainImage;
import com.example.weathercodysummer.Dto.SteadyWomanMainImg;
import com.example.weathercodysummer.Entity.SteadyWomanSubImg;
import com.example.weathercodysummer.Entity.SubImage;
import com.example.weathercodysummer.Repository.CrawlingRepository;
import com.example.weathercodysummer.Repository.SteadyWomanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SteadyWomenMainImage {

    @Autowired
    SteadyWomanRepository Wrepository;

    @Autowired
    CrawlingRepository repo;

    public List<SteadyWomanMainImg> mainImageRank(){
        List<com.example.weathercodysummer.Entity.SteadyWomanMainImg> mainRankEntity = Wrepository.getRanking();
        List<com.example.weathercodysummer.Dto.SteadyWomanMainImg> mainRank = new ArrayList<>();
        for(int i =0;i< mainRankEntity.size();i++){
            mainRank.add(i,mainRankEntity.get(i).toDto());
        }
        return mainRank;
    }

    public com.example.weathercodysummer.Dto.SteadyWomanMainImg findMainSrc(Long id){
        com.example.weathercodysummer.Entity.SteadyWomanMainImg mainImage = repo.findMainSrc2(id);
        com.example.weathercodysummer.Dto.SteadyWomanMainImg toDto = mainImage.toDto();
        return toDto;
    }

    public List<com.example.weathercodysummer.Dto.SteadyWomanSubImg> detail(Long id){

        List<SteadyWomanSubImg> detailImages = repo.findSubImage2(id);
        List<com.example.weathercodysummer.Dto.SteadyWomanSubImg> toDto = detailImages.stream().map(SteadyWomanSubImg::toDto).collect(Collectors.toList());
        return toDto;
    }

    public int countLike(String src,String stat){
        int resultNum = 0;
        if(stat.equals("like")){
            int num = Wrepository.howLike(src)+1;
            System.out.println("like:" + num);
            Wrepository.likeNum(num,src);
            return num;

        }if (stat.equals("unlike")) {
            int num = Wrepository.howLike(src)-1;
            System.out.println("like:" + num);
            resultNum = Wrepository.likeNum(num,src);
            return num;
        }else{
            System.out.println("오류");
            return 0;
        }
    }
}
