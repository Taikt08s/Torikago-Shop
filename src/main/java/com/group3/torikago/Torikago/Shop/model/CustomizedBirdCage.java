package com.group3.torikago.Torikago.Shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "CustomizedBirdCage")
public class CustomizedBirdCage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product customizedBirdCage;
    @Column(name = "dimension", length = 20)
    private String dimension;
    @Column(name = "cage_shape", length = 50)
    private String cageShape;
    @Column(name = "bar_spacing", length = 10)
    private double barSpacing;
    @Column(name = "bird_wingspan", length = 10)
    private double birdWingSpan;
    @Column(name = "status", length = 20)
    private String status;
    @Column(name = "description", length = 2000)
    private String description;

}
