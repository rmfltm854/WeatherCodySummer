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
@Table(name = "MusinsaWomen_sub")
public class MusinsaWomenSubEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_id")
    private Long id;

    private String src;


    @ManyToOne
    @JoinColumn(name = "main_id")
    private MusinsaWomenEntity image;



    public com.example.weathercodysummer.Dto.MusinsaWomenSubDTO toDto(){
        return new com.example.weathercodysummer.Dto.MusinsaWomenSubDTO(id, src);
    }
}
