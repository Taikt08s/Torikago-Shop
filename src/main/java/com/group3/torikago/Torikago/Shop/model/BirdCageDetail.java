/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3.torikago.Torikago.Shop.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "bird_cage_details")
public class BirdCageDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product birdCage;
    @Column(name = "dimension", length = 20)
    private String dimension;
    @Column(name = "cage_shape", length = 50)
    private String cageShape;
    @Column(name = "bar_spacing", length = 10)
    private double barSpacing;
    @Column(name = "bird_wingspan", length = 10)
    private double birdWingSpan;
    @Column(name = "material", length = 50)
    private String material;
    @Column(name = "color", length = 50)
    private String color;
    @Column(name = "description", length = 2000)
    private String description;
}