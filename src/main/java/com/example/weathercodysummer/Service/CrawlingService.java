package com.example.weathercodysummer.Service;

import com.example.weathercodysummer.Entity.*;
import com.example.weathercodysummer.Repository.CrawlingRepository;
import com.example.weathercodysummer.Repository.OutManRepository;
import com.example.weathercodysummer.Repository.WomanURl;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class CrawlingService {//웹페이지 slowsteadyclub 크롤러

    @Autowired
    private CrawlingRepository repo;

    @Autowired
    OutManRepository out;

    @Autowired
    WomanURl womanURL;
    public List<HashMap<String, List<String>>> steadyClubMan() { // steady 남자 크롤링 메서드
        int num = 1;
        int[] page = {1, 3, 4, 6, 7};
        List<HashMap<String, List<String>>> resultList = null;
       List<OutManImage> outURL = out.findAll();
        for (int k = 0; k < page.length; k++) {
            System.out.println("k값: " + k);
            final String url = "https://slowsteadyclub.com/docu/list.html?cate_no=509&page=" + page[k]; // 크롤링 할 url 선언
            System.out.println("현재 url : " + url);
            ArrayList<String> hrefOfMainImageList = new ArrayList<>(); // 메인 이미지의 href 담을 리스트 생성
            ArrayList<String> subImageList = new ArrayList<>(); // 서브 이미지 담을 리스트 생성
            HashMap<String, List<String>> resultMap = new LinkedHashMap<>(); // map{[hrefOfMainImageList(?), subImageList]}
            resultList = new ArrayList<HashMap<String, List<String>>>();

            try {

                Connection conn = Jsoup.connect(url); //Jsoup라이브러리를 활용해서 url 주소로 접속
                Document document = conn.get(); // 접속한 페이지의 html을 Docoument에 담음

                Elements divTags = document.select("ul.row > li > div.image"); // <ul class=row> --> <li> --> <div class=image> 원하는 태그가 담긴 태그 또는 태그의 클래스 명으로 찾아서 Elements에 담음
                Elements aTags = divTags.select("a"); // 찾은 클래스 내에서 a 태그를 찾음
                Elements mainTags = aTags.select("img");//a 태그 안에 <img> 태그가 있기때문에 select 를 한번더 써줘야한다.

                for (int i = 0; i < aTags.size(); i++) { //for문을 활용해 List<Elements>에 담긴 Element를 찾음

                    Element aTag = aTags.get(i); // aTags에 담긴 각각의 aTag를 찾음
                    Element mainTag = mainTags.get(i);//받아온 maintags 에서 i번째 image태그 를 받아온다.
                    String mainImageTag = "https:" + mainTag.attr("src");//src 에 https: 가 빠져서나오는데 이렇게 저장하면 나중에 connection할때 또 붙혀줘야하기때문에 미리붙여준다.
                    String hrefLink = aTag.attr("href"); // aTag안의 href의 link를 찾음
                    hrefOfMainImageList.add("https://slowsteadyclub.com" + hrefLink); // 찾아온 링크를 hrefOfMainImageList에 추가
                    String linkUrl = hrefOfMainImageList.get(i).toString(); // list에서 link를 가져와 String형태로 변환
                    Connection innerConn = Jsoup.connect(linkUrl); // linkUrl로 접속
                    Document innerDocument = innerConn.get(); // linkUrl의 html을 담음
                    Elements liTags = innerDocument.select("ul.prdList > li.relation-item"); // 위와 같은 방법으로 <ul class=prdList> --> <li class=relation-item> 원하는 태그가 들어있는 클래스를 클래스 명으로 찾아서 Elements에 담음
                    Elements imgTags = liTags.select("img"); // 찾은 클래스 내에서 img 태그를 찾음
                    for(int e = 0;e<imgTags.size();e++){
                        for(int j = 0;j<outURL.size();j++){
                            if(imgTags.get(e).equals(outURL.get(j))){
                                imgTags.remove(e);
                            }
                        }
                    }
/**
 URL mainImage = new URL(mainImageTag); //메인 이미지 src로 url 생성
 System.out.println(mainImage);
 BufferedImage image = ImageIO.read(mainImage); //ImageIo를 통해 url을 읽음
 File file = new File("C:\\Users\\OWNER\\Desktop\\crawling\\main" + "\\" + i + "\\.jpg"); // 파일생성


 if(!file.exists()){
 // 디렉토리 생성 메서드
 file.mkdirs();
 }
 ImageIO.write(image, "jpg" , file); // 파일에 이미지 넣기 */

                    for (int j = 0; j < imgTags.size(); j++) { //메인 이미지의 서브 이미지를 가져오기 위해 for문 실행

                        Element imgTag = imgTags.get(j); // imgTags에 담긴 각각의 imgTag를 찾음
                        String subImage = imgTag.attr("src"); // imgTag안의 src의 주소를 찾음
/**
 URL subImageUrl = new URL("https:" + subImage); // sub이미지의 src로 url 생성
 File file1 = new File("C:\\Users\\OWNER\\Desktop\\crawling\\main" + "\\" + i + "\\" + j +".jpg"); // 메인이미지와 서브이미지가 같이 파일에 담김
 BufferedImage read = ImageIO.read(subImageUrl); //ImageIo를 통해 url을 읽음
 ImageIO.write(read, "jpg", file1); // 파일에 이미지 넣기 */

                        subImageList.add("https:" + subImage); // 서브 이미지를 리스트에 담음

                    }


                    resultMap.put(mainImageTag, subImageList);//이렇게바꾸면 배열안에 [{부모사진,[자식사진1,2,3,4..]}] 이런식으로 들어가서 나중에 db 넣을때편하게 할수있음

                    for (Map.Entry<String, List<String>> elem : resultMap.entrySet()) {
                        MainImage mainImage = new MainImage();
                        mainImage.setSrc(elem.getKey());
                        repo.saveMainImage(mainImage);

                        for (String s : elem.getValue()) {
                            SubImage subImage = new SubImage();
                            subImage.setImage(mainImage);
                            subImage.setSrc(s);
                            repo.saveSubImage(subImage);
                        }
                    }


                    resultList.add(resultMap);
                    resultMap = new HashMap<>();//map 저장된 앞선 사진들 초기화
                    subImageList = new ArrayList<>();//arr2 배열안에있는 앞선 사진List 초기화
                    System.out.println(resultList);
                    System.out.println("size" + subImageList.size());

                }


            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        return resultList;

    }

    public List<HashMap<String, List<String>>> steadyClubWoman() { // steady 여자 크롤링 메서드
        int num = 1;
        int[] page = {1, 2,3, 4, 6, 7,9};
        List<HashMap<String, List<String>>> resultList = null;
        List<OutWomanEntity> outURL = womanURL.findAll();
        for (int k = 0; k < page.length; k++) {
            System.out.println("k값: " + k);
            final String url = "https://slowsteadyclub.com/docu/list.html?cate_no=510&page=" + page[k]; // 크롤링 할 url 선언
            System.out.println("현재 url : " + url);
            ArrayList<String> hrefOfMainImageList = new ArrayList<>(); // 메인 이미지의 href 담을 리스트 생성
            ArrayList<String> subImageList = new ArrayList<>(); // 서브 이미지 담을 리스트 생성
            HashMap<String, List<String>> resultMap = new LinkedHashMap<>(); // map{[hrefOfMainImageList(?), subImageList]}
            resultList = new ArrayList<HashMap<String, List<String>>>();

            try {

                Connection conn = Jsoup.connect(url); //Jsoup라이브러리를 활용해서 url 주소로 접속
                Document document = conn.get(); // 접속한 페이지의 html을 Docoument에 담음

                Elements divTags = document.select("ul.row > li > div.image"); // <ul class=row> --> <li> --> <div class=image> 원하는 태그가 담긴 태그 또는 태그의 클래스 명으로 찾아서 Elements에 담음
                Elements aTags = divTags.select("a"); // 찾은 클래스 내에서 a 태그를 찾음
                Elements mainTags = aTags.select("img");//a 태그 안에 <img> 태그가 있기때문에 select 를 한번더 써줘야한다.

                for (int i = 0; i < aTags.size(); i++) { //for문을 활용해 List<Elements>에 담긴 Element를 찾음

                    Element aTag = aTags.get(i); // aTags에 담긴 각각의 aTag를 찾음
                    Element mainTag = mainTags.get(i);//받아온 maintags 에서 i번째 image태그 를 받아온다.
                    String mainImageTag = "https:" + mainTag.attr("src");//src 에 https: 가 빠져서나오는데 이렇게 저장하면 나중에 connection할때 또 붙혀줘야하기때문에 미리붙여준다.
                    String hrefLink = aTag.attr("href"); // aTag안의 href의 link를 찾음
                    hrefOfMainImageList.add("https://slowsteadyclub.com" + hrefLink); // 찾아온 링크를 hrefOfMainImageList에 추가
                    String linkUrl = hrefOfMainImageList.get(i).toString(); // list에서 link를 가져와 String형태로 변환
                    Connection innerConn = Jsoup.connect(linkUrl); // linkUrl로 접속
                    Document innerDocument = innerConn.get(); // linkUrl의 html을 담음
                    Elements liTags = innerDocument.select("ul.prdList > li.relation-item"); // 위와 같은 방법으로 <ul class=prdList> --> <li class=relation-item> 원하는 태그가 들어있는 클래스를 클래스 명으로 찾아서 Elements에 담음
                    Elements imgTags = liTags.select("img"); // 찾은 클래스 내에서 img 태그를 찾음
                    for(int e = 0;e<imgTags.size();e++){
                        for(int j = 0;j<outURL.size();j++){
                            if(imgTags.get(e).equals(outURL.get(j))){
                                imgTags.remove(e);
                            }
                        }
                    }
/**
 URL mainImage = new URL(mainImageTag); //메인 이미지 src로 url 생성
 System.out.println(mainImage);
 BufferedImage image = ImageIO.read(mainImage); //ImageIo를 통해 url을 읽음
 File file = new File("C:\\Users\\OWNER\\Desktop\\crawling\\main" + "\\" + i + "\\.jpg"); // 파일생성


 if(!file.exists()){
 // 디렉토리 생성 메서드
 file.mkdirs();
 }
 ImageIO.write(image, "jpg" , file); // 파일에 이미지 넣기 */

                    for (int j = 0; j < imgTags.size(); j++) { //메인 이미지의 서브 이미지를 가져오기 위해 for문 실행

                        Element imgTag = imgTags.get(j); // imgTags에 담긴 각각의 imgTag를 찾음
                        String subImage = imgTag.attr("src"); // imgTag안의 src의 주소를 찾음
/**
 URL subImageUrl = new URL("https:" + subImage); // sub이미지의 src로 url 생성
 File file1 = new File("C:\\Users\\OWNER\\Desktop\\crawling\\main" + "\\" + i + "\\" + j +".jpg"); // 메인이미지와 서브이미지가 같이 파일에 담김
 BufferedImage read = ImageIO.read(subImageUrl); //ImageIo를 통해 url을 읽음
 ImageIO.write(read, "jpg", file1); // 파일에 이미지 넣기 */

                        subImageList.add("https:" + subImage); // 서브 이미지를 리스트에 담음

                    }


                    resultMap.put(mainImageTag, subImageList);//이렇게바꾸면 배열안에 [{부모사진,[자식사진1,2,3,4..]}] 이런식으로 들어가서 나중에 db 넣을때편하게 할수있음

                    for (Map.Entry<String, List<String>> elem : resultMap.entrySet()) {
                        SteadyWomanMainImg mainImage = new SteadyWomanMainImg();
                        mainImage.setSrc(elem.getKey());
                        repo.saveMainImage2(mainImage);


                        for (String s : elem.getValue()) {
                            SteadyWomanSubImg subImage = new SteadyWomanSubImg();
                            subImage.setImages(mainImage);
                            subImage.setSrc(s);
                            repo.saveSubImage2(subImage);
                        }
                    }


                    resultList.add(resultMap);
                    resultMap = new HashMap<>();//map 저장된 앞선 사진들 초기화
                    subImageList = new ArrayList<>();//arr2 배열안에있는 앞선 사진List 초기화
                    System.out.println(resultList);
                    System.out.println("size" + subImageList.size());

                }


            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        return resultList;

    }

    public List<String> fourMan(){ // 4xr 남자 크롤링 메서드

        final String url = "https://www.4xr.co.kr/style/style_main.php"; // 크롤링 할 url 선언

        ArrayList<String> hrefOfMainImageList = new ArrayList<>(); // 메인 이미지의 href 담을 리스트 생성
        ArrayList<String> subImageList = new ArrayList<>(); // 서브 이미지 담을 리스트 생성
        ArrayList<String> mainImageList = new ArrayList<>(); // 서브 이미지 담을 리스트 생성
        HashMap<String, List<String>> resultMap = new LinkedHashMap<>(); // map{[hrefOfMainImageList(?), subImageList]}
        List<HashMap<String, List<String>>> resultList = new ArrayList<HashMap<String, List<String>>>(); // resultMap을 감쌀 리스트 생성

        try {

            Connection conn = Jsoup.connect(url); //Jsoup라이브러리를 활용해서 url 주소로 접속
            Document document = conn.get(); // 접속한 페이지의 html을 Docoument에 담음

            Elements divTags = document.select("ul.swiper-wrapper > li > div.inner_img_box");
            Elements aTags = divTags.select("a.link");


            for (int i = 0; i < aTags.size(); i++) { //for문을 활용해 List<Elements>에 담긴 Element를 찾음

                Element aTag = aTags.get(i); // aTags에 담긴 각각의 aTag를 찾음
                String hrefLink = aTag.attr("href"); // aTag안의 href의 link를 찾음
                hrefOfMainImageList.add("https://www.4xr.co.kr" + hrefLink); // 찾아온 링크를 hrefOfMainImageList에 추가
                System.out.println(hrefLink);
                String linkUrl = hrefOfMainImageList.get(i).toString(); // list에서 link를 가져와 String형태로 변환
                System.out.println(hrefOfMainImageList.size());

                Connection Conns = Jsoup.connect(linkUrl);
                Document innerDocument = Conns.get(); // linkUrl의 html을 담음
                Elements mainLiTags = innerDocument.select("ul.swiper-wrapper > li"); // mainImg src가 담긴 li태그 가져오는 로직
                Elements subLiTags = innerDocument.select("ul.wrapper > li"); // subImg src가 담긴 li태그 가져오는 로직

                Elements imgs = mainLiTags.select("img"); // 찾은 클래스 내에서 img 태그를 찾음
                Elements subTimgs = subLiTags.select("img");

                for (int j = 0; j < 1; j++) { // li태그에서 mainImg src 가져오는 반복문

                    if (imgs.size() != 0){

                        Element imgTag = imgs.get(j);
                        String imgSrc = imgTag.attr("src");
                        System.out.println(imgSrc);
                        mainImageList.add(imgSrc);

                    }
                }

                for (int h=0; h<subTimgs.size(); h++) { // li태그에서 subImg src 가져오는 반복문 --> li태그에 총 8개 서브 이미지가 담겨있음 티셔츠와 바지만 나오는 이미지 인덱스 1과 5의 값만 추출

                    if (subTimgs.size() != 0){

                        if (h == 1 || h == 5){

                            Element subImgTag = subTimgs.get(h);
                            String subImgSrc = subImgTag.attr("src");
                            System.out.println(subImgSrc);
                            subImageList.add(subImgSrc);

                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }

        return subImageList;
    }


    /**
     * crawling1 -> steadyclub 남자 이미지 크롤링 관련 메서드
     * @return
     */

    public List<com.example.weathercodysummer.Dto.MainImage> mainImageList(){
        List<com.example.weathercodysummer.Dto.MainImage> all = repo.findAll();
        // List<com.example.weathercodysummer.Dto.MainImage> collect = all.stream().map(MainImage::toDto).collect(Collectors.toList());
        return all;

    }

    public List<com.example.weathercodysummer.Dto.MainImage> findMainImage(){
        List<com.example.weathercodysummer.Dto.MainImage> mainImages = mainImageList();
        return mainImages;

    }



    public List<com.example.weathercodysummer.Dto.SubImage> detail(Long id){

        List<SubImage> detailImages = repo.findSubImage(id);
        List<com.example.weathercodysummer.Dto.SubImage> toDto = detailImages.stream().map(SubImage::toDto).collect(Collectors.toList());
        return toDto;
    }

    public com.example.weathercodysummer.Dto.MainImage findMainSrc(Long id){
        MainImage mainImage = repo.findMainSrc(id);
        com.example.weathercodysummer.Dto.MainImage toDto = mainImage.toDto();
        return toDto;
    }

    /**
     * crawling2 -> steadyclub 여자 이미지 크롤링 관련 메서드
     * @return
     */

    public List<com.example.weathercodysummer.Dto.SteadyWomanMainImg> mainImageList2(){
        List<com.example.weathercodysummer.Dto.SteadyWomanMainImg> all = repo.findAll2();
        // List<com.example.weathercodysummer.Dto.MainImage> collect = all.stream().map(MainImage::toDto).collect(Collectors.toList());
        return all;

    }

    public List<com.example.weathercodysummer.Dto.MainImage> findMainImage2(){
        List<com.example.weathercodysummer.Dto.MainImage> mainImages = mainImageList();
        return mainImages;

    }



    public List<com.example.weathercodysummer.Dto.SteadyWomanSubImg> detail2(Long id){

        List<SteadyWomanSubImg> detailImages = repo.findSubImage2(id);
        List<com.example.weathercodysummer.Dto.SteadyWomanSubImg> toDto = detailImages.stream().map(SteadyWomanSubImg::toDto).collect(Collectors.toList());
        return toDto;
    }

    public com.example.weathercodysummer.Dto.SteadyWomanMainImg findMainSrc2(Long id){
        SteadyWomanMainImg mainImage = repo.findMainSrc2(id);
        com.example.weathercodysummer.Dto.SteadyWomanMainImg toDto = mainImage.toDto();
        return toDto;
    }

    public void saveReview(String text, String src, String c,String gender){
        if(gender.equals("man")){
            SignUpEntity b = repo.b(c);
            Review review = new Review();
            review.setSrc(src);
            review.setReviewByUser(b);
            review.setReview(text);
            repo.saveReview(review);
        } else if (gender.equals("women")) {
            SignUpEntity b = repo.b(c);
            Review review = new Review();
            review.setSrc(src);
            review.setReviewByUser(b);
            review.setReview(text);
            repo.saveReview(review);


        }

    }




}