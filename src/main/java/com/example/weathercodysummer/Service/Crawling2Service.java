package com.example.weathercodysummer.Service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class Crawling2Service { //href만 긁어오는 코드
    final String url = "https://slowsteadyclub.com/docu/list.html?cate_no=509";

    private String url2 = "";

    public List<String> main() {

        Connection conn = Jsoup.connect(url);
        ArrayList<String> arr = new ArrayList<>();

        try {
            Document document = conn.get();
            Elements sel = document.select("ul.row > li");


            for (int i=0; i < sel.size(); i++) {
                Element element = sel.get(i);
                Elements aTag = element.select("a");
                for(Element element1 : aTag){
                    String string = element1.attr("href").toString();
                    arr.add(string);
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return arr;
    }
}