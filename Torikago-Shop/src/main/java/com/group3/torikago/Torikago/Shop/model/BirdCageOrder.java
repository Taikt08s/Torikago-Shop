package com.group3.torikago.Torikago.Shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name ="BirdCageOrder")
public class BirdCageOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;
    @Column(name = "order_value",length = 10)
    private float orderValue;  
    @Column(name = "payment_method",length = 50) 
    private String paymentMethod;
    @Column(name = "order_date",length = 10)    
    private LocalDateTime orderDate;
    @Column(name = "status",length = 50)  
    private String status;
    @Column(name = "shipped_address",length = 50)   
    private String shippedAddress;
    @Column(name = "shipped_date")  
    private LocalDateTime shippedDate;
    @Column(name = "user_id",length = 10)  
    private String userID;   
}
