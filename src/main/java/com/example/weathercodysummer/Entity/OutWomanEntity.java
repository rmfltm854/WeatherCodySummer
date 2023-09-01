package com.example.weathercodysummer.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Woman_URL")
public class OutWomanEntity {

    @Id
    @Column(name = "src_id")
    private Long id;

    private String src;


    public OutWomanEntity(Long id, String src) {
        this.id = id;
        this.src = src;
    }
    public com.example.weathercodysummer.Dto.WomanUrlDto toDto(){
        return new com.example.weathercodysummer.Dto.WomanUrlDto(id, src);
    }
}