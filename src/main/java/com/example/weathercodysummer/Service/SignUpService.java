package com.example.weathercodysummer.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.weathercodysummer.Dto.Login;

import com.example.weathercodysummer.Dto.SignUp;
import com.example.weathercodysummer.Entity.SignUpEntity;

import com.example.weathercodysummer.Repository.SignUpRepo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SignUpService {

    @Autowired
    private SignUpRepo repo;

    public void save(SignUp signUp){
        repo.save(signUp);
    }


    public SignUp findByLoginId(String userId, String userPassword){
        SignUpEntity z = repo.findByLoginId(userId).filter(s -> s.getUserPassword().equals(userPassword)).orElse(null);
        if(z==null){
            return null;
        }

        SignUp login = z.toDto();


        return login;
    }

    public List<SignUp> findInfo() {
        List<SignUpEntity> all = repo.findInfo();
        List<SignUp> collect = all.stream().map(SignUpEntity::toDto)
                .collect(Collectors.toList());
        return collect;
    }
}
