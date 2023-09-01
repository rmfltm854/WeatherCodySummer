package com.example.weathercodysummer.Repository;

import com.example.weathercodysummer.Entity.MusinasManSubEntity;
import com.example.weathercodysummer.Entity.SubImage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SteadyManSubRepository extends JpaRepository<SubImage,Long> {

}
