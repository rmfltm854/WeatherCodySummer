package com.example.weathercodysummer.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Musinsa_sub")
public class MusinasManSubEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_id")
    private Long id;

    private String src;


    @ManyToOne
    @JoinColumn(name = "main_id")
    private MusinsaManEntity image;



    public com.example.weathercodysummer.Dto.MusinsaManSubDto toDto(){
        return new com.example.weathercodysummer.Dto.MusinsaManSubDto(id, src);
    }
}
