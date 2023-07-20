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
            /**이미지 관련 link 만 나와야하는데 메인 Url 이 나오는이유는 같은 ul.row > li 태그를 가진 태그가 더있기때문에 그런거고
            이미지관련 link 를 포함한 li 태그만 가져오기위해서는 이 li 태그만 가지고있는 고유 class명을 찾아서 지정해주면 된다.**/
            Elements sel = document.select("ul.row > li.docu-item");

            for (int i=0; i < sel.size(); i++) {
                Element element = sel.get(i);
                Elements aTag = element.select("a");
                for(Element element1 : aTag){
                    String string = element1.attr("href").toString();
                    /**"/docu/detail.html?product_no=19677&cate_no=509&display_group=1"만으로는 링크로 바로접속불가하기때문에,
                    "https://slowsteadyclub.com/" 을 붙혀서 저장시켜준다.**/
                    arr.add("https://slowsteadyclub.com" + string);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arr;
    }
}