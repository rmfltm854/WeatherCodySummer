package com.example.weathercodysummer.Dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class WomanUrlDto {

    private Long id;

    private String src;

    public WomanUrlDto(Long id, String src) {
        this.id = id;
        this.src = src;
    }

    public com.example.weathercodysummer.Entity.OutWomanEntity toEntity() {
        return new com.example.weathercodysummer.Entity.OutWomanEntity(id, src);
    }

    public static WomanUrlDto toDTO(com.example.weathercodysummer.Entity.OutWomanEntity entity) {
        return WomanUrlDto.builder()
                .id(entity.getId())
                .src(entity.getSrc())
                .build();
    }
}
