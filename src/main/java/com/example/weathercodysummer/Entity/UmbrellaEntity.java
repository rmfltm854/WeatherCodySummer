package com.example.weathercodysummer.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Umbrella")
public class UmbrellaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String src;

    public UmbrellaEntity(Long id,String src) {
        this.id = id;
        this.src = src;
    }

    public UmbrellaEntity(String src){
        this.src = src;
    }
}
