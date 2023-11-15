package com.group3.torikago.Torikago.Shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    @Column(name = "voucher_created_date")
    private LocalDateTime createdTime;
    @Column(name = "voucher_expried_date")
    private LocalDateTime expiredTime;
    @Column(name = "voucher_status")
    private Boolean status;
    @Column(name = "voucher_maxValue")
    private double maxValue;
    @OneToMany(mappedBy = "voucher")
    private List<Order> birdCageOrder;

}