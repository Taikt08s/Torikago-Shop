package com.group3.torikago.Torikago.Shop.model;

import jakarta.persistence.*;
import lombok.*;

//import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="BirdCageAccessory")
public class BirdCageAccessory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accessoryID;
    private String getAccessoryName;
    @Column(name = "accessory_name",length = 50)
    private float price;
    @Column(name = "price",length = 10)
}
