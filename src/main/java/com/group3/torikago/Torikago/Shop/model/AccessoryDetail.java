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
@Table(name = "AccessoryDetails")
public class AccessoryDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name ="id")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product accessory;
    @Column(name = "type_of_accessory", length = 20)
    private String accessoryType;
    @Column(name = "description", length = 2000)
    private String description;  
}
