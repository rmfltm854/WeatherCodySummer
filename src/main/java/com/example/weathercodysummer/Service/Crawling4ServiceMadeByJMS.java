package com.example.weathercodysummer.Service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class Crawling4ServiceMadeByJMS {//웹페이지 slowsteadyclub 크롤러
    public List<HashMap<String,List<String>>> main5() {

        final String url = "https://slowsteadyclub.com/docu/list.html?cate_no=509"; // 크롤링 할 url 선언

        ArrayList<String> hrefOfMainImageList = new ArrayList<>(); // 메인 이미지의 href 담을 리스트 생성
        ArrayList<String> mainImageList = new ArrayList<>(); // 메인 이미지 담을 리스트 생성
        ArrayList<String> subImageList = new ArrayList<>(); // 서브 이미지 담을 리스트 생성
        HashMap<String,List<String>> resultMap = new HashMap<>(); // map{[hrefOfMainImageList(?), subImageList]}
        List<HashMap<String,List<String>>> resultList = new ArrayList<HashMap<String,List<String>>>(); // resultMap을 감쌀 리스트 생성

        try {

            Connection conn = Jsoup.connect(url); //Jsoup라이브러리를 활용해서 url 주소로 접속
            Document document = conn.get(); // 접속한 페이지의 html을 Docoument에 담음

            Elements divTags = document.select("ul.row > li > div.image"); // <ul class=row> --> <li> --> <div class=image> 원하는 태그가 담긴 태그 또는 태그의 클래스 명으로 찾아서 Elements에 담음
            Elements aTags = divTags.select("a"); // 찾은 클래스 내에서 a 태그를 찾음
            int h = 0;

            for (int i = 0; i < aTags.size(); i++) { //for문을 활용해 List<Elements>에 담긴 Element를 찾음

                Element aTag = aTags.get(i); // aTags에 담긴 각각의 aTag를 찾음
                String hrefLink = aTag.attr("href"); // aTag안의 href의 link를 찾음
                //String mainImage = aTag.attr("src"); // aTag안의 scr의 주소를 찾음
                hrefOfMainImageList.add("https://slowsteadyclub.com" + hrefLink); // 찾아온 링크를 hrefOfMainImageList에 추가
                // mainImageList.add(mainImage); // List에 MainImage를 담음
                String linkUrl = hrefOfMainImageList.get(i).toString(); // list에서 link를 가져와 String형태로 변환

                Connection innerConn = Jsoup.connect(linkUrl); // linkUrl로 접속
                Document innerDocument = innerConn.get(); // linkUrl의 html을 담음
                Elements liTags = innerDocument.select("ul.prdList > li.relation-item"); // 위와 같은 방법으로 <ul class=prdList> --> <li class=relation-item> 원하는 태그가 들어있는 클래스를 클래스 명으로 찾아서 Elements에 담음
                Elements imgTags = liTags.select("img"); // 찾은 클래스 내에서 img 태그를 찾음

                for (int j = 0; j < imgTags.size(); j++) { //메인 이미지의 서브 이미지를 가져오기 위해 for문 실행
                    Element imgTag = imgTags.get(j); // imgTags에 담긴 각각의 imgTag를 찾음
                    String subImage = imgTag.attr("src"); // imgTag안의 src의 주소를 찾음
                    h++;
                    System.out.println(h);
                    subImageList.add(subImage); // 서브 이미지를 리스트에 담음
                }

                resultMap.put(hrefOfMainImageList.get(i),subImageList);//이렇게바꾸면 배열안에 [{부모사진,[자식사진1,2,3,4..]}] 이런식으로 들어가서 나중에 db 넣을때편하게 할수있음
                resultList.add(resultMap);
                resultMap = new HashMap<>();//map 저장된 앞선 사진들 초기화
                subImageList = new ArrayList<>();//arr2 배열안에있는 앞선 사진List 초기화
                System.out.println(resultList);
                System.out.println("size" + subImageList.size());
            }


        } catch(IOException e){
            e.printStackTrace();

        }

        return resultList;

    }
}
