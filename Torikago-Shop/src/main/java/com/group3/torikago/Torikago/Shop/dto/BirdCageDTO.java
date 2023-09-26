package com.group3.torikago.Torikago.Shop.dto;

import com.group3.torikago.Torikago.Shop.model.Product;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class BirdCageDTO {
    @NotEmpty(message = "*Dimension is required")
    @Length(max = 20,message = "No more than 20 characters")
    private String dimension;
    @NotEmpty(message = "*Shape is required")
    @Length(max = 20,message = "No more than 20 characters")
    private String cageShape;

    private double barSpacing;

    private double birdWingSpan;
    @NotEmpty(message = "*Description is required")
    @Length(max = 250,message = "No more than 250 characters")
    private String description;
}
