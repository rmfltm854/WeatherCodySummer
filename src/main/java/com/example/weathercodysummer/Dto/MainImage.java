package com.example.weathercodysummer.Dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class MainImage {

    private Long id;

    private String src;

    private int likeNum;

    public MainImage(Long id, String src, int likeNum) {
        this.id = id;
        this.src = src;
        this.likeNum = likeNum;
    }


    public com.example.weathercodysummer.Entity.MainImage toEntity(){
        return new com.example.weathercodysummer.Entity.MainImage(null,src,likeNum);
    }

    public static MainImage toDTO(com.example.weathercodysummer.Entity.MainImage entity) {
        return MainImage.builder()
                .id(entity.getId())
                .src(entity.getSrc())
                .likeNum(entity.getLikeNum())
                .build();
    }
}
