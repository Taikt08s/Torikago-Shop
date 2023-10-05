package com.group3.torikago.Torikago.Shop.dto;

import com.group3.torikago.Torikago.Shop.model.Product;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class BirdCageDTO {
    private Long id;
    private ProductDTO birdCage;
    @NotEmpty(message = "*Required")
    @Length(max = 20,message = "Max 20 characters")
    private String dimension;
    @NotEmpty(message = "*Required")
    @Length(max = 20,message = "Max 20 characters")
    private String cageShape;
    @NotNull(message = "*Required")
    @Digits(integer = 3, fraction = 2, message = "Invalid number format")
    private double barSpacing;
    @NotNull(message = "*Required")
    @Digits(integer = 3, fraction = 2, message = "Invalid number format")
    private double birdWingSpan;
    @NotEmpty(message = "*Required")
    @Length(max = 250,message = "No more than 250 characters")
    private String description;
}
