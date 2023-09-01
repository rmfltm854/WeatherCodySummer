package com.example.weathercodysummer.Repository;

import com.example.weathercodysummer.Entity.MusinsaManEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MusinsaManRepository extends JpaRepository<MusinsaManEntity,Long> {

    @Query(value = "update musinsa_main set wlike_Num=? where src = ?",nativeQuery = true)
    @Modifying
    @Transactional
    int likeNum(int likeNum,String src);

    @Query(value = "select like_Num from musinsa_main where src= ?",nativeQuery = true)
    @Transactional
    int howLike(String src);

    @Query(value = "select src from musinsa_main where main_id = ?",nativeQuery = true)
    @Transactional
    String mainSrc(Long id);

    @Query(value = "update musinsa_main set like_Num=? where src = ?",nativeQuery = true)
    @Modifying
    @Transactional
    int LikeNum(int likeNum,String src);


}
