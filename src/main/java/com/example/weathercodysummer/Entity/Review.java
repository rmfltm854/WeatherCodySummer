package com.example.weathercodysummer.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private String review;

    private String src;//mainImage IDX값을 넣어주면 woman 정보는 어떻게?? , Musinsa 도 넣어줘야하는데?? 그냥 String 으로 주고
                       //sql로 빼오는게 괜찮을것같습니다 ^^

    @ManyToOne
    @JoinColumn(name = "mem_id")
    private SignUpEntity reviewByUser;

    public com.example.weathercodysummer.Dto.ReviewDto toDto(){
        return new com.example.weathercodysummer.Dto.ReviewDto(id, review,src);
    }

}
