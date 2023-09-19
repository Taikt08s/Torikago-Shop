package com.group3.torikago.Torikago.Shop.model;
<<<<<<< HEAD
<<<<<<< HEAD

=======
>>>>>>> 0151d0a51e910581a97d2fbed6829b2740602bdb
import jakarta.persistence.*;
=======
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
>>>>>>> refs/remotes/origin/main
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
    private int voucherID;
    private String voucherName;
<<<<<<< HEAD
    @Column(name = "voucher_value",length = 10)
>>>>>>> 0151d0a51e910581a97d2fbed6829b2740602bdb
=======
>>>>>>> refs/remotes/origin/main
    private float voucherValue;
    @OneToOne(mappedBy = "voucher")
    private BirdCageOrder birdCageOrder;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User userVoucher;
}
