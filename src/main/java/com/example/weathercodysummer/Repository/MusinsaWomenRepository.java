package com.example.weathercodysummer.Repository;

import com.example.weathercodysummer.Entity.MusinsaWomenEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MusinsaWomenRepository extends JpaRepository<MusinsaWomenEntity,Long> {

    @Query(value = "update musinsa_women_main set wlike_Num=? where src = ?",nativeQuery = true)
    @Modifying
    @Transactional
    int likeNum(int likeNum,String src);

    @Query(value = "select like_Num from musinsa_women_main where src= ?",nativeQuery = true)
    @Transactional
    int howLike(String src);

    @Query(value = "select src from musinsa_women_main where main_id = ?",nativeQuery = true)
    @Transactional
    String mainSrc(Long id);

    @Query(value = "update musinsa_women_main set like_Num=? where src = ?",nativeQuery = true)
    @Modifying
    @Transactional
    int LikeNum(int likeNum,String src);

}
