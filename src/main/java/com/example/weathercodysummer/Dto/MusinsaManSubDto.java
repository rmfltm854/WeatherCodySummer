package com.example.weathercodysummer.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MusinsaManSubDto {

    private Long id;

    private String src;

    public MusinsaManSubDto(Long id, String src){
        this.id=id;
        this.src=src;
    }
}
