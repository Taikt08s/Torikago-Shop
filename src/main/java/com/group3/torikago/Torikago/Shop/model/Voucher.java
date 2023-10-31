package com.group3.torikago.Torikago.Shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity 
@Table(name = "voucher")
public class Voucher {
    @Id
    @Column(name = "id")  
    private Long id;
    @Column(name = "voucher_name",length = 50)  
    private String voucherName;
    @Column(name = "voucher_value",length = 5)  
    private float voucherValue;
//    @OneToOne(mappedBy = "voucher")
//    private Order birdCageOrder;
}