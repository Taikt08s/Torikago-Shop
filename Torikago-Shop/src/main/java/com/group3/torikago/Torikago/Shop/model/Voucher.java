package com.group3.torikago.Torikago.Shop.model;
<<<<<<< HEAD

=======
>>>>>>> 0151d0a51e910581a97d2fbed6829b2740602bdb
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
@Table(name = "Voucher")
public class Voucher {
    @Id
<<<<<<< HEAD
    @Column(name = "voucher_id")  
    private Long voucherID;
    @Column(name = "voucher_name",length = 50)  
    private String voucherName;
    @Column(name = "voucher_value",length = 5)  
=======
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_id",length = 10)
    private int voucherID;
    @Column(name = "voucher_name",length = 20)
    private String voucherName;
    @Column(name = "voucher_value",length = 10)
>>>>>>> 0151d0a51e910581a97d2fbed6829b2740602bdb
    private float voucherValue;
    @OneToOne(mappedBy = "voucher")
    private BirdCageOrder birdCageOrder;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User userVoucher;
}
