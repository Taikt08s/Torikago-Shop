/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
@Table(name = "BirdCageDetails")
public class BirdCageDetail {
    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product birdCage;
    @Column(name = "dimension", length = 20)
    private String dimension;
    @Column(name = "cage_shape", length = 50)
    private String cageShape;
    @Column(name = "bar_spacing", length = 10)
    private double barSpacing;
    @Column(name = "bird_wingspan", length = 10)
    private double birdWingSpan;
    @Column(name = "description", length = 250)
    private String description;
}
