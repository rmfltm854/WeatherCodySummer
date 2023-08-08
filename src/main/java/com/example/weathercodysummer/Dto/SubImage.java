package com.example.weathercodysummer.Dto;

import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor
public class SubImage {

    private Long id;

    private String src;

    public SubImage(Long id, String src){
        this.id=id;
        this.src=src;
    }

}
