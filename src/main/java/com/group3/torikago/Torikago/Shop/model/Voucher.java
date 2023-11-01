package com.group3.torikago.Torikago.Shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity 
@Table(name = "voucher")

public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")

    private Long id;
    @Column(name = "voucher_name",length = 50)
    private String voucherName;
    @Column(name = "voucher_value",length = 5)
    private float voucherValue;

    @Column(name = "voucher_cTime")

    private LocalDateTime createdTime;
    @Column(name = "voucher_eTime")
    private LocalDateTime expiredTime;
    @Column(name = "voucher_status")
    private Boolean status;

//    @OneToOne(mappedBy = "voucher")
//    private BirdCageOrder birdCageOrder;

//    @OneToOne(mappedBy = "voucher")
//    private Order birdCageOrder;

}