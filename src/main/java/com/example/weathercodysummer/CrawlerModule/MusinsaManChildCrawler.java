package com.example.weathercodysummer.CrawlerModule;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MusinsaManChildCrawler {

    public List<String> getProductDetail(String url) throws IOException {
        HashMap<String, String> map = new HashMap<String, String>();
        List<String> subList = new ArrayList<>();
        String childURL = url;
        Document Musinsa = Jsoup.connect(childURL).get();
        Elements productDetail = Musinsa.select(".styling_list > .swiper-slide > .box-img > .styling_img > img");
        for (int j = 0; j < productDetail.size(); j++) {
            subList.add("https:" + productDetail.get(j).attr("src"));
        }
        return subList;
    }
}
