package com.group3.torikago.Torikago.Shop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class ProductDTO {
    @NotEmpty(message = "Product name is required")
    @Length(max = 80,message = "No more than 80 characters")
    private String productName;
    private String productType;
    private String image;

    private double unitPrice;

    private int unitsInStock;
    @Min(value = 0)
    private int unitsOnOrder;

    private String status;
}
