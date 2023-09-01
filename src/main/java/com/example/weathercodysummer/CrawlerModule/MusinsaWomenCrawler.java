package com.example.weathercodysummer.CrawlerModule;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class MusinsaWomenCrawler {
    private static final Pattern PATTERN_BRACKET = Pattern.compile("\\([^\\(\\)]+\\)");
    private static final String VOID = "";
    public List<HashMap<String,String>> setData() {
        int pageNo = 1;
        List<String> list = new ArrayList<>();

        String HowPage = "https://www.musinsa.com/search/musinsa/coordi?q=%EC%BD%94%EB%94%94&list_kind=small&sortCode=term_date&sub_sort=&page=1&display_cnt=0&saleGoods=false&includeSoldOut=false&setupGoods=false&popular=false&category1DepthCode=&category2DepthCodes=&category3DepthCodes=&selectedFilters=&category1DepthName=&category2DepthName=&brandIds=&price=&colorCodes=&contentType=&styleTypes=&includeKeywords=&excludeKeywords=&originalYn=N&tags=&campaignId=&serviceType=&eventType=&type=&season=&measure=&openFilterLayout=N&selectedOrderMeasure=&shoeSizeOption=&groupSale=false&d_cat_cd=&attribute=&plusDeliveryYn=";
        try {
            Document page = Jsoup.connect(HowPage).get();
            Elements totalNum = page.select(".totalPagingNum");
            pageNo = Integer.parseInt(totalNum.get(0).text());
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String,String> map = new HashMap<String,String>();
        List<HashMap<String,String>> relist = new ArrayList<HashMap<String,String>>();
        for (int i = 1; i <4; i++) {
            String wtUrl = "https://www.musinsa.com/search/musinsa/coordi?q=%EC%97%AC%EC%9E%90&list_kind=small&sortCode=term_date&sub_sort=&page="+i+"&display_cnt=0&saleGoods=false&includeSoldOut=false&setupGoods=false&popular=false&category1DepthCode=&category2DepthCodes=&category3DepthCodes=&selectedFilters=&category1DepthName=&category2DepthName=&brandIds=&price=&colorCodes=&contentType=&styleTypes=&includeKeywords=&excludeKeywords=&originalYn=N&tags=&campaignId=&serviceType=&eventType=&type=&season=&measure=&openFilterLayout=N&selectedOrderMeasure=&shoeSizeOption=&groupSale=false&d_cat_cd=&attribute=&plusDeliveryYn=";
            try {
                // System.out.println(wtUrl);
                Document Musinsa = Jsoup.connect(wtUrl).get();

                Elements imgUrl = Musinsa.select(".style-list-thumbnail > img");
                Elements productName = Musinsa.select(".style-list-information__link > strong");
                Elements productDetalURL = Musinsa.select(".style-list-item__thumbnail > a");
                // System.out.println(i);
                for (int j = 0; j < imgUrl.size(); j++) {
                    //System.out.println(imgUrl.get(j).attr("data-original"));
                    String src = imgUrl.get(j).attr("data-original");

                    URL url = new URL(src);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setRequestProperty("Referer", src);
                    map.put("name",productName.get(j).text());
                    map.put("url",src);
                    map.put("productDetailURL",productDetalURL.get(j).attr("href"));
                    System.out.println("name : " + productName.get(j).text());
                    System.out.println("url : " + src);
                    System.out.println("productDetail : " +productDetalURL.get(j).attr("href") );
                    relist.add(map);
                    map = new HashMap<String,String>();
                }

                pageNo++;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return relist;
    }
}
