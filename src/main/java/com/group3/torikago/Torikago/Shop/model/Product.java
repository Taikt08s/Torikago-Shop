package com.group3.torikago.Torikago.Shop.model;

import jakarta.persistence.*;
import jakarta.persistence.OneToOne;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "product_name", length = 80)
    private String productName;
    @Column(name = "product_type", length = 15)
    private String productType;
    @Column(name = "imageMain", length = 45)
    private String mainImage;
    @Column(name = "extra_image1", length = 45, nullable = false)
    private String extraImage1;
    @Column(name = "extra_image2", length = 45, nullable = false)
    private String extraImage2;
    @Column(name = "extra_image3", length = 45, nullable = false)
    private String extraImage3;
    @Column(name = "unit_price", length = 10)
    private double unitPrice;
    @Column(name = "unit_in_stock", length = 11)
    private int unitsInStock;
    @Column(name = "unit_on_order", length = 10)
    private int unitsOnOrder;
    @Column(name = "status", length = 20)
    private String status;
    @OneToMany(mappedBy = "product")
    private List<OrderDetails> orderDetails;
    @OneToOne(mappedBy = "birdCage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BirdCageDetail birdCageDetail;
    @OneToOne(mappedBy = "accessory")
    private AccessoryDetail accessoryDetail;
    @Transient
    public String getMainImagePath() {
        String imagePath = "/product-images/" + id + "/" + mainImage;
        System.out.println("Image Path: " + imagePath);
        return imagePath;
    }
    @Transient
    public String getExtraImagePath1() {
        if (id == null || extraImage1 == null) {
            return null;
        }
        return "/product-images/" + id + "/" + extraImage1;
    }
    @Transient
    public String getExtraImagePath2() {
        if (id == null || extraImage2 == null) {
            return null;
        }
        return "/product-images/" + id + "/" + extraImage2;
    }
    @Transient
    public String getExtraImagePath3() {
        if (id == null || extraImage3 == null) {
            return null;
        }
        return "/product-images/" + id + "/" + extraImage3;
    }
}


