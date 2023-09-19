/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3.torikago.Torikago.Shop.model;

import jakarta.persistence.*;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author huvo2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Product")
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "product_name", length = 50)
    private String productName;
    @Column(name = "image", length = 64)
    private String image;
    @Column(name = "unit_price", length = 10)
    private double unitPrice;
    @Column(name = "unit_in_stock", length = 10)
    private int unitsInStock;
    @Column(name = "unit_on_order", length = 10)
    private int unitsOnOrder;
    @Column(name = "status", length = 5)
    private String status;
    @OneToMany(mappedBy = "product")
    private List<OrderDetails> orderdetails;
    @OneToOne(mappedBy = "productdetail")
    private BirdCageDetail birdcagedetail;
}


