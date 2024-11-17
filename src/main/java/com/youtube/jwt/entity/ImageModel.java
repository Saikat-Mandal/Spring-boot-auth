package com.youtube.jwt.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="images")
public class ImageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false) // Foreign key reference to Product
    private Product product;
}
