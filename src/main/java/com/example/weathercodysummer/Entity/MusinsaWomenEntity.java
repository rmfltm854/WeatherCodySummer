package com.example.weathercodysummer.Entity;

import com.example.weathercodysummer.Dto.MuinsaManDTO;
import com.example.weathercodysummer.Dto.MusinsaWomenDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "MusinsaWomen_main")
public class MusinsaWomenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "main_id")
    private Long id;

    private String src;

    private int likeNum;

//    @OneToMany(mappedBy = "reviewByImg")
//    private List<Review> reviews = new ArrayList<>();

    /**
     @ManyToOne
     @JoinColumn(name = "reviews")
     @Convert(converter = SignUpEntity.class)
     private List<String> reviews;
     */

    public MusinsaWomenEntity(Long id,String src,int likeNum) {
        this.id = id;
        this.src = src;
        this.likeNum = likeNum;
    }

    public MusinsaWomenEntity(String src){
        this.src = src;
    }



    public MusinsaWomenDTO toDto(){
        return new MusinsaWomenDTO(id, src,likeNum);
    }
}
