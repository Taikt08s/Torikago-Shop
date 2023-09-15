package com.group3.torikago.Torikago.Shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name ="BirdCageOrder")
public class BirdCageOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderID;
    private float orderValue;
    private String paymentMethod;
    private Date orderDate;
    private String status;
    private String shippedAddress;
    private Date shippedDate;
    private String userID;
}
