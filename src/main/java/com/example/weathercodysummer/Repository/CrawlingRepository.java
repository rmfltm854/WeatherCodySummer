package com.example.weathercodysummer.Repository;

import com.example.weathercodysummer.Entity.MainImage;
import com.example.weathercodysummer.Entity.SignUpEntity;
import com.example.weathercodysummer.Entity.SubImage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class CrawlingRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save2(MainImage mainImage){
        em.persist(mainImage);
    }


    @Transactional
    public void save(SubImage subImage){
        em.persist(subImage);
    }

    public List<com.example.weathercodysummer.Dto.MainImage> findAll(){
        List<com.example.weathercodysummer.Dto.MainImage> mainImageList = em.createQuery("select m.src from MainImage m", com.example.weathercodysummer.Dto.MainImage.class).getResultList();
        return mainImageList;
    }
}
