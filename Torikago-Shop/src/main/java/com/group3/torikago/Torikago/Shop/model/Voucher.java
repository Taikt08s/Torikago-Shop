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
@Entity (name = "Voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_id",length = 10)
    private int voucherID;
    @Column(name = "voucher_name",length = 20)
    private String voucherName;
    @Column(name = "voucher_value",length = 10)
    private float voucherValue;
}
