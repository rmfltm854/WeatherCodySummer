package com.example.weathercodysummer.Service;

import com.example.weathercodysummer.Dto.ReviewDto;
import com.example.weathercodysummer.Entity.Review;
import com.example.weathercodysummer.Repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class reviewService {

    @Autowired
    ReviewRepository reviewRepo;

    public List<ReviewDto> getReview(String src){
        List<Review> reviewEntity = reviewRepo.getReview(src);
        List<ReviewDto> reviewDto = reviewEntity.stream().map(Review::toDto).toList();
        return reviewDto;
    }
}
