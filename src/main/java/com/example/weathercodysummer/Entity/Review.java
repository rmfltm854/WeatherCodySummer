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

    @ManyToOne
    @JoinColumn(name = "mem_id")
    private SignUpEntity reviewByUser;

    @ManyToOne
    @JoinColumn(name = "main_id")
    private MainImage reviewByImg;

}
