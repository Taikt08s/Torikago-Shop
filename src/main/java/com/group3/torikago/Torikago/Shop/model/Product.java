package com.group3.torikago.Torikago.Shop.model;

import jakarta.persistence.*;
import jakarta.persistence.OneToOne;

import java.util.List;
import java.util.UUID;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "product_name", length = 80)
    private String productName;
    @Column(name = "product_type", length = 15)
    private String productType;
    @Column(name = "imageMain")
    private String mainImage;
    @Column(name = "extra_image1")
    private String extraImage1;
    @Column(name = "extra_image2")
    private String extraImage2;
    @Column(name = "extra_image3")
    private String extraImage3;
    @Column(name = "unit_price", length = 10)
    private double unitPrice;
    @Column(name = "unit_weight", length = 10)
    private Double unitWeight;
    @Column(name = "unit_in_stock", length = 11)
    private int unitsInStock;
    @Column(name = "unit_on_order", length = 10)
    private int unitsOnOrder;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "feature_product")
    private Boolean featureProduct;
    @OneToMany(mappedBy = "product")
    private List<OrderDetails> orderDetails;
    @OneToOne(mappedBy = "birdCage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BirdCageDetail birdCageDetail;
    @OneToOne(mappedBy = "customizedBirdCage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CustomizedBirdCage customizedBirdCage;
    @OneToOne(mappedBy = "accessory")
    private AccessoryDetail accessoryDetail;
    @OneToMany(mappedBy = "productId")
    private List<CartItems> cartItems;
}


