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
@Table(name ="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long orderId;
    @Column(name = "order_value", length = 10)
    private double orderValue;
    @Column(name = "payment_method", length = 50)
    private String paymentMethod;
    @Column(name = "order_date", length = 10)
    private LocalDateTime orderDate = LocalDateTime.now();
    @Column(name = "status", length = 50)
    private String status;
    @Column(name = "shipped_address", length = 150)
    private String shippedAddress;
    @Column(name = "shipped_date", length = 10, nullable = true)
    private LocalDateTime shippedDate = orderDate.plusDays(7) ;
    @OneToMany(mappedBy = "order")
    private List<OrderDetails> orderdetails;
    @OneToOne(mappedBy = "orderFeedback")
    private Feedback feedback;
    //    @OneToOne
//    @JoinColumn(name = "voucher_id", referencedColumnName = "id")
//    private Voucher voucher;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User userOrder;
}