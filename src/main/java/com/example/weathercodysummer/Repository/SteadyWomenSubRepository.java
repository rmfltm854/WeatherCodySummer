package com.example.weathercodysummer.Repository;


import com.example.weathercodysummer.Entity.MusinasManSubEntity;
import com.example.weathercodysummer.Entity.SubImage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SteadyWomenSubRepository extends JpaRepository<SubImage,Long> {

    @Query(value = "select * from women_sub where main_id = ?",nativeQuery = true)
    @Transactional
    List<MusinasManSubEntity> subSrc(Long id);

}
