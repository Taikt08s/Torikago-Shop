package com.group3.torikago.Torikago.Shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

//import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name ="BirdCageOrder")
public class BirdCageOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;
    private float orderValue;
    private String paymentMethod;
    private LocalDateTime orderDate;
    private String status;
    private String shippedAddress;
    private LocalDateTime shippedDate;
    private String userID;
}
