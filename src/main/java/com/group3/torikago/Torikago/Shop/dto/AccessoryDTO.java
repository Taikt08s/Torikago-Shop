package com.group3.torikago.Torikago.Shop.dto;

import com.group3.torikago.Torikago.Shop.model.Product;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Builder
@Data
public class AccessoryDTO {
    private Long id;
    private Product accessory;
    @NotEmpty(message = "*Required")
    @Length(max = 80,message = "No more than 20 characters")
    private String accessoryType;
    @NotEmpty(message = "*Required")
    @Length(max = 2000,message = "No more than 2000 characters")
    private String description;
}
