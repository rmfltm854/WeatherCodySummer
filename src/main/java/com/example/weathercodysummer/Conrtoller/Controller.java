package com.example.weathercodysummer.Conrtoller;

import com.example.weathercodysummer.CrawlerModule.MusinsaManCrawler;
import com.example.weathercodysummer.Dto.*;
import com.example.weathercodysummer.Dto.MainImage;
import com.example.weathercodysummer.Dto.SteadyWomanMainImg;
import com.example.weathercodysummer.Dto.SteadyWomanSubImg;
import com.example.weathercodysummer.Dto.SubImage;
import com.example.weathercodysummer.Entity.*;
import com.example.weathercodysummer.Repository.*;
import com.example.weathercodysummer.Service.*;
import com.example.weathercodysummer.session.SessionConst;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.*;

@Slf4j
@org.springframework.stereotype.Controller
@RequiredArgsConstructor
public class Controller {//윤서 등장

    //Crawling4ServiceMadeByJMS jms = new Crawling4ServiceMadeByJMS();

    private final EmailService emailService;

    private static String secretKey = "c1h4RXhOQWZzdnhmc0VqcUhaaGZrdml3UFBSSEpxUVc="; //시크릿key
    private static String apiUrl = "https://th2kqf441b.apigw.ntruss.com/custom/v1/9578/49c70246790368c425cc314d5f0f34ee727da4e65be819176717606f84934719"; //apiURL

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private CrawlingService crawlingService;


    @Autowired
    MainImageService mainService;

    @Autowired
    SteadyWomenMainImage WmainService;

    @Autowired
    reviewService rService;

    @Autowired
    MainImageRepo repo;

    @Autowired
    OutManService outService;

    @Autowired
    SteadyWomanRepository womanRepo;

    @Autowired
    MunsinsaManService service;

    @Autowired
    MusinsaWomenService wService;

    @Autowired
    MusinsaManRepository musinMan;

    @Autowired
    MusinsaManSubRepository musinsub;

    @Autowired
    MusinsaWomenRepository musinWomen;

    @Autowired
    MusinsaWomenSubRepository musinWomenSub;

    @Autowired
    SteadyManRepository steadyman;

    @Autowired
    SteadyWomanRepository steadywomen;
    @GetMapping("/crawlingBy4xr")
    @ResponseBody
    public List<String> fourMan(){ // 4xr 남자 크롤링 메서드
        List<String> list = crawlingService.fourMan();
        return list;
    }

    @GetMapping("/Musinsa")
    @ResponseBody
    public void MusinsaMan() throws IOException { // 4xr 남자 크롤링 메서드
        wService.getSubManImage();
    }

    @GetMapping("/crawling2")
    @ResponseBody
    public List<HashMap<String,List<String>>> crawlingBySteadyMan(){ // steady 남자 크롤링 메서드
        List<HashMap<String,List<String>>> list = crawlingService.steadyClubWoman();
        return list;
    }


    @GetMapping("/crawling")
    @ResponseBody
    public List<HashMap<String,List<String>>> crawlingBySteadyWoman() { // steady 여자 크롤링 메서드
//        List<String> list = service4.main5();
        List<HashMap<String, List<String>>> list = crawlingService.steadyClubMan();
        return list;
    }


    //hello
    @GetMapping("/SignUp")
    public String signUp(){
        return "signUp";
    }

    @GetMapping("/Main")
    public String mainPage2(){
        return "main";
    }

    @GetMapping("/UserInfo")
    public String userInfo(){
        return "myPage";
    }


    @GetMapping("/signUp")
    public String getSignUpPage(@ModelAttribute("signUp") SignUp signUp){
        return "login/signUp";
    }

    @PostMapping("/signUp")
    public String signUp(@Valid @ModelAttribute("signUp") SignUp signUp, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/login/signUp";
        }
        boolean a = signUpService.getAllList(signUp);
        if(a == false){
            bindingResult.reject("loginFail", "중복된 아이디 입니다.");
            return "/login/signUp";
        }
        signUpService.save(signUp);
        return "redirect:/login";
    }

    @GetMapping("/main")
    public String getMainPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) SignUp userInfo,
                              Model model,HttpServletResponse response){ // mainPage 매핑 -> Get

        if (userInfo != null){ // session에 담긴 memberInfo의 값이 있으면 view에 memberInfo를 넘겨준다.
            model.addAttribute("memberInfo", userInfo);
            Cookie cookie = new Cookie("userInfo","loginSuccess");
            response.addCookie(cookie);
        }

        List<MainImage> mainRanking = mainService.mainImageRank();
        List<SteadyWomanMainImg> WomenRanking = WmainService.mainImageRank();
        model.addAttribute("manRank",mainRanking);
        model.addAttribute("WomenRank",WomenRanking);

        return "login/main";
    }

    @GetMapping("/login")
    public String getLogPage(@ModelAttribute("login") Login login){ // 로그인 페이지 매핑
        return "login/login";
    }


    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("login") Login login, BindingResult bindingResult, HttpServletRequest request){ // 로그인 페이지 매핑-> post

        System.out.println(login);
        if(bindingResult.hasErrors()) { //에러 발생시 BindingResult 활용해서 글로벌 에러 띄우기
            return "login";
        }
        SignUp userInfo = signUpService.findByLoginId(login.getUserId(), login.getUserPassword()); //Login 객체를 활용하여 아이디와 비밀번호가 일치하면 회원정보 전체가 담긴 SignUp 객체에 담는다
        log.info("login? {}", userInfo);
        if (userInfo == null) { //일치하지 않을경우 에러메시지를 띄운다
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/login";
        }
        HttpSession session = request.getSession(); //session을 생성하여 getSession을 사용한다. -> session이 없으면 생성해주고 있으면 세션을 가져옴
        System.out.println("in"+session.toString());
        session.setAttribute(SessionConst.LOGIN_MEMBER, userInfo); // 생성된 session에 회원정보 전체를 담는다.

        return "redirect:/main";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model, HttpServletResponse response){

        HttpSession session = request.getSession(false); //session 가져옴. false --> session이 없어도 생성하지 않음
        if (session != null){ // session이 null이 아니라면 session의 모든 데이터 삭제
            session.invalidate();
        }
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for(int i=0; i< cookies.length; i++){

                cookies[i].setMaxAge(0); // 유효시간을 0으로 설정

                response.addCookie(cookies[i]); // 응답 헤더에 추가

            }
        }
        System.out.println("out"+session.toString());


        return "redirect:/main";
    }

    @GetMapping("/userInfo")
    public String getUserInfoPage(HttpServletRequest request , Model model){
        List<SignUp> info = signUpService.findUserInfo(); //이건 회원정보 리스트로 받아오는 로직 사용하지는 않았음
        model.addAttribute("list", info);

        HttpSession session = request.getSession(false);

        if(session!=null) {

            SignUp userInfo = (SignUp)
                    session.getAttribute(SessionConst.LOGIN_MEMBER);
            model.addAttribute("userInfo", userInfo);
            log.info("login? {}", userInfo);
        }
/**
 Login session = (Login) sessionManager.getSession(request);
 model.addAttribute("session", session);*/
        return "/login/userInfo";
    }

    @GetMapping("/update")
    public String getUpdatePage(@ModelAttribute("signUp") SignUp signUp, HttpServletRequest request , Model model) {
        HttpSession session = request.getSession(false); // session 받아오기
        SignUp userInfo = (SignUp) session.getAttribute(SessionConst.LOGIN_MEMBER); // session에 담긴 사용자 정보를 SignUp에 담기
        model.addAttribute("userInfo", userInfo); // SignUp을 view에 넘기기
        return "login/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("signUp") SignUp signUp){
        signUpService.update(signUp); // 회원정보 수정 메소드
        return "redirect:/login";
    }
    @GetMapping("/product/man")
    public String manMusinsaPage(Model model, HttpServletRequest request){
        HttpSession session = request.getSession(false); // session 받아오기
        if (session != null) {
            SignUp userInfo = (SignUp) session.getAttribute(SessionConst.LOGIN_MEMBER);
            model.addAttribute("memberInfo", userInfo);
        }
        return "/login/man";
    }

    @GetMapping("/product/man/etc") // db에 저장된 크롤링 한 이미지 띄우기
    public String manSteadyPage(Model model, HttpServletRequest request){

        HttpSession session = request.getSession(false); // session 받아오기
        if (session != null) {
            SignUp userInfo = (SignUp) session.getAttribute(SessionConst.LOGIN_MEMBER);
            model.addAttribute("memberInfo", userInfo);
        }

        List<MainImage> mainImages = crawlingService.mainImageList();
        //List<SubImage> mainImages = crawlingService.mainImageList2();
        model.addAttribute("list", mainImages);


        return "login/manEtc";
    }

    @GetMapping("/product/women")
    public String womanMusinsaPage(Model model,HttpServletRequest request){
        HttpSession session = request.getSession(false); // session 받아오기
        if (session != null) {
            SignUp userInfo = (SignUp) session.getAttribute(SessionConst.LOGIN_MEMBER);
            model.addAttribute("memberInfo", userInfo);
        }
        return "login/women";
    }

    @GetMapping("/product/women/etc") // db에 저장된 크롤링 한 이미지 띄우기
    public String womenSteadyPage(Model model, HttpServletRequest request){

        HttpSession session = request.getSession(false); // session 받아오기
        if (session != null) {
            SignUp userInfo = (SignUp) session.getAttribute(SessionConst.LOGIN_MEMBER);
            model.addAttribute("memberInfo", userInfo);
        }

        List<SteadyWomanMainImg> mainImages = crawlingService.mainImageList2();
        //List<SubImage> mainImages = crawlingService.mainImageList2();
        model.addAttribute("list", mainImages);


        return "login/womenEtc";
    }

    @GetMapping("/product/detail")
    public String detailPage(@RequestParam("id") Long id, Model model, HttpServletRequest request,HttpServletResponse response,String gender,String stat){ //상품 상세 메소드 --> view 가 아직 없어서 userInfo.html 복사 후 사용. 백앤드 로직은 완벽 구현

        HttpSession session = request.getSession(false); // session 받아오기
        if (session != null) {
            SignUp userInfo = (SignUp) session.getAttribute(SessionConst.LOGIN_MEMBER);
            model.addAttribute("memberInfo", userInfo);

        }
        if(stat.equals("musinsa")){
            System.out.println("무신사");
            if(gender.equals("man")){
                String mainImage = musinMan.mainSrc(id);
                List<MusinasManSubEntity> detailImages =musinsub.subSrc(id);
                List<ReviewDto> rDto = rService.getReview(mainImage);
                model.addAttribute("review",rDto);
                model.addAttribute("mainSrc", mainImage);
                model.addAttribute("list", detailImages);
                model.addAttribute("gender",gender);
                model.addAttribute("stat",stat);
                System.out.println(gender);
                Cookie cookie = new Cookie(id.toString(), mainImage);
                cookie.setPath("/recently/view");
                cookie.setMaxAge(300);
                response.addCookie(cookie);
            } else{
                String mainImage = musinWomen.mainSrc(id);
                List<MusinsaWomenSubEntity> detailImages = musinWomenSub.subSrc(id);
                List<ReviewDto> rDto = rService.getReview(mainImage);
                model.addAttribute("review",rDto);
                model.addAttribute("mainSrc", mainImage );
                model.addAttribute("list", detailImages);
                model.addAttribute("gender",gender);
                model.addAttribute("stat",stat);
                System.out.println(gender);
                Cookie cookie = new Cookie(id.toString(), mainImage);
                cookie.setPath("/recently/view");
                cookie.setMaxAge(300);
                response.addCookie(cookie);
            }
        }else{
            System.out.println("steady");
            if(gender.equals("man")){
                String mainSrc = steadyman.mainSrc(id);
                List<SubImage> detailImages = crawlingService.detail(id);
                List<ReviewDto> rDto = rService.getReview(mainSrc);
                model.addAttribute("review",rDto);
                model.addAttribute("mainSrc", mainSrc);
                model.addAttribute("list", detailImages);
                model.addAttribute("gender",gender);
                model.addAttribute("stat",stat);
                System.out.println(gender);
                Cookie cookie = new Cookie(id.toString(), mainSrc);
                cookie.setPath("/recently/view");
                cookie.setMaxAge(300);
                response.addCookie(cookie);
            } else{
                String mainSrc = steadywomen.mainSrc(id);
                List<SteadyWomanSubImg> detailImages = WmainService.detail(id);
                List<ReviewDto> rDto = rService.getReview(mainSrc);
                model.addAttribute("review",rDto);
                model.addAttribute("mainSrc", mainSrc);
                model.addAttribute("list", detailImages);
                model.addAttribute("gender",gender);
                model.addAttribute("stat",stat);
                System.out.println(gender);
                Cookie cookie = new Cookie(id.toString(), mainSrc);
                cookie.setPath("/recently/view");
                cookie.setMaxAge(300);
                response.addCookie(cookie);
            }

        }


        return "login/productDetail"; // 현재 productDetail 페이지가 아니라 userInfo.html 복사 붙여넣기 한 페이지임.
    }

    @GetMapping("/recently/view")
    public String recentlyView(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        if (session != null){
            SignUp userInfo = (SignUp) session.getAttribute(SessionConst.LOGIN_MEMBER);
            model.addAttribute("userInfo", userInfo);
        }
        //ArrayList mainSrc = (ArrayList) session.getAttribute("mainSrc");
        //model.addAttribute("mainSrc", mainSrc);
        HashMap<Long, String> map = new LinkedHashMap<>();


        Cookie[] cookies = request.getCookies();
        /**
        if (cookies != null){
            for (int i=0; i<cookies.length; i++){
                String value = cookies[i].getValue().toString();
                a.add(value);
                }
*/
        if (cookies != null){
            for (int i=0; i<cookies.length; i++){
                String value = cookies[i].getValue().toString();
                String string = cookies[i].getName();
                if(value.contains("slowsteadyclub")){
                    Long l = Long.parseLong(string);
                    map.put(l, value);
                }
            }
        }

        model.addAttribute("list", map);


        return "login/recentlyView";
    }

    @RequestMapping("/mail")//회원가입 중 email인증 메소드
    @ResponseBody
    public String mailCheck(String email,Model model)throws Exception{
        String confirm = emailService.sendSimpleMessage(email);
        return confirm;
    }

    @GetMapping("/help")
    public String aa(Model model,HttpServletRequest request){
        HttpSession session = request.getSession(false); // session 받아오기
        if (session != null) {
            SignUp userInfo = (SignUp) session.getAttribute(SessionConst.LOGIN_MEMBER);
            model.addAttribute("memberInfo", userInfo);
        }
        return "/login/help";
    }



    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public List<String> sendMessage(@Payload String chatMessage) throws IOException {
        List<String> result = new ArrayList<>();
        URL url = new URL(apiUrl);
        String message = chatBotService.getReqMessage(chatMessage);
        String encodeBase64String = chatBotService.makeSignature(message, secretKey);
        String URLResult;//받아오는 값이 url이 포함되어있을때
        //api서버 접속 (서버 -> 서버 통신)
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json;UTF-8");
        con.setRequestProperty("X-NCP-CHATBOT_SIGNATURE", encodeBase64String);
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.write(message.getBytes("UTF-8"));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        if (responseCode == 200) { // 정상 호출

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            con.getInputStream(), "UTF-8"));
            String decodedString;
            String jsonString = "";
            while ((decodedString = in.readLine()) != null) {
                jsonString = decodedString;
            }

            //받아온 값을 세팅하는 부분
            JSONParser jsonparser = new JSONParser();
            try {
                JSONObject json = (JSONObject) jsonparser.parse(jsonString);
                JSONArray bubblesArray = (JSONArray) json.get("bubbles");
                JSONObject bubbles = (JSONObject) bubblesArray.get(0);

                JSONObject data = (JSONObject) bubbles.get("data");
                String description = "";
                description = (String) data.get("description");
                chatMessage = description;
                result.add(0,chatMessage);
                URLResult = (String)data.get("url");
                result.add(1,URLResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
            in.close();
        } else {  // 에러 발생
            chatMessage = con.getResponseMessage();
        }
//        return chatMessage;
        return result;
    }

    @GetMapping("/ranking")
    @ResponseBody
    public List<com.example.weathercodysummer.Dto.MainImage> ranking(){

        List<MainImage> mainRanking = mainService.mainImageRank();
        return mainRanking;
    }

    @GetMapping("/like")
    @ResponseBody
    public int likeCount(String imgSrc,String action, String gender,String stat){
        System.out.println("stat: " + stat);
        int resultMain = 0;
        if(stat.equals("musinsa")){
            if(gender.equals("man")){
                resultMain = service.countLike(imgSrc,action);
            }else{
                resultMain = service.countLike(imgSrc,action);
            }
            return resultMain;
        }else{
            if(gender.equals("man")){
                resultMain = mainService.countLike(imgSrc,action);
            }else{
                resultMain = WmainService.countLike(imgSrc,action);
            }
            return resultMain;
        }
    }
    @GetMapping("/submit-review")
    @ResponseBody
    public String review(String reviewText, String imgSrc,HttpServletRequest request,String gender,Model model, String stat){

        System.out.println(reviewText);
        System.out.println(gender);
        HttpSession session = request.getSession(false);
            if(gender.equals("man")){
                if (session != null) {
                    SignUp userInfo = (SignUp) session.getAttribute(SessionConst.LOGIN_MEMBER);
                    String string = userInfo.getUserId().toString();
                    System.out.println(imgSrc);
                    crawlingService.saveReview(reviewText, imgSrc, string,gender);
                    List<ReviewDto> rDto = rService.getReview(imgSrc);//img 주소를 받아서 그사진에대한 리뷰를 상세페이지로 넘어갈때 보내준다.
                    model.addAttribute("review",rDto);
                    Cookie[] cookie = request.getCookies();
                }
            } else{
                if (session != null) {
                    SignUp userInfo = (SignUp) session.getAttribute(SessionConst.LOGIN_MEMBER);
                    String string = userInfo.getUserId().toString();
                    crawlingService.saveReview(reviewText, imgSrc, string,gender);

                    List<ReviewDto> rDto = rService.getReview(imgSrc);//img 주소를 받아서 그사진에대한 리뷰를 상세페이지로 넘어갈때 보내준다.
                    model.addAttribute("review",rDto);
                    Cookie[] cookie = request.getCookies();
                }
            }

        return "성공";
    }

    @DeleteMapping ("/delete-review")
    @ResponseBody
    public String deleteReview(String reviewText, String imgSrc, HttpServletRequest request, String gender){
        HttpSession session = request.getSession(false);
        if(gender.equals("man")){
            if (session != null) {
                SignUp userInfo = (SignUp) session.getAttribute(SessionConst.LOGIN_MEMBER);
                String string = userInfo.getUserId().toString();
                crawlingService.saveReview(reviewText, imgSrc, string,gender);
                System.out.println(imgSrc);
                Cookie[] cookie = request.getCookies();
            }
        } else if (gender.equals("women")) {
            if (session != null) {
                SignUp userInfo = (SignUp) session.getAttribute(SessionConst.LOGIN_MEMBER);
                String string = userInfo.getUserId().toString();
                crawlingService.saveReview(reviewText, imgSrc, string,gender);
                System.out.println(imgSrc);
                Cookie[] cookie = request.getCookies();

            }

        }

        return "성공";
    }

   @GetMapping("/productList")
    public String product(@PageableDefault(page=0,size=16,direction = Sort.Direction.DESC)Pageable pageable, Model model,String gender,HttpServletRequest request){
       HttpSession session = request.getSession(false); // session 받아오기
       if (session != null) {
           SignUp userInfo = (SignUp) session.getAttribute(SessionConst.LOGIN_MEMBER);
           model.addAttribute("memberInfo", userInfo);
       }
        System.out.println("gender: " + gender);
        if(gender.equals("man")){
           List<MusinsaManEntity> page = musinMan.findAll(pageable).getContent();
           model.addAttribute("manProduct",page);
           long total = musinMan.count();
           int button = (int)total/16 +1;
           model.addAttribute("button",button);
           return "login/man";
       }else{
           List<MusinsaWomenEntity> page = musinWomen.findAll(pageable).getContent();
           model.addAttribute("WomenProduct",page);
           long total = musinWomen.count();
           int button = (int)total/16 +1;
           model.addAttribute("button",button);
           model.addAttribute("stat","musinsa");
           return "login/women";
       }
   }

    @GetMapping("/productList/etc")
    public String productEtc(@PageableDefault(page=0,size=16,direction = Sort.Direction.DESC)Pageable pageable, Model model,String gender){
        if(gender.equals("man")){
            List<com.example.weathercodysummer.Entity.MainImage> page = repo.findAll(pageable).getContent();
            model.addAttribute("manProduct",page);
            long total = repo.count();
            int button = (int)total/16 +1;
            model.addAttribute("button",button);
            return "login/manEtc";
        }else{
            List<com.example.weathercodysummer.Entity.SteadyWomanMainImg> page = womanRepo.findAll(pageable).getContent();
            model.addAttribute("WomenProduct",page);
            long total = womanRepo.count();
            int button = (int)total/16 +1;
            model.addAttribute("button",button);
            return "login/womenEtc";
        }
    }

   @GetMapping("/testURL")
    public List<OutManImage> testURL(){
        List<OutManImage> list = outService.allOutSRC();

        return list;
    }
}
