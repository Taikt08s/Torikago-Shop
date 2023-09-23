package com.group3.torikago.Torikago.Shop.dto;

import com.group3.torikago.Torikago.Shop.model.Product;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BirdCageDTO {
    private Product birdCage;

    private String dimension;

    private String cageShape;

    private double barSpacing;

    private double birdWingSpan;

    private String description;
}
