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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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

    @Autowired
    SignUpService signUpService;

    @GetMapping("/crawling")
    @ResponseBody
    public List<HashMap<String,List<String>>> Test(){
//        List<String> list = service4.main5();
        List<HashMap<String,List<String>>> list = jms.main5();
        return list;
    }


//    @GetMapping("/Main")
    public String mainPage(){
        return "product3madeByJms";
    }

    @GetMapping("/Login")
    public String loginPage(){
        return "login";
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

    @GetMapping("/Product")
    public String productPage(){
        return "product";
    }

    @GetMapping("signUp")
    public String login(){
        return "login/signUp";
    }

    @PostMapping("signUp")
    public String signUp(SignUp signUp){
        signUpService.save(signUp);
        return "redirect:/practice/main";
    }

    @GetMapping("/main")
    public String main(){
        return "login/main";
    }

    @GetMapping("login")
    public String logPage(){
        return "login/login";
    }

    @PostMapping("login")
    public String logP(@Valid @ModelAttribute("login") Login login, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors()) {
            return "login";
        }
        SignUp a = signUpService.findByLoginId(login.getUserId(), login.getUserPassword());
        log.info("login? {}", a);
        if (a == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/login";
        }
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, a);

        return "redirect:/practice/userInfo";
    }

    @GetMapping("/userInfo")
    public String userInfo(HttpServletRequest request , Model model){
        List<SignUp> info = signUpService.findInfo();
        model.addAttribute("list", info);

        HttpSession session = request.getSession(false);
        SignUp loginMember = (SignUp)
                session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("log", loginMember);
        log.info("login? {}", loginMember);
/**
 Login session = (Login) sessionManager.getSession(request);
 model.addAttribute("session", session);*/
        return "/login/userInfo";
    }


}
