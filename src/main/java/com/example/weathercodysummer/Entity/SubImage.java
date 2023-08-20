package com.example.weathercodysummer.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "man_sub")
public class SubImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_id")
    private Long id;

    private String src;


    @ManyToOne
    @JoinColumn(name = "main_id")
    private MainImage image;



    public com.example.weathercodysummer.Dto.SubImage toDto(){
        return new com.example.weathercodysummer.Dto.SubImage(id, src);
    }

}
