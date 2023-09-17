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
@Entity(name = "OrderDetails")
public class OrderDetails {
    @Id
    private int Quantity;
    private double UnitPrice;   
    private long OrderID;
    @ManyToOne
    @JoinColumn(name = "OrderDetailsID")
    private Bird_Cage product ;
//    private BirdCageAccessory accessory;
}
