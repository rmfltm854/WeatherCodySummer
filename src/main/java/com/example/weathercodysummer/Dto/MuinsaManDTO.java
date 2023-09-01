package com.example.weathercodysummer.Dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class MuinsaManDTO {

    private Long id;

    private String src;

    private int likeNum;

    public MuinsaManDTO(Long id, String src, int likeNum) {
        this.id = id;
        this.src = src;
        this.likeNum = likeNum;
    }


    public com.example.weathercodysummer.Entity.MusinsaManEntity  toEntity(){
        return new com.example.weathercodysummer.Entity.MusinsaManEntity(null,src,likeNum);
    }

    public static MuinsaManDTO toDTO(com.example.weathercodysummer.Entity.MusinsaManEntity entity) {
        return MuinsaManDTO.builder()
                .id(entity.getId())
                .src(entity.getSrc())
                .likeNum(entity.getLikeNum())
                .build();
    }
}
