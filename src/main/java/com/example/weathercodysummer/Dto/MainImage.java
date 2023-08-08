package com.example.weathercodysummer.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MainImage {

    private Long id;

    private String src;

    public MainImage(Long id, String src) {
        this.id = id;
        this.src = src;
    }


    public com.example.weathercodysummer.Entity.MainImage toEntity(){
        return new com.example.weathercodysummer.Entity.MainImage(null,src);
    }
}
