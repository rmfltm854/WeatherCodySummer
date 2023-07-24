package com.example.weathercodysummer.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.example.weathercodysummer.Dto.SignUp;
import com.example.weathercodysummer.Entity.SignUpEntity;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SignUpRepo {

    @PersistenceContext
    private EntityManager em; //EntityManager 활용

    @Transactional
    public void save(SignUp dto){

        SignUpEntity entity = dto.toEntity(); // Dto to Entity
        em.persist(entity); // DB에 저장
        em.flush();
    }

    @Transactional
    public Optional<SignUpEntity> findByLoginId(String userId){

        Optional<SignUpEntity> getUserInfo = findInfo().stream().filter(s -> s.getUserId().equals(userId)).findFirst(); // DB에서 받아온 userId값과 일치한 userId값을 찾아와서 Optional로 한 번 감싼다
        System.out.println(getUserInfo.toString());

        return getUserInfo;

    }

    public List<SignUpEntity> findInfo(){

        List<SignUpEntity> allUserInfo = em.createQuery("select s from SignUpEntity s", SignUpEntity.class) // 쿼리문 활용하여 리스트에 userInfo 담기
                .getResultList();

        return allUserInfo;
    }
}
