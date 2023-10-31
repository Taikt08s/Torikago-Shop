package com.group3.torikago.Torikago.Shop.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    @NotEmpty(message = "*Required")
    @Length(max = 80, message = "No more than 80 characters")
    private String productName;
    private String productType;
    private String mainImage;
    private String extraImage1;
    private String extraImage2;
    private String extraImage3;
    @NotNull(message = "*Required")
    private Double unitPrice;
    @NotNull(message = "*Required")
    @Min(value = 0, message = "*Greater or equal to 0")
    private int unitsInStock;
    private int unitsOnOrder;
    @NotNull(message = "*Required")
    private Boolean status;
    private Boolean featureProduct;
}
