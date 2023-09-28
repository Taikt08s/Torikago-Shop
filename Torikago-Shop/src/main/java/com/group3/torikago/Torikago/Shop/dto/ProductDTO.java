package com.group3.torikago.Torikago.Shop.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class ProductDTO {
    private Long id;
    @NotEmpty(message = "*Required")
    @Length(max = 80, message = "No more than 80 characters")
    private String productName;
    private String productType;
    @NotEmpty(message = "*Required")
    private String mainImage;
    @NotEmpty(message = "*Required")
    private String extraImage1;
    @NotEmpty(message = "*Required")
    private String extraImage2;
    @NotEmpty(message = "*Required")
    private String extraImage3;
    @NotNull(message = "*Required")
    private Double unitPrice;
    @NotNull(message = "*Required")
    @Min(value = 1, message = "*Greater or equal to 1")
    private int unitsInStock;
    private int unitsOnOrder;
    @NotEmpty(message = "*Required")
    @Length(max = 20, message = "No more than 20 characters")
    private String status;

    @Transient
    public String getMainImagePath() {
        if (id == null || mainImage == null) {
            return null;
        }
        return "/product-images/" + id + "/" + mainImage;
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
