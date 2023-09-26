package com.group3.torikago.Torikago.Shop.dto;

import com.group3.torikago.Torikago.Shop.model.Product;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Builder
@Data
public class AccessoryDTO {
    @NotEmpty(message = "*Bird Cage Accessory is required")
    private Product accessory;
    @Length(max = 80,message = "No more than 20 characters")
    private String accessoryType;
    @Length(max = 80,message = "No more than 250 characters")
    private String description;
}
