package com.example.weathercodysummer.Repository;

import com.example.weathercodysummer.Entity.MainImage;
import com.example.weathercodysummer.Entity.SubImage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class CrawlingRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void saveMainImage(MainImage mainImage){
        em.persist(mainImage);
    }


    @Transactional
    public void saveSubImage(SubImage subImage){

        em.persist(subImage);
    }



    public List<com.example.weathercodysummer.Dto.MainImage> findAll(){
        List<MainImage> mainImageList = em.createQuery("select m from MainImage m", MainImage.class).getResultList();
        List<com.example.weathercodysummer.Dto.MainImage> collect = mainImageList.stream().map(MainImage::toDto).collect(Collectors.toList());
        return collect;
    }


    public List<SubImage> findSubImage(Long id){
        List<SubImage> resultList = em.createQuery("select s from SubImage s join s.image t where " + "t.id=:id", SubImage.class).setParameter("id",  id).getResultList();
        System.out.println(resultList.size());
        return resultList;
    }

    public MainImage findMainSrc(Long id){
        MainImage mainImage = em.find(MainImage.class, id);
        return mainImage;
    }


}
