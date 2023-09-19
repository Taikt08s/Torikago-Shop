package com.group3.torikago.Torikago.Shop.DTO;

import com.group3.torikago.Torikago.Shop.model.OrderDetails;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class Bird_CageDTO {
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
    private List<OrderDetails> orderdetails;
}
