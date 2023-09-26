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
    //    @NotNull(message = "*Product price is required")
//    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Double unitPrice;

    private int unitsInStock;
    @Min(value = 0)
    private int unitsOnOrder;

    private String status;
}
