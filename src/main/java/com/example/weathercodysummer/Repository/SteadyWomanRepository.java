package com.example.weathercodysummer.Repository;

import com.example.weathercodysummer.Entity.SteadyWomanMainImg;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SteadyWomanRepository extends JpaRepository<SteadyWomanMainImg,Long> {

    @Query(value = "select *, RANK() OVER (ORDER BY wlike_num DESC)as ranking from women_main limit 8;", nativeQuery = true)
    List<SteadyWomanMainImg> getRanking();


    @Query(value = "update women_main set wlike_Num=? where src = ?",nativeQuery = true)
    @Modifying
    @Transactional
    int likeNum(int likeNum,String src);

    @Query(value = "select wlike_Num from women_main where src= ?",nativeQuery = true)
    @Transactional
    int howLike(String src);

}
