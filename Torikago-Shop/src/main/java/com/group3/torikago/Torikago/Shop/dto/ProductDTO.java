package com.group3.torikago.Torikago.Shop.dto;

import com.group3.torikago.Torikago.Shop.model.AccessoryDetail;
import com.group3.torikago.Torikago.Shop.model.BirdCageDetail;
import com.group3.torikago.Torikago.Shop.model.OrderDetails;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@Builder

public class ProductDTO {
    private Long productId;
    @NotEmpty(message = "Product name is required")
    @Length(max = 50,message = "No more than 50 characters")
    private String productName;
    @NotEmpty(message = "Image is required")
    private String image;

    private double unitPrice;

    private int unitsInStock;

    private int unitsOnOrder;
    @NotEmpty(message = "Status")
    private String status;



    private BirdCageDetail birdCageDetail;

    private AccessoryDetail accessoryDetail;
}
