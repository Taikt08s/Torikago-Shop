package com.group3.torikago.Torikago.Shop.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "BirdcageOrder")
public class BirdcageOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String OrderID;
    private String OrderValue;
    private LocalDateTime OderDate;
    private String Status;
    private String ShippedAddress;
    private LocalDateTime ShippedDate;
    private String PaymentMethod;
    private String UserID;
    private String VoucherID;

}
