/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3.torikago.Torikago.Shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OrderDetails")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "quantity", length = 10)
    private int quantity;
    @Column(name = "unit_price", length = 10)
    private double unitPrice;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private BirdCageOrder order;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product ;
}