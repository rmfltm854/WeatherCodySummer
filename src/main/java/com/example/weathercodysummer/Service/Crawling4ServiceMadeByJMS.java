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
public class Crawling4ServiceMadeByJMS {//부모이미지에서 자식이미지 끌고 오는 로직 나머지 주소 설정이나 그런 건 나중에 함
    public List<HashMap<String,List<String>>> main5() {

        final String url = "https://slowsteadyclub.com/docu/list.html?cate_no=509";
        ArrayList<String> arr = new ArrayList<>();
        ArrayList<String> arr2 = new ArrayList<>();
        HashMap<String,List<String>> map = new HashMap<>();
        List<HashMap<String,List<String>>> resultList = new ArrayList<HashMap<String,List<String>>>();
        try {
            Connection conn = Jsoup.connect(url);
            Document document = conn.get();
            Elements sel = document.select("ul.row > li > div.image");
            Elements a2 = sel.select("a");
            int h = 0;

            for (int i = 0; i < a2.size(); i++) {
                Element element1 = a2.get(i);
                String attr = element1.attr("href");
                arr.add("https://slowsteadyclub.com" + attr);
                String url2 = arr.get(i).toString();
                Connection innerConn = Jsoup.connect(url2);
                Document innerDocument = innerConn.get();
                Elements z = innerDocument.select("ul.prdList > li.relation-item");
                Elements zz = z.select("img");
                for (int j = 0; j < zz.size(); j++) {
                    Element element2 = zz.get(j);
                    String attr2 = element2.attr("src");
                    h++;
                    System.out.println(h);
                    arr2.add(attr2);
                }
                map.put(arr.get(i),arr2);//이렇게바꾸면 배열안에 [{부모사진,[자식사진1,2,3,4..]}] 이런식으로 들어가서 나중에 db 넣을때편하게 할수있음
                resultList.add(map);
                map = new HashMap<>();//map 저장된 앞선 사진들 초기화
                arr2 = new ArrayList<>();//arr2 배열안에있는 앞선 사진List 초기화
                System.out.println(resultList);
                System.out.println("size" + arr2.size());
            }


        } catch(IOException e){
            e.printStackTrace();
        }

        return resultList;

    }
}
