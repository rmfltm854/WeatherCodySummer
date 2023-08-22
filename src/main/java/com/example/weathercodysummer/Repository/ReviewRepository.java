package com.example.weathercodysummer.Repository;

import com.example.weathercodysummer.Entity.Review;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    @Query(value = "select * from review where src = ?",nativeQuery = true)
    @Modifying
    @Transactional
    List<Review> getReview(String src);
}
