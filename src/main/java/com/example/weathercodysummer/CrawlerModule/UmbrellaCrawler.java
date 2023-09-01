package com.example.weathercodysummer.CrawlerModule;

import com.example.weathercodysummer.Entity.UmbrellaEntity;
import com.example.weathercodysummer.Repository.UmbrellaRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UmbrellaCrawler {
    String wtUrl = "https://www.musinsa.com/search/musinsa/goods?q=%EC%9E%A5%EC%9A%B0%EC%82%B0&list_kind=small&sortCode=pop&sub_sort=&page=1&display_cnt=0&saleGoods=&includeSoldOut=&setupGoods=&popular=&category1DepthCode=&category2DepthCodes=&category3DepthCodes=&selectedFilters=&category1DepthName=&category2DepthName=&brandIds=&price=&colorCodes=&contentType=&styleTypes=&includeKeywords=&excludeKeywords=&originalYn=N&tags=&campaignId=&serviceType=&eventType=&type=&season=&measure=&openFilterLayout=N&selectedOrderMeasure=&shoeSizeOption=&groupSale=&d_cat_cd=&attribute=&plusDeliveryYn=";
    public List<String> Umbrella(){
        List<String> umbrella = new ArrayList<>();
        try {
            Document Musinsa = Jsoup.connect(wtUrl).get();
            Elements imgUrl = Musinsa.select(".li_inner > .list_img > .img-block > img");
            for(int i =0;i<imgUrl.size();i++){
                String src = "https:" + imgUrl.get(i).attr("data-original");
                umbrella.add(src);
            }
        }catch(IOException e){

        }
        return umbrella;
    }






}
