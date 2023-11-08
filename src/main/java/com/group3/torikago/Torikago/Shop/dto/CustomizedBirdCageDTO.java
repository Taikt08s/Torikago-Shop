package com.group3.torikago.Torikago.Shop.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.Empty;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class CustomizedBirdCageDTO {
    private Long id;
    private ProductDTO customizedBirdCage;
    @NotEmpty(message = "*Required")
    @Length(max = 20, message = "Max 20 characters")
    private String dimension;
    @NotEmpty(message = "*Required")
    @Length(max = 20, message = "Max 20 characters")
    private String cageShape;
    @NotNull(message = "*Required")
    @Digits(integer = 3, fraction = 2, message = "Invalid number format")
    private double barSpacing;
    @NotNull(message = "*Required")
    @Digits(integer = 3, fraction = 2, message = "Invalid number format")
    private double birdWingSpan;
    @NotEmpty(message = "*Required")
    @Length(max = 20, message = "No more than 20 characters")
    private String status;
    @NotEmpty(message = "*Required")
    @Length(max = 50, message = "No more than 50 characters")
    private String material;
    @Length(max = 50, message = "No more than 50 characters")
    private String color;
    @NotEmpty(message = "*Required")
    @Length(max = 2000, message = "No more than 2000" +
            " characters")
    private String description;
    @Nullable
    private Boolean cartStatus;
}
