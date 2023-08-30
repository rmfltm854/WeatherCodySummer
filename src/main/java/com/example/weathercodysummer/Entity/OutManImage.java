package com.example.weathercodysummer.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "man_out")
public class OutManImage {

    @Id
    @Column(name = "src_id")
    private Long id;

    private String src;


    public OutManImage(Long id, String src) {
        this.id = id;
        this.src = src;
    }
    public com.example.weathercodysummer.Dto.OutManDTO toDto(){
        return new com.example.weathercodysummer.Dto.OutManDTO(id, src);
    }
}
