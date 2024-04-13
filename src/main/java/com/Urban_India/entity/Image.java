package com.Urban_India.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image")
@SuperBuilder
public class Image extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    private String name;

    private String type;

//    @Lob
    @Column(name = "imagedata", length = 1000)
    private byte[] imageData;
}
