package com.example.weathercodysummer.Repository;

import com.example.weathercodysummer.Entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Repository
@RequiredArgsConstructor
public class CrawlingRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * crawling1 -> steadyclub 남자 이미지 크롤링 관련 메서드
     * @param mainImage
     */
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

    /**
     * crawling2 -> steadyclub 여자 이미지 크롤링 관련 메서드
     */
    @Transactional
    public void saveMainImage2(SteadyWomanMainImg mainImage){
        em.persist(mainImage);
    }


    @Transactional
    public void saveSubImage2(SteadyWomanSubImg subImage){

        em.persist(subImage);
    }

    public List<com.example.weathercodysummer.Dto.SteadyWomanMainImg> findAll2(){
        List<SteadyWomanMainImg> mainImageList = em.createQuery("select m from SteadyWomanMainImg m", SteadyWomanMainImg.class).getResultList();
        List<com.example.weathercodysummer.Dto.SteadyWomanMainImg> collect = mainImageList.stream().map(SteadyWomanMainImg::toDto).collect(Collectors.toList());
        return collect;
    }

    public List<SteadyWomanSubImg> findSubImage2(Long id){
        List<SteadyWomanSubImg> resultList = em.createQuery("select s from SteadyWomanSubImg s join s.images t where " + "t.id=:id", SteadyWomanSubImg.class).setParameter("id",  id).getResultList();
        System.out.println(resultList.size());
        return resultList;
    }

    public SteadyWomanMainImg findMainSrc2(Long id){
        SteadyWomanMainImg mainImage = em.find(SteadyWomanMainImg.class, id);
        return mainImage;
    }



}
