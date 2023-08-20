package com.example.weathercodysummer.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "women_sub")
public class SteadyWomanSubImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "w_sub_id")
    private Long id;

    private String src;


    @ManyToOne
    @JoinColumn(name = "w_main_id")
    private SteadyWomanMainImg images;



    public com.example.weathercodysummer.Dto.SteadyWomanSubImg toDto(){
        return new com.example.weathercodysummer.Dto.SteadyWomanSubImg(id, src);
    }
}
