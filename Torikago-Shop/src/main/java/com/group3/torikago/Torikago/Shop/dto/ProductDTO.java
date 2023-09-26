package com.group3.torikago.Torikago.Shop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class ProductDTO {
    @NotEmpty(message = "*Product name is required")
    @Length(max = 80,message = "No more than 80 characters")
    private String productName;
    private String productType;
    private String image;
    @NotNull(message = "*Product price is required")
    private Double unitPrice;
    @NotNull(message = "*In Stock is required")
    @Min(value = 1, message = "In Stock must be greater than or equal to 1")
    private int unitsInStock;
    
    private int unitsOnOrder;
    @NotEmpty(message = "*Status is required")
    @Length(max = 20,message = "No more than 20 characters")
    private String status;
}
