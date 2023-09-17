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
    @Column(name = "order_value",length = 10)
    private String paymentMethod;
    @Column(name = "payment_method",length = 50)
    private LocalDateTime orderDate;
    @Column(name = "order_date",length = 10)
    private String status;
    @Column(name = "status",length = 50)
    private String shippedAddress;
    @Column(name = "shipped_address",length = 50)
    private LocalDateTime shippedDate;
    @Column(name = "shipped_date",length = 50)
    private String userID;
    @Column(name = "user_id",length = 10)
}
