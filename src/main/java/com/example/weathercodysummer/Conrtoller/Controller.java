package com.example.weathercodysummer.Conrtoller;

import com.example.weathercodysummer.Dto.Login;
import com.example.weathercodysummer.Dto.SignUp;
import com.example.weathercodysummer.Service.*;
import com.example.weathercodysummer.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Slf4j
@org.springframework.stereotype.Controller
@RequiredArgsConstructor
public class Controller {//윤서 등장

    Crawling2Service service2 = new Crawling2Service();
    Crawling3Service service3 = new Crawling3Service();

    Crawling4Service service4 = new Crawling4Service();
    Crawling4ServiceMadeByJMS jms = new Crawling4ServiceMadeByJMS();

    private final EmailService service;
    @Autowired
    SignUpService signUpService;

    @GetMapping("/crawling")
    @ResponseBody
    public List<HashMap<String,List<String>>> Test(){
//        List<String> list = service4.main5();
        List<HashMap<String,List<String>>> list = jms.main5();
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

    @GetMapping("/Product")
    public String productPage(){
        return "product";
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
    public String logout(HttpServletRequest request, Model model){

        HttpSession session = request.getSession(false); //session 가져옴. false --> session이 없어도 생성하지 않음
        if (session != null){ // session이 null이 아니라면 session의 모든 데이터 삭제
            session.invalidate();
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


}
