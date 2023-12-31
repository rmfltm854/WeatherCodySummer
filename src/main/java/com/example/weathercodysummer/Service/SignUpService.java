package com.example.weathercodysummer.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.weathercodysummer.Dto.SignUp;
import com.example.weathercodysummer.Entity.SignUpEntity;

import com.example.weathercodysummer.Repository.SignUpRepo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SignUpService {

    @Autowired
    private SignUpRepo repo;

    public void save(SignUp signUp){
        repo.save(signUp);
    } // 저장 메서드


    public SignUp findByLoginId(String userId, String userPassword){

        SignUpEntity userInfo = repo.findByLoginId(userId).filter(s -> s.getUserPassword().equals(userPassword)).orElse(null); // DB에 저장된 userPassword와 받아온 userPassword 일치하는지

        if(userInfo==null){ // 반환된 값이 null이라면 null로 반환
            return null;
        }

        SignUp entityToDto = userInfo.toDto(); // 반환된 값이 있다면 SignUpEntity --> SignUp  Entity to DTO


        return entityToDto;
    }

    public List<SignUp> findUserInfo() {

        List<SignUpEntity> userInfo = repo.findInfo(); // Entity to Dto
        List<SignUp> userInfoList = userInfo.stream().map(SignUpEntity::toDto) // Entity to Dto
                .collect(Collectors.toList());

        return userInfoList;
    }

    public boolean getAllList(SignUp signUp){
        Optional<SignUpEntity> a = repo.findByLoginId(signUp.getUserId());
        System.out.println(a);
        return a.isEmpty();
    }

    @Transactional
    public void update(SignUp signUp){ //받아온 값으로 정보 수정
        SignUpEntity updateInfo = repo.update(signUp.getUserId());
        updateInfo.setUserName(signUp.getUserName());
        updateInfo.setUserPassword(signUp.getUserPassword());
        updateInfo.setUserEmail(signUp.getUserEmail());

    }

}
