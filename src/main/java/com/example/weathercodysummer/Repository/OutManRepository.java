package com.example.weathercodysummer.Repository;

import com.example.weathercodysummer.Dto.OutManDTO;
import com.example.weathercodysummer.Entity.OutManImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutManRepository extends JpaRepository<OutManImage,Long> {


}
