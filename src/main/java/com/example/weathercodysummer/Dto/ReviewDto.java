package com.example.weathercodysummer.Dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class ReviewDto {

    private Long review_id;
    private String review;
    private String src;

    public ReviewDto(Long review_id,String review,String src){
        this.review_id = review_id;
        this.review = review;
        this.src = src;
    }



}
