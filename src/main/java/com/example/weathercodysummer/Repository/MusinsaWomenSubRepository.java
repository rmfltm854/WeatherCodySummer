package com.example.weathercodysummer.Repository;

import com.example.weathercodysummer.Entity.MusinasManSubEntity;
import com.example.weathercodysummer.Entity.MusinsaWomenSubEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MusinsaWomenSubRepository extends JpaRepository<MusinsaWomenSubEntity,Long> {
    @Query(value = "select * from musinsa_women_sub where main_id = ?",nativeQuery = true)
    @Transactional
    List<MusinsaWomenSubEntity> subSrc(Long id);

}
