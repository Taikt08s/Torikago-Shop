package com.group3.torikago.Torikago.Shop.dto;

import com.group3.torikago.Torikago.Shop.model.AccessoryDetail;
import com.group3.torikago.Torikago.Shop.model.BirdCageDetail;
import com.group3.torikago.Torikago.Shop.model.OrderDetails;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@Builder
public class ProductDTO {
    private String productName;
    private String productType;
    private String image;
    private double unitPrice;
    private int unitsInStock;
    @Min(value = 0)
    private int unitsOnOrder;
    private String status;
}
