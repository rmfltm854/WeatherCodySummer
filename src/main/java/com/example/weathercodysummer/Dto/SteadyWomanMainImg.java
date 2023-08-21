package com.example.weathercodysummer.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SteadyWomanMainImg {

    private Long id;

    private String src;

    private int WlikeNum;

    public SteadyWomanMainImg(Long id, String src, int WlikeNum) {
        this.id = id;
        this.src = src;
        this.WlikeNum = WlikeNum;
    }


    public com.example.weathercodysummer.Entity.SteadyWomanMainImg toEntity(){
        return new com.example.weathercodysummer.Entity.SteadyWomanMainImg(id,src,WlikeNum);
    }
}
