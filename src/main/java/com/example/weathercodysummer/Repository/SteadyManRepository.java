package com.example.weathercodysummer.Repository;

import com.example.weathercodysummer.Entity.MainImage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SteadyManRepository extends JpaRepository<MainImage,Long> {
    @Query(value = "select src from man_main where main_id = ?",nativeQuery = true)
    @Transactional
    String mainSrc(Long id);
}
