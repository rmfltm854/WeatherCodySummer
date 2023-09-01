package com.example.weathercodysummer.Repository;

import com.example.weathercodysummer.Entity.UmbrellaEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UmbrellaRepository extends JpaRepository<UmbrellaEntity,Long> {

    @Query(value = "Select src From umbrella Order by rand()Limit 1;",nativeQuery = true)
    @Transactional
    String getUmbrella();

}
