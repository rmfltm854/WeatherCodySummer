package com.example.weathercodysummer.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SteadyWomanMainImg {

    private Long id;

    private String src;

    public SteadyWomanMainImg(Long id, String src) {
        this.id = id;
        this.src = src;
    }


    public com.example.weathercodysummer.Entity.SteadyWomanMainImg toEntity(){
        return new com.example.weathercodysummer.Entity.SteadyWomanMainImg(null,src);
    }
}
