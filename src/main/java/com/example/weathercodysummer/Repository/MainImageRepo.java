package com.example.weathercodysummer.Repository;

import com.example.weathercodysummer.Entity.MainImage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MainImageRepo extends JpaRepository<MainImage,Long> {

    @Query(value = "select *, RANK() OVER (ORDER BY like_num DESC)as ranking from main_image limit 3;", nativeQuery = true)
    List<MainImage> getRanking();


    @Query(value = "update main_image set like_Num=? where src = ?",nativeQuery = true)
    @Modifying
    @Transactional
    int LikeNum(int likeNum,String src);

    @Query(value = "select like_Num from main_image where src= ?",nativeQuery = true)
    @Transactional
    int howLike(String src);

}