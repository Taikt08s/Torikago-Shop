package com.group3.torikago.Torikago.Shop.model;

import jakarta.persistence.*;
import lombok.*;

//import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name ="BirdCageAccessory")
public class BirdCageAccessory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accessoryID;
    @Column(name = "accessory_name",length = 50)
    private String getAccessoryName;
    @Column(name = "price",length = 10)
    private float price;    
}
