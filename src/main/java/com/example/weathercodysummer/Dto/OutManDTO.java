package com.example.weathercodysummer.Dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class OutManDTO {

    private Long id;

    private String src;

    public OutManDTO(Long id, String src) {
        this.id = id;
        this.src = src;
    }

    public com.example.weathercodysummer.Entity.OutManImage toEntity(){
        return new com.example.weathercodysummer.Entity.OutManImage(id,src);
    }

    public static OutManDTO toDTO(com.example.weathercodysummer.Entity.OutManImage entity) {
        return OutManDTO.builder()
                .id(entity.getId())
                .src(entity.getSrc())
                .build();
    }

}
