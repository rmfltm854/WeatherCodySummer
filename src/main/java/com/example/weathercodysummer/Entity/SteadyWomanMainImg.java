package com.example.weathercodysummer.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "women_main")
public class SteadyWomanMainImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "w_main_id")
    private Long id;

    private String src;

    private int WlikeNum;

    @OneToMany(mappedBy = "images")
    private List<SteadyWomanSubImg> subImages = new ArrayList<>();


    public SteadyWomanMainImg(Long id,String src,int WlikeNum) {
        this.id = id;
        this.src = src;
        this.WlikeNum = WlikeNum;
    }

    public SteadyWomanMainImg(String src){
        this.src = src;
    }



    public com.example.weathercodysummer.Dto.SteadyWomanMainImg toDto(){
        return new com.example.weathercodysummer.Dto.SteadyWomanMainImg(id, src,WlikeNum);
    }
}
