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
    private EntityManager em;

    @Transactional
    public void save(SignUp dto){
        SignUpEntity entity = dto.toEntity();
        em.persist(entity);

    }

    @Transactional
    public Optional<SignUpEntity> findByLoginId(String userId){
        Optional<SignUpEntity> first = findInfo().stream().filter(s -> s.getUserId().equals(userId)).findFirst();
        System.out.println(first.toString());
        return first;

    }

    public List<SignUpEntity> findInfo(){
        List<SignUpEntity> li = em.createQuery("select s from SignUpEntity s", SignUpEntity.class)
                .getResultList();
        return li;
    }
}
