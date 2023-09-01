package com.example.weathercodysummer.Repository;

import com.example.weathercodysummer.Entity.MusinasManSubEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MusinsaManSubRepository extends JpaRepository<MusinasManSubEntity,Long> {
    @Query(value = "select * from musinsa_sub where main_id = ?",nativeQuery = true)
    @Transactional
    List<MusinasManSubEntity> subSrc(Long id);
}
