/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3.torikago.Torikago.Shop.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Bird-cage Product")
public class Bird_Cage{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ProductID;
    private String ProductName;
    private double UnitPrice;
    private int UnitsInStock;
    private int UnitsOnOrder;
    private String Dimension;
    private String CageShape;
    private double BarSpacing;
    private double BirdWingSpan;
    private String Status;
//    @OneToMany(mappedBy = "pro")
//    private List<OrderDetails> orderdetails;
}
