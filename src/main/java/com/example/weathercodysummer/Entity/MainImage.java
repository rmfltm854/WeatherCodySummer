package com.example.weathercodysummer.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "man_main")
public class MainImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "main_id")
    private Long id;

    private String src;

    private int likeNum;

    @OneToMany(mappedBy = "image")
    private List<SubImage> subImages = new ArrayList<>();


    public MainImage(Long id,String src,int likeNum) {
        this.id = id;
        this.src = src;
        this.likeNum = likeNum;
    }

    public MainImage(String src){
        this.src = src;
    }



    public com.example.weathercodysummer.Dto.MainImage toDto(){
        return new com.example.weathercodysummer.Dto.MainImage(id, src,likeNum);
    }

}
