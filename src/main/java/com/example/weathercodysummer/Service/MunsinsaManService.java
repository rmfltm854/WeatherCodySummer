package com.example.weathercodysummer.Service;

import com.example.weathercodysummer.CrawlerModule.MusinsaManChildCrawler;
import com.example.weathercodysummer.CrawlerModule.MusinsaManCrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MunsinsaManService {
    MusinsaManCrawler manC = new MusinsaManCrawler();

    MusinsaManChildCrawler manChild = new MusinsaManChildCrawler();

   public void getSubManImage() throws IOException{
       List<HashMap<String,String>> man = manC.setData();
       List<String> manChildResult = new ArrayList<>();
       for(int i = 0;i<man.size();i++){
           manChildResult = manChild.getProductDetail(man.get(i).get("productDetailURL"));
           System.out.println("++++++++++++++++++++"+i+"번째child+++++++++++++++++++++++");
           System.out.println("mainURL : " +man.get(i).get("productDetailURL") );
           for(int j = 0;j<manChildResult.size();j++){
               System.out.println(manChildResult.get(j));
           }
           System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
       }
   }
}
