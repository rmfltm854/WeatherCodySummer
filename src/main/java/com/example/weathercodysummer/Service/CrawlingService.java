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
public class CrawlingService { //크롤러 로직


    final String url = "https://slowsteadyclub.com/docu/list.html?cate_no=509";

    public List<String> main() {

        Connection conn = Jsoup.connect(url);
        ArrayList<String> arr = new ArrayList<>();

        try {
            Document document = conn.get();
            Elements imageUrlElements = document.getElementsByClass("max");

            for (Element element : imageUrlElements) {
                if (element.attr("alt") == "") {
                    arr.add(element.attr("abs:src"));
                    System.out.println(element.attr("abs:src"));
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
    }

        return arr;
    }
}
