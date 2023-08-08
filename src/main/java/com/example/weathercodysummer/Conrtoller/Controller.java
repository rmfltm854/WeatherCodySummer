package com.example.weathercodysummer.Conrtoller;

import com.example.weathercodysummer.Dto.*;
import com.example.weathercodysummer.Repository.CrawlingRepository;
import com.example.weathercodysummer.Service.*;
import com.example.weathercodysummer.session.SessionConst;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@org.springframework.stereotype.Controller
@RequiredArgsConstructor
public class Controller {//윤서 등장

    //Crawling4ServiceMadeByJMS jms = new Crawling4ServiceMadeByJMS();

    private final EmailService emailService;


    @Autowired
    private SignUpService signUpService;

    @Autowired
    private CrawlingService crawlingService;

    @GetMapping("/crawling")
    @ResponseBody
    public List<HashMap<String,List<String>>> Test(){
//        List<String> list = service4.main5();
        List<HashMap<String,List<String>>> list = crawlingService.main5();
        return list;
    }


//   @GetMapping("/")
//    public String mainPage() throws Exception {
//        String Confirm = service.sendSimpleMessage("bill7666@naver.com");
//        System.out.println(Confirm);
//        return "main";
//    }

//    @GetMapping("/Login")
//    public String loginPage(){
//        return "login";
//    }

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
    public String getMainPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) SignUp userInfo, Model model){
        if (userInfo != null){ // session에 담긴 memberInfo의 값이 있으면 view에 memberInfo를 넘겨준다.
            model.addAttribute("memberInfo", userInfo);
        }
        model.addAttribute("a", userInfo);
        return "login/main";
    }

    @GetMapping("/login")
    public String getLogPage(@ModelAttribute("login") Login login){
        return "login/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("login") Login login, BindingResult bindingResult, HttpServletRequest request){
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

    @GetMapping("/product/man/etc") // db에 저장된 크롤링 한 이미지 띄우기
    public String manPage(Model model, HttpServletRequest request){

        request.getSession(false);

        List<MainImage> mainImages = crawlingService.mainImageList();
        //List<SubImage> mainImages = crawlingService.mainImageList2();
        model.addAttribute("list", mainImages);


        return "login/manEtc";
    }

    @GetMapping("/product/women")
    public String womenPage(Model model){

        return "login/women";
    }

    @GetMapping("/product/detail")
    public String detailPage(@RequestParam("id") Long id, Model model, HttpServletRequest request,HttpServletResponse response){ //상품 상세 메소드 --> view 가 아직 없어서 userInfo.html 복사 후 사용. 백앤드 로직은 완벽 구현

        MainImage mainSrc = crawlingService.findMainSrc(id);
        List<SubImage> detailImages = crawlingService.detail(id);
        model.addAttribute("mainSrc", mainSrc);
        model.addAttribute("list", detailImages);

        request.getSession(false); // session 받아오기
        //SignUp userInfo = (SignUp) session.getAttribute(SessionConst.LOGIN_MEMBER); // session에 담긴 사용자 정보를 SignUp에 담기
        //model.addAttribute("userInfo", userInfo); // SignUp을 view에 넘기기


        Cookie cookie = new Cookie(id + "recently", mainSrc.getSrc());
        cookie.setPath("/recently/view");
        cookie.setMaxAge(300);
        response.addCookie(cookie);


        return "login/productDetail"; // 현재 productDetail 페이지가 아니라 userInfo.html 복사 붙여넣기 한 페이지임.
    }

    @GetMapping("/recently/view")
    public String recentlyview(HttpServletRequest request, Model model){
        //HttpSession session = request.getSession(false);
        //ArrayList mainSrc = (ArrayList) session.getAttribute("mainSrc");
        //model.addAttribute("mainSrc", mainSrc);
        List<String> a = new ArrayList<>();


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
                if(string.contains("recently")){
                    a.add(value);
                }
                System.out.println(string);
            }
        }
        model.addAttribute("list", a);
        return "login/recentlyView";
    }

    @RequestMapping("/mail")//회원가입 중 email인증 메소드
    @ResponseBody
    public String mailCheck(String email,Model model)throws Exception{
        String confirm = emailService.sendSimpleMessage(email);
        System.out.println(confirm);
        return confirm;
    }

    @GetMapping("aa")
    public String aa(){
        return "help";
    }



    @GetMapping("bb")
    public String aa2(){
        return "productDetail";
    }


}
