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
public class Crawling3Service {

    final String url = "https://slowsteadyclub.com/docu/list.html?cate_no=509";

    public List<String> main3(){

        ArrayList<String> arr = new ArrayList<>();
        ArrayList<String> arr2 = new ArrayList<>();

        try {
            Connection conn = Jsoup.connect(url);
            Document document = conn.get();
            Elements sel = document.select("ul.row > li > div.image");
            Elements a2 = sel.select("a");

            for (int i=0; i<a2.size(); i++) {
                Element element1 = a2.get(i);
                String attr = element1.attr("href");
                arr.add("https://slowsteadyclub.com"+attr);
            }
            System.out.println(arr.size());
            for (int j=0; j<arr.size(); j++) {
                String url2 = arr.get(j).toString();
                Connection innerConn = Jsoup.connect(url2);
                Document innerDocument = innerConn.get();
                Elements z = innerDocument.select("ul.prdList > li.relation-item");
                Elements zz = z.select("img");
                for (int k=0; k<zz.size(); k++) {
                    Element element2 = zz.get(k);
                    String attr = element2.attr("src");
                    arr2.add("https:" + attr);
                }
            }





        } catch (IOException e) {
            e.printStackTrace();
        }

        return arr2;

    }

}
