package com.example.weathercodysummer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
public class WeahterCodyService {

    @Autowired
    CrawlingService jms;

    public void Test(){
        List<HashMap<String,List<String>>> totalResultURL = jms.steadyClubMan();//main5메소드 반환받는다.

        for(int i = 0;i<totalResultURL.size();i++){
            String mainImageURL = totalResultURL.get(i).keySet().toString();
            List<List<String>> subImageURL = new ArrayList<>(totalResultURL.get(i).values());//HashMap value값을 전체반환값을 반환받아야하는데 반환형식이 배열로 주기때문에 List(List(value)) 로 된다.
            List<String> subImage = subImageURL.get(0);//value 로 반환하는 배열길이는 항상1이기때문에 get(0) 으로 고정
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("메인URL : " + mainImageURL);//mainImageURL 확인
            for(int j = 0;j<subImage.size();j++){
                System.out.println("subURL : " + subImage.get(j));//subURL 순차적으로 확인
            }
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            subImageURL = new ArrayList<>();//배열초기화 를 시켜줘야 중첩적으로 되지않는다.
            subImage = new ArrayList<>();//배열초기화 를 시켜줘야 중첩적으로 되지않는다.
        }
    }
}
